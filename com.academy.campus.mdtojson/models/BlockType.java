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
    CodeBlock("```"),
    ParagraphBlock(null),
    ;

    private final String identifier;

    BlockType(String identifier) {
        this.identifier = identifier;
    }

    public String getIdentifier() {
        return identifier;
    }

    public static BlockType findByIdentifier(String identifier) {
       // System.out.println(String.format("Identifier dans BlockType = %s", identifier));
        for (BlockType bt : values()) {
            if (identifier != null && !identifier.isEmpty()) {
                if (bt.getIdentifier() != null && bt.getIdentifier().equals(identifier)) {
                    return bt;
                }
            }
        }
        if (identifier != null && !identifier.isEmpty()) {
            if (identifier.contains(CodeBlock.getIdentifier())){
                return CodeBlock;
            }
            return ParagraphBlock;
        }
        return null;
    }
}
