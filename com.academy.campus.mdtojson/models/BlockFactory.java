package models;

import com.google.gson.Gson;

import java.util.List;

public class BlockFactory {

    private BlockFactory() {
    }

    private static BlockFactory instance = null;

    public static synchronized BlockFactory getInstance() {
        if (instance == null) {
            instance = new BlockFactory();
        }
        return instance;
    }

    public Block getBlock(BlockType blockType , String content) {
        return new StandardBlock(blockType, content);
    }

    public Block getBlock(BlockType blockType, List<String> values) {
        Gson g = new Gson();
        String content = g.toJson(values);
        return new ListBlock(blockType, content);
    }
}
