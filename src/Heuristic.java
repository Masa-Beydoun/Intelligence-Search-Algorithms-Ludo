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
}
