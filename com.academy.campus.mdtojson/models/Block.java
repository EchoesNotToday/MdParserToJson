package models;

public class Block {
    private BlockType blockType;
    private State state;
    private Block parent;

    public Block(BlockType blockType, State state) {
        this.blockType = blockType;
        this.state = state;
    }

    public BlockType getBlockType() {
        return blockType;
    }

    public void setBlockType(BlockType blockType) {
        this.blockType = blockType;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public Block getParent() {
        return parent;
    }

    public void setParent(Block parent) {
        this.parent = parent;
    }
}
