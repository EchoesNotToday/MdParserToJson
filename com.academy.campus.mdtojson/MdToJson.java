import models.Block;
import models.BlockType;
import models.State;

import java.io.*;

public class MdToJson {

    public static String json = "{";
    public static Block oldBlock;

    public static void main(String[] args) {
        System.out.println("Conversion du fichier : " + args[0]);
        File file = new File(args[0]);

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                ProcessLine(line);
            }
            json+= "}";
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

        Block currentBlock = new Block(blockType, State.Neutral);

        // Ligne vide
        if(line.equals("")) {
            currentBlock.setState(State.Neutral);
        }
        // Fin d'une balise (exemple de nouveau ''')
        else if(currentBlock.getParent() != null && currentBlock.getParent().getBlockType() == blockType) {
            currentBlock.setState(State.Completed);
        }
        // Si n'existe pas => paragraphe : line = "Un nouveau paragraphe"
        else if(blockType == null) {
            // SI C'EST DANS LE MÊME PARAGRAPHE
            if(oldBlock.getState() == State.Pending){
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
           currentBlock.setState(State.Pending);
           json += "\"" + blockType.name() + "\":\"" + content  + "\",\n";

            //TODO Si block est différents Hx
//           currentBlock->setParent();
        }
        oldBlock = currentBlock;
    }
}
