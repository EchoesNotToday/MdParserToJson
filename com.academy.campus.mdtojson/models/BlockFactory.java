package models;

public class BlockFactory {

    private BlockFactory(){}

    private static BlockFactory INSTANCE = null;

    public static synchronized BlockFactory getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new BlockFactory();
        }
        return INSTANCE;
    }

    public Block getBlock(BlockType blockType, String content, Block parent) {
        return new ParagraphBlock(blockType, content, parent);
    }

    public Block getBlock(BlockType blockType, String content) {
        return new StandardBlock(blockType, content);
    }
}
