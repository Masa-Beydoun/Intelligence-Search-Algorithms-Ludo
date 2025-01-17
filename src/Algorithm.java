import java.util.List;

public class Algorithm {

    private static final int MAX_DEPTH = 3;

    public State bestState(State root) {

        double bestValue = Double.NEGATIVE_INFINITY;
        State bestState = null;

        List<State> nextStates = root.nextStates(root.turn);
        for (State state : nextStates) {
            double value = expectimax(state, 0, root.turn);
            if (value > bestValue) {
                bestValue = value;
                bestState = state;
            }
        }

        return bestState;
    }

    private double expectimax(State state, int depth, int turn) {

        if (depth == MAX_DEPTH || state.checkGoal()) {
            return state.heuristic();
        }

        if (turn == 0) {

            double maximizer = Double.NEGATIVE_INFINITY;

            List<State> nextStates = state.nextStates(turn);
            for (State nextState : nextStates) {
                double max = expectimax(nextState, depth + 1, nextState.turn);
                maximizer = Math.max(maximizer, max);
            }

            return maximizer;
        }
        else {

            double chance = 0;

            List<State> nextStates = state.nextStates(turn);
            double probability = 1.0 / 6;
            for (State nextState : nextStates) {
                chance += probability * expectimax(nextState, depth - 1, nextState.turn);
            }

            return chance;
        }
    }
}
