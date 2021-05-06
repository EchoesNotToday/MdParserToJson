package models;

public abstract class Block {
    private BlockType blockType;
    private String content;

    public Block(BlockType blockType, String content) {
        this.blockType = blockType;
        this.content = content;
    }

    public BlockType getBlockType() {
        return blockType;
    }

    public void setBlockType(BlockType blockType) {
        this.blockType = blockType;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
