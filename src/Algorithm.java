
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Algorithm {

    private static final int MAX_DEPTH = 2;

    public State bestState(State root) {

        double bestValue = Double.NEGATIVE_INFINITY;
        State bestState = null;
        Set<State> visited = new HashSet<>();
        visited.add(root);

        List<State> nextStates = root.nextStates();
        for (State state : nextStates) {
            double value = expectimax(state, 0, root.turn, root.turn, visited);
            if (value > bestValue) {
                bestValue = value;
                bestState = state;
            }
        }

        return bestState;
    }

    private double expectimax(State state, int depth, int turn, int actualTurn, Set<State> visited) {

        if (depth == MAX_DEPTH || state.checkGoal()) {
            //return state.heuristic();
            return 1;
        }

        if (visited.contains(state)) {
            return 0;
        }
        visited.add(state);

        if (turn == actualTurn) {

            double maximizer = Double.NEGATIVE_INFINITY;

            List<State> nextStates = state.nextStates();
            for (State nextState : nextStates) {
                double max = expectimax(nextState, depth + 1, nextState.turn, actualTurn, visited);
                maximizer = Math.max(maximizer, max);
            }

            return maximizer;
        }
        else {

            double chance = 0;

            List<State> nextStates = state.nextStates();
            double probability = 1.0 / 6;
            for (State nextState : nextStates) {
                chance += probability * expectimax(nextState, depth + 1, nextState.turn, actualTurn, visited);
            }

            return chance;
        }
    }
}
