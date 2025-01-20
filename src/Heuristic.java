import java.util.ArrayList;
import java.util.List;

public class Heuristic {


    public static int inDangerStones(State state){
        int sum=0;
        List<Stone> stones = state.players.get(state.turn).stones;
        for(Stone stone : stones){
            boolean flag =  stone.position.getPrevious6Cells(stone.position.i,stone.position.j,state.getOtherStones());
            if(flag) sum++;
        }
        return sum;
    }

    public static int wallStones(State state){
        List<Position> positions = new ArrayList<>();
        for(Stone stone : state.players.get(state.turn).stones){
            positions.add(stone.position);
        }
        List<Position> visitedPositions = new ArrayList<>();
        int count = 0;
        List<Integer> vis = new ArrayList<>();
        for(int i=0; i<positions.size(); i++){
            if(vis.contains(i)) continue;
            for(int j=i+1; j<positions.size(); j++){
                if(positions.get(i).equals(positions.get(j))){
                    vis.add(j);
                    count++;
                }
            }
        }
        if(count == 0) return 0;
        return count + 1;
    }


    public static int enteredTheKitchen(State state){
        Player player = state.players.get(state.turn);
        return 0;
    }




    public static int calculateHeuristic(State state) {
        int heuristicValue = 0;

        return inDangerStones(state)+wallStones(state)+ enteredTheKitchen(state)+ isPlayerSafe(state);
//
//        for (int i = 0; i < state.players.size(); i++) {
//            Player player = state.players.get(i);
//
//
//            // check each stone
//            for (Stone stone : player.stones) {
//                if (stone.alive) {
//
//
//                    // Check if stone is in safe zone
//                    boolean isSafe = isPlayerSafe(player, allStones);
//                    if (isSafe) {
//                        heuristicValue += 1;
//                    } else {
//                        heuristicValue -= 1;
//                    }
//
//                    // Check if stone is blocking an opponent
//                    if (isBlockingOpponent(player, allStones)) {
//                        heuristicValue +=1;
//                    }
//                }
//            }
//        }
//
//        return heuristicValue;
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


//    private static boolean isBlockingOpponent(Player player, List<Stone> allStones) {
//        for (Stone stone : player.stones) {
//            if (stone.alive) {
//                List<Stone> previousCells = new ArrayList<>();
//                stone.position.getNext6Cells(stone.position.i, stone.position.j, previousCells);
//
//                for (Stone prevCell : previousCells) {
//                    if (prevCell.locked) {
//                        return true;
//
//                    }
//                }
//            }
//        }
//
//        return false; // All stones are safe
//    }

}
