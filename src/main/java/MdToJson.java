import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import models.Block;
import models.BlockType;
import models.State;

import java.io.*;
import java.util.*;

public class MdToJson {

    public static Block previousBlock;
    public static Map<String, Object> map = new HashMap<>();

    public static void main(String[] args) {
        System.out.println("Conversion du fichier : " + args[0]);
        File file = new File(args[0]);

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                ProcessLine(line);
            }
        } catch (IOException fnfEx) {
            fnfEx.printStackTrace();
        }

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(map);

        System.out.println(json);
    }

    private static void ProcessLine(String line) {
        String code = line.split(" ")[0];
        String content = line.substring(code.length()).trim();
        BlockType blockType = BlockType.findByIdentifier(code);
        Block block = new Block(blockType, State.Completed, content);

        if (block.getBlockType() == BlockType.ul) {
            block.setState(State.Pending);
        }

        map.put(block.getBlockType().name(), content);

        previousBlock = block;
    }
}
