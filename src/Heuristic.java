import java.util.ArrayList;
import java.util.List;

public class Heuristic {


    public static int calculateHeuristic(State state) {
        State parent = state;
        while(parent != null && parent.turn == state.turn) {
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

    public static void printingHeuristic(State state) {
        State parent = state;
        while(parent != null && parent.turn == state.turn) {
            parent=parent.parent;
        }
        System.out.println();
        System.out.println("--------------------------");
        System.out.println("state : "+ state);
        System.out.println("new stones : " + isNewStone(state));
        System.out.println("isPlayer safe : " + isPlayerSafe(state));
        System.out.println("is killing : " + iskilling(state,parent));
        System.out.println("distributeTheStones : "+distributeTheStones(state));
        System.out.println("entered the kitchen : " + enteredTheKitchen(state));
        System.out.println("wallStones : " + wallStones(state));
        System.out.println("inDangerStones : " + inDangerStones(state));
        System.out.println("-----------------------------------");
    }

    public static List<Stone> getOtherStones(State state) {
        List<Stone> otherStones = new ArrayList<>();
        int turn = state.turn - 1;
        if(turn == -1) turn = 3;
        for(Player player : state.players) {
            if(player.playerID == turn) continue;
            for(Stone stone : player.stones) {
                otherStones.add(stone);
            }
        }
        return otherStones;
    }

    private static int isNewStone(State state) {
        int count = 0;
//        System.out.println(state.players.get(state.turn).stones);
        int turn = state.turn - 1;
        if(turn == -1) turn = 3;
        for(Stone stone : state.players.get(turn).stones){
            if(stone.alive){
                count++;
            }
        }

        return count;
    }

    public static int distributeTheStones(State state) {
        boolean f1 = false, f2 = false, f3 = false, f4 = false;
        int turn = state.turn - 1;
        if(turn == -1) turn = 3;
        for (Stone s : state.players.get(turn).stones) {
            if (!s.alive) continue;
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
        int turn = state.turn - 1;
        if(turn == -1) turn = 3;
        List<Stone> stones = state.players.get(turn).stones;
        for (Stone stone : stones) {
            if (!stone.alive)continue;
            boolean flag = stone.position.getPrevious6Cells(stone.position.i, stone.position.j, getOtherStones(state));
            if (flag) sum++;
        }
        return sum;
    }

    public static int wallStones(State state) {
        List<Position> positions = new ArrayList<>();
        int turn = state.turn - 1;
        if(turn == -1) turn = 3;
        for (Stone stone : state.players.get(turn).stones) {
            if(!stone.alive) continue;
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
        int turn = state.turn - 1;
        if(turn == -1) turn = 3;
        Player player = state.players.get(turn);
        return 4 - player.stones.size();
    }

    private static int iskilling(State state,State parentState) {
        int killCount = 0;
        if (parentState != null && getOtherStones(state).size() < getOtherStones(parentState).size()) {
            killCount += (getOtherStones(parentState).size() - getOtherStones(state).size());
        }
        return killCount;
    }

    private static int isPlayerSafe(State state) {
        int count = 0;
        int turn = state.turn - 1;
        if(turn == -1) turn = 3;
        for (Stone stone : state.players.get(turn).stones) {
            if (stone.alive && stone.locked) {
                count++;
            }
        }
        return count;
    }



}
