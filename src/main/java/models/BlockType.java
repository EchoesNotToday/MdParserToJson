package models;

import java.util.regex.Pattern;

// https://github.com/adam-p/markdown-here/wiki/Markdown-Cheatsheet
public enum BlockType {
    h1("#"),
    h2("##"),
    h3("###"),
    h4("####"),
    h5("#####"),
    h6("######"),
    ul("*"),
    CodeBlock("```");

    private final String identifier;

    BlockType(String identifier) {
        this.identifier = identifier;
    }

    public String getIdentifier() {
        return identifier;
    }

    public static BlockType findByIdentifier(String identifier) {
        for (BlockType bt : values()) {
            if (bt.getIdentifier().equals(identifier)) {
                return bt;
            }
        }
        return null;
    }
}
