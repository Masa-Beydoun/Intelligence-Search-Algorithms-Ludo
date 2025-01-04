import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class State {

    Stone[] stones = new Stone[16];
    int turn = 0;

    public State() {
        int id = 0;
        stones[0] = new Stone(id++, 1, 1, Color.red, false, false);
        stones[1] = new Stone(id++, 1, 4, Color.red, false, false);
        stones[2] = new Stone(id++, 4, 1, Color.red, false, false);
        stones[3] = new Stone(id++, 4, 4, Color.red, false, false);


        stones[4] = new Stone(id++, 1, 10, Color.green, false, false);
        stones[5] = new Stone(id++, 1, 13, Color.green, false, false);
        stones[6] = new Stone(id++, 4, 10, Color.green, false, false);
        stones[7] = new Stone(id, 4, 13, Color.green, false, false);


        stones[12] = new Stone(id++, 13, 13, Color.yellow, false, false);
        stones[13] = new Stone(id++, 13, 10, Color.yellow, false, false);
        stones[14] = new Stone(id++, 10, 10, Color.yellow, false, false);
        stones[15] = new Stone(id++, 10, 13, Color.yellow, false, false);

        stones[8] = new Stone(id++, 10, 1, Color.blue, false, false);
        stones[9] = new Stone(id++, 10, 4, Color.blue, false, false);
        stones[10] = new Stone(id++, 13, 1, Color.blue, false, false);
        stones[11] = new Stone(id++, 13, 4, Color.blue, false, false);


    }

    public void killOrNot(int id, int finalI, int finalJ) {
        int stoneExisted = 0;
        Stone willBeKilled = null;
        for (Stone s : stones) {
            if (s.i == finalI && s.j == finalJ
                    && s.color != stones[id + 1].color) {

                //edit this
                //add sherah
                stoneExisted++;
                willBeKilled = s;
            }
        }
        if (stoneExisted == 1) {
            willBeKilled.alive = false;
            willBeKilled.i = 0;
            willBeKilled.j = 0;
        }
    }


    public int getNerdNumber() {
        Random r = new Random();
        int ran;
        while ((ran = r.nextInt(7)) == 0) {

        }
        System.out.println(ran);
        if (ran != 6) {
            turn = (turn + 1) % 4;
            System.out.println(turn);
        }
        return ran;
    }


    public State move(int id, int ran) {
        Stone s = stones[id];
        boolean flag = (s.id < 4 && turn == 0);
        flag = flag || (s.id > 3 && s.id < 8 && turn == 1);
        flag = flag || (s.id > 7 && s.id < 12 && turn == 2);
        flag = flag || (s.id > 11 && turn == 3);
        State newState = new State();
        newState.stones = Arrays.copyOf(stones, stones.length);
        newState.turn = turn;
        Stone newStone = new Stone(s.id,s.i,s.j,s.color,s.alive,s.locked);
        if (flag){
            newStone.move(ran);
            newState.stones[id] = newStone;
            return newState;
        }
        return null;
    }

    public boolean checkExist(int i, int j) {
        //check if there is another stone in this place
        for (Stone s : stones) {
            if (s.i == i && s.j == j) {
                return true;
            }
        }
        return false;
    }


    public List<State> nextStates() {

        List<State> nextStates = new ArrayList<>();
        for (int nerdNumber = 1; nerdNumber <= 6; nerdNumber++) {
            if (turn == 0) {
                for (int stoneId = 0; stoneId < 4; stoneId++) {
                    nextStates.add(move(this, stoneId, nerdNumber, turn));
                }
            } else {
                for (int dtoneId = 4; dtoneId < 8; dtoneId++) {
                    nextStates.add(move(this, dtoneId, nerdNumber, turn));
                }
            }
        }
        return nextStates;

    }

    public State move(State state, int id, int ran, int turn) {
        return null;
    }

    @Override
    public String toString() {
        return "State{" +
                "stones=" + Arrays.toString(stones) +
                ", turn=" + turn +
                '}';
    }
}
