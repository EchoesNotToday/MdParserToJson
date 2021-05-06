package models;

public enum State {
    InH1,
    InH2,
    InH3,
    InH4,
    InH5,
    InH6,
    InUL,
    InCodeBlock,
    InParagraph,
    Neutral,
    Pending,
    Completed;

    public State nextState(BlockType blockType, String content, State currentState) {
        State state2return = currentState;
        if (blockType == null) {
            state2return = State.Neutral;
        } else {
            switch (blockType) {
                case H1:
                    if (currentState != InCodeBlock) {
                        state2return = InH1;
                    }
                    break;
                case H2:
                    state2return = InH2;
                    break;
                case H3:
                    state2return = InH3;
                    break;
                case H4:
                    state2return = InH4;
                    break;
                case H5:
                    state2return = InH5;
                    break;
                case H6:
                    state2return = InH6;
                    break;
                case UL:
                    state2return = InUL;
                    break;
                case CodeBlock:
                    state2return = InCodeBlock;
                    if (currentState == InCodeBlock) {
                        state2return = Neutral;
                    }
                    break;
                case ParagraphBlock:
                    state2return = InParagraph;
                    break;
                default:
                    state2return = Neutral;
                    if (content != null && !content.isEmpty()) {
                        state2return = InParagraph;
                    }
                    break;
            }
        }
        return state2return;
    }
}
