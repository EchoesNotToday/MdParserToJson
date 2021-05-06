import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import models.*;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;

public class MdToJson {

    private static String json;
    private static Block oldBlock;
    private static boolean firstUl = true;
    private static ArrayList<Block> map = new ArrayList<>();

    public static void main(String[] args) {
        System.out.println("Conversion du fichier : " + args[0]);
        File file = new File(args[0]);
        State currentState = State.Neutral;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                currentState = ProcessLine(currentState, line);
            }

            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            json = gson.toJson(map);
        } catch (IOException fnfEx) {
            fnfEx.printStackTrace();
        }
        System.out.println(json);
    }

    private static void ProcessLine(String line) {
        String code = line.split(" ")[0];
        BlockType blockType = BlockType.findByIdentifier(code);
        String content = "";
        if (blockType != null) {
            content = line.substring(code.length());
        } else {
            content = line;
        }

        Block currentBlock = BlockFactory.getInstance().getBlock(blockType, content);
        // Ligne vide
        if (line.equals("")) {
            //currentBlock.setState(State.Neutral);
        }
        // Fin d'une balise (exemple de nouveau ''')
        else if (currentBlock instanceof ParagraphBlock && ((ParagraphBlock) currentBlock).getParent() != null && ((ParagraphBlock) currentBlock).getParent().getBlockType() == blockType) {
            //currentBlock.setState(State.Completed);
        }
        // Si n'existe pas => paragraphe : line = "Un nouveau paragraphe"
        else if (blockType == null) {
            // SI C'EST DANS LE MÊME PARAGRAPHE
            if (null == State.Pending) {
//                System.out.println("---------------------------------------------------------");
//                System.out.println(json);
                json = json.substring(0, json.length() - 3);
                json += "\",\\n" + content + ",\n";
            }
            // SI NOUVEAU PARAGRAPHE
            else {
                json += "\"p\":\"" + content + "\",\n";
            }
        }
        //Si blockType existe
        else {
            //currentBlock.setState(State.Pending);
            json += "\"" + blockType.name() + "\":\"" + content + "\",\n";

            //TODO Si block est différents Hx
//           currentBlock->setParent();
        }
        oldBlock = currentBlock;
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

        content = content.trim();
        State oldState = currentState;
        currentState = currentState.nextState(blockType, content);
        //System.out.println(String.format("CurrentState = %s", currentState.name()));
        //System.out.println(String.format("Content = %s", line));

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
                if (oldState == State.InUL) {
                    ListBlock currentUl = (ListBlock) map.get(map.size() - 1);
                    map.remove(currentUl);
                    currentUl.addContent(content);
                    map.add(currentUl);
                } else {
                    ArrayList<String> values = new ArrayList<>();
                    values.add(content);
                    map.add(BlockFactory.getInstance().getBlock(blockType, values));
                }
                break;
            case InParagraph:
            case InCodeBlock:
                if (oldState == currentState) {
                    Block currentBlock = map.get(map.size() - 1);
                    map.remove(currentBlock);
                    String newContent;
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
