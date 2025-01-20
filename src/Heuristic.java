import java.util.ArrayList;
import java.util.List;

public class Heuristic {


    public static int calculateHeuristic(State state) {
        State parent = state;
        while(parent.turn == state.turn) {
            parent=parent.parent;
        }
        return isNewStone(state) * 5 +
                isPlayerSafe(state) * 3 +
                iskilling(state,parent) * 6 +
                distributeTheStones(state) * 4 +
                enteredTheKitchen(state) * 2 +
                wallStones(state) * 4 +
                inDangerStones(state) * -5;
    }

    private static int isNewStone(State state) {
        int count = 0;
        for(Stone stone : state.players.get(state.turn).stones){
            if(stone.alive){
                count++;
            }

        }

        return count;
    }


    public static int distributeTheStones(State state) {
        boolean f1 = false, f2 = false, f3 = false, f4 = false;
        for (Stone s : state.players.get(state.turn).stones) {
            if (s.position.i >= 9) {
                f1 = true;
            } else if (s.position.i <= 5) {
                f2 = true;
            } else if (s.position.j >= 9) {
                f3 = true;
            } else if (s.position.j <= 5) {
                f4 = true;
            }
        }
        int count = 0;
        if (f1) count++;
        if (f2) count++;
        if (f3) count++;
        if (f4) count++;
        return count;
    }

    public static int inDangerStones(State state) {
        int sum = 0;
        List<Stone> stones = state.players.get(state.turn).stones;
        for (Stone stone : stones) {
            boolean flag = stone.position.getPrevious6Cells(stone.position.i, stone.position.j, state.getOtherStones());
            if (flag) sum++;
        }
        return sum;
    }

    public static int wallStones(State state) {
        List<Position> positions = new ArrayList<>();
        for (Stone stone : state.players.get(state.turn).stones) {
            positions.add(stone.position);
        }
        int count = 0;
        List<Integer> vis = new ArrayList<>();
        for (int i = 0; i < positions.size(); i++) {
            if (vis.contains(i)) continue;
            for (int j = i + 1; j < positions.size(); j++) {
                if (positions.get(i).equals(positions.get(j))) {
                    vis.add(j);
                    count++;
                }
            }
        }
        if (count == 0) return 0;
        return count + 1;
    }


    public static int enteredTheKitchen(State state) {
        Player player = state.players.get(state.turn);
        return 4 - player.stones.size();
    }


    private static int iskilling(State state,State parentState) {
        int killCount = 0;
        if (state.getOtherStones().size() < parentState.getOtherStones().size()) {
            killCount += (parentState.getOtherStones().size() - state.getOtherStones().size());
        }
        return killCount;
    }


    private static int isPlayerSafe(State state) {
        int count = 0;
        for (Stone stone : state.players.get(state.turn).stones) {
            if (stone.alive && stone.locked) {
                count++;
            }
        }
        return count;
    }


}
