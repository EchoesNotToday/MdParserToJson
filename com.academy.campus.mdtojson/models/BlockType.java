package models;

public enum BlockType {
    H1("#"),
    H2("##"),
    H3("###"),
    H4("####"),
    H5("#####"),
    H6("######"),
    p(" "),
    UL("*"),
    CodeBlock("```");

    private final String identifier;

    BlockType(String identifier) {
        this.identifier = identifier;
    }

    public String getIdentifier() {
        return identifier;
    }
}
