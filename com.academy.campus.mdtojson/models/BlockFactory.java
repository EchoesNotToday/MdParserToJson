package models;

import com.google.gson.Gson;

import java.util.ArrayList;

public class BlockFactory {

    private BlockFactory() {
    }

    private static BlockFactory INSTANCE = null;

    public static synchronized BlockFactory getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new BlockFactory();
        }
        return INSTANCE;
    }

    public Block getBlock(BlockType blockType , String content) {
        return new StandardBlock(blockType, content);
    }

    public Block getBlock(BlockType blockType, ArrayList<String> values) {
        Gson g = new Gson();
        String content = g.toJson(values);
        return new ListBlock(blockType, content);
    }
}
