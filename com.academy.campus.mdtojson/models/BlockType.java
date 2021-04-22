package models;

import java.util.regex.Pattern;

// https://github.com/adam-p/markdown-here/wiki/Markdown-Cheatsheet
public enum BlockType {
    H1("#"),
    H2("##"),
    H3("###"),
    H4("####"),
    H5("#####"),
    H6("######"),
    UL("*"),
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
