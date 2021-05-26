package models;

public enum State {
    INH1,
    INH2,
    INH3,
    INH4,
    INH5,
    INH6,
    IN_UL,
    IN_CODE_BLOCK,
    IN_PARAGRAPH,
    NEUTRAL;

    public State nextState(BlockType blockType, String content) {
        State state2return = this;
        if (blockType == null) {
            state2return = State.NEUTRAL;
        } else {
            switch (blockType) {
                case H1:
                    if (this != IN_CODE_BLOCK) {


                        state2return = INH1;
                    }
                    break;
                case H2:
                    state2return = INH2;
                    break;
                case H3:
                    state2return = INH3;
                    break;
                case H4:
                    state2return = INH4;
                    break;
                case H5:
                    state2return = INH5;
                    break;
                case H6:
                    state2return = INH6;
                    break;
                case UL:
                    if (this != IN_CODE_BLOCK) {
                        state2return = IN_UL;
                    }
                    break;
                case CODE_BLOCK:
                    state2return = IN_CODE_BLOCK;
                    if (this == IN_CODE_BLOCK) {
                        state2return = NEUTRAL;
                    }
                    break;
                case PARAGRAPH_BLOCK:
                    if (this != IN_CODE_BLOCK) {
                        state2return = IN_PARAGRAPH;
                    }
                    break;
                default:
                    state2return = NEUTRAL;
                    if (content != null && !content.isEmpty()) {
                        state2return = IN_PARAGRAPH;
                    }
                    break;
            }
        }
        return state2return;
    }
}
