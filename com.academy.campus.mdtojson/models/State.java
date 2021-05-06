package models;

public enum State {
    Neutral {
        @Override
        public State nextState(BlockType blockType) {
            // Reste en Neutral dans le cas d'une ligne vide
            State state2Return = this;
            if (blockType != null) {
                // Passe à l'état Pending dans tous les autres cas
                state2Return = Pending;
            }
            return state2Return;
        }
    },
    Pending {
        @Override
        public State nextState(BlockType blockType) {
            // Si paragraphe -> continue à pending
            State state2return = this;

            // Si ligne vide -> repasse à l'état Neutral
            if (blockType == null) {
                state2return = Neutral;
            }

            // Si fin d'un block de code -> Completed
            if (blockType == BlockType.CodeBlock) {
                return Completed;
            }

            return state2return;
        }
    },
    Completed {
        @Override
        public State nextState(BlockType blockType) {
            // Par défaut Completed == process terminé
            State state2Return = this;

            // Si nouvelle ligne vide -> Neutral
            if (blockType == null) {
                state2Return = Neutral;
            }
            return state2Return;
        }
    };

    public abstract State nextState(BlockType blockType);
}
