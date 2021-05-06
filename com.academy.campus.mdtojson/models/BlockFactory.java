package models;

public class BlockFactory {

    private BlockFactory() {
    }

    private static BlockFactory INSTANCE = null;

    public static synchronized BlockFactory getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new BlockFactory();
        }
        return INSTANCE;
    }

    public Block getBlock(BlockType blockType, State state, String content, Block parent) {
        return new ParagraphBlock(blockType, state, content, parent);
    }

    public Block getBlock(BlockType blockType, State state, String content) {
        return new StandardBlock(blockType, state, content);
    }
}
