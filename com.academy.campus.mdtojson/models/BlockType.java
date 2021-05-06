package models;

// https://github.com/adam-p/markdown-here/wiki/Markdown-Cheatsheet
public enum BlockType {
    H1("#"),
    H2("##"),
    H3("###"),
    H4("####"),
    H5("#####"),
    H6("######"),
    UL("*"),
    CODE_BLOCK("```"),
    PARAGRAPH_BLOCK(null),
    ;

    private final String identifier;

    BlockType(String identifier) {
        this.identifier = identifier;
    }

    public String getIdentifier() {
        return identifier;
    }

    public static BlockType findByIdentifier(String identifier) {
        for (BlockType bt : values()) {
            if (identifier != null && !identifier.isEmpty() && bt.getIdentifier() != null && bt.getIdentifier().equals(identifier)) {
                return bt;
            }
        }
        if (identifier != null && !identifier.isEmpty()) {
            if (identifier.contains(CODE_BLOCK.getIdentifier())) {
                return CODE_BLOCK;
            }
            return PARAGRAPH_BLOCK;
        }
        return null;
    }
}
