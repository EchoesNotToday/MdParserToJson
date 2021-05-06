package models;

import com.google.gson.Gson;

import java.util.ArrayList;

public class ListBlock extends Block{

    ListBlock(BlockType blockType, String content) {
        super(blockType, content);
    }

    public void addContent(String content2Add) {
        Gson g = new Gson();
        ArrayList<String> content = g.fromJson(this.getContent(), ArrayList.class);
        content.add(content2Add);
        this.setContent(g.toJson(content));
    }
}
