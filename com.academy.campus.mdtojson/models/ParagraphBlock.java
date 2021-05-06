package models;

public class ParagraphBlock extends Block {
    Block parent;

    protected ParagraphBlock(BlockType blockType, State state, String content, Block parent) {
        super(blockType, state, content);
        this.parent = parent;
    }

    public Block getParent() {
        return parent;
    }

    protected void setParent(Block parent) {
        this.parent = parent;
    }
}
