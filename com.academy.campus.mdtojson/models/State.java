package models;

// Approuvé par Laurine et Jules
public enum State {
    Neutral {
        @Override
        public State nextState() {
            return Pending;
        }
    },
    Pending {
        @Override
        public State nextState() {
            return Completed;
        }
    },
    Completed {
        @Override
        public State nextState() {
            return this;
        }
    };

    public abstract State nextState();

}
