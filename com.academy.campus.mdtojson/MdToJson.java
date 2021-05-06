import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import models.*;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;

public class MdToJson {

    private static String json;
    private static ArrayList<Block> map = new ArrayList<>();
    public static Gson g = new Gson();
    public static Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public static void main(String[] args) {
        System.out.println("Conversion du fichier : " + args[0]);
        File file = new File(args[0]);

        // init sate à Neutral
        State currentState = State.Neutral;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                currentState = ProcessLine(currentState, line);
            }

            json = gson.toJson(map);
        } catch (IOException fnfEx) {
            fnfEx.printStackTrace();
        }
        System.out.println(json);
    }


    private static State ProcessLine(State currentState, String line) {
        String code = line.split(" ")[0];
        BlockType blockType = BlockType.findByIdentifier(code);
        String content = "";
        if (blockType != null && blockType != BlockType.ParagraphBlock) {
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
            case InH1:
            case InH2:
            case InH3:
            case InH4:
            case InH5:
            case InH6:
                map.add(BlockFactory.getInstance().getBlock(blockType, content));
                break;
            case InUL:
                // si on eétait déjà dans une UL on ajoute à cette UL
                if (oldState == State.InUL) {
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
            case InParagraph:
            case InCodeBlock:
                // si on est dans un block de code on ajoute à ce block
                if (oldState == currentState) {
                    Block currentBlock = map.get(map.size() - 1);
                    map.remove(currentBlock);
                    String newContent;

                    // évite d'avoir un \n en début de ligne à cause des ```
                    if (currentBlock.getContent().isEmpty()) {
                        newContent = String.format("%s", content);
                    }else {
                        newContent = String.format("%s\n%s", currentBlock.getContent(), content);
                    }
                    currentBlock.setContent(newContent);
                    map.add(currentBlock);
                } else {
                    map.add(BlockFactory.getInstance().getBlock(blockType, content));
                }
                break;
            case Neutral:
                break;
        }
        return currentState;
    }

}
