
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class Algorithm {

    private static final int MAX_DEPTH = 2;
    AlgorithmType type;

    public Algorithm(AlgorithmType type) {
        this.type = type;
    }

    public State bestState(State root, int nerdNumber) {

        double bestValue = Double.NEGATIVE_INFINITY;
        State bestState = null;
        Set<State> visited = new HashSet<>();
        visited.add(root);

        List<State> nextStates;
        if (this.type == AlgorithmType.SIMPLE) {
            nextStates = root.nerdSimpleNextState(nerdNumber);
        }
        else {
            nextStates = root.nerdAdvancedNextStates(nerdNumber);

        }
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
            return Heuristic.calculateHeuristic(state);
        }

        if (visited.contains(state)) {
            return 0;
        }
        visited.add(state);

        double maximizer;
        if (turn == actualTurn) {

            maximizer = Double.NEGATIVE_INFINITY;

            List<State> nextStates;
            if (this.type == AlgorithmType.SIMPLE) {
                nextStates = state.simpleNextState();
            }
            else {
                nextStates = state.advancedNextStates();

            }
            for (State nextState : nextStates) {
                double max = expectimax(nextState, depth + 1, nextState.turn, actualTurn, visited);
                maximizer = Math.max(maximizer, max);
            }

        }
        else {

            maximizer = 0;

            List<State> nextStates;
            if (this.type == AlgorithmType.SIMPLE) {
                nextStates = state.simpleNextState();
            }
            else {
                nextStates = state.advancedNextStates();

            }
            for (State nextState : nextStates) {
                maximizer += nextState.possibility * expectimax(nextState, depth + 1, nextState.turn, actualTurn, visited);
            }

        }
        return maximizer;
    }
}
