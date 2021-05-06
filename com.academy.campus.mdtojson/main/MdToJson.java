package main;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import models.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MdToJson {

    private static String json;
    private static ArrayList<Block> map = new ArrayList<>();
    public static final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private static Logger logger = Logger.getLogger(MdToJson.class.getName());

    public static void main(String[] args) {
        File file = new File(args[0]);

        // init sate à Neutral
        State currentState = State.NEUTRAL;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                currentState = processLine(currentState, line);
            }

            json = gson.toJson(map);
        } catch (IOException fnfEx) {
            fnfEx.printStackTrace();
        }
        logger.log(Level.INFO, json);
    }


    private static State processLine(State currentState, String line) {
        String code = line.split(" ")[0];
        BlockType blockType = BlockType.findByIdentifier(code);
        String content = "";
        if (blockType != null && blockType != BlockType.PARAGRAPH_BLOCK) {
            content = line.substring(code.length());
        } else {
            content = line;
        }

        // efface les espaces du content
        content = content.trim();

        // garde le dernier état
        State oldState = currentState;

        // obtient le nouvel état selon le type de block qui a été trouvé
        currentState = currentState.nextState(blockType, content);

        // ajoute dans la liste un objet de type Block qui sera sérialisé en Json
        switch (currentState) {
            case INH1:
            case INH2:
            case INH3:
            case INH4:
            case INH5:
            case INH6:
                map.add(BlockFactory.getInstance().getBlock(blockType, content));
                break;
            case IN_UL:
                // si on eétait déjà dans une UL on ajoute à cette UL
                if (oldState == State.IN_UL) {
                    ListBlock currentUl = (ListBlock) map.get(map.size() - 1);
                    map.remove(currentUl);
                    currentUl.addContent(content);
                    map.add(currentUl);
                } else {
                    // sinon on crée la liste
                    ArrayList<String> values = new ArrayList<>();
                    values.add(content);
                    map.add(BlockFactory.getInstance().getBlock(blockType, values));
                }
                break;
            case IN_PARAGRAPH:
            case IN_CODE_BLOCK:
                // si on est dans un block de code on ajoute à ce block
                if (oldState == currentState) {
                    Block currentBlock = map.get(map.size() - 1);
                    map.remove(currentBlock);
                    String newContent;

                    // évite d'avoir un \n en début de ligne à cause des ```
                    if (currentBlock.getContent().isEmpty()) {
                        newContent = String.format("%s", content);
                    } else {
                        newContent = String.format("%s%n%s", currentBlock.getContent(), content);
                    }
                    currentBlock.setContent(newContent);
                    map.add(currentBlock);
                } else {
                    map.add(BlockFactory.getInstance().getBlock(blockType, content));
                }
                break;
            default:
            case NEUTRAL:
                break;
        }
        return currentState;
    }

}
