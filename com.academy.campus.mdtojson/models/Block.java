package models;

public abstract class Block {
    private BlockType blockType;
    private String content;
    private State state;

    public Block(BlockType blockType, State state, String content) {
        this.blockType = blockType;
        this.state = state;
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

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }


}
