import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class State {

    Stone stones[] = new Stone[8];
    int turn = 0;

    public State() {
        int id = 0;
        stones[0] = new Stone(id++, 1, 1, Color.green, false, false);
        stones[1] = new Stone(id++, 1, 4, Color.green, false, false);
        stones[2] = new Stone(id++, 4, 1, Color.green, false, false);
        stones[3] = new Stone(id++, 4, 4, Color.green, false, false);
        stones[4] = new Stone(id++, 13, 13, Color.blue, false, false);
        stones[5] = new Stone(id++, 13, 10, Color.blue, false, false);
        stones[6] = new Stone(id++, 10, 10, Color.blue, false, false);
        stones[7] = new Stone(id, 10, 13, Color.blue, false, false);
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
            willBeKilled.Alive = false;
            willBeKilled.i = 0;
            willBeKilled.j = 0;
        }
    }


    public int getNerdNumber() {
        Random r = new Random();
        int ran;
        while ((ran = r.nextInt(7)) == 0) {
        }
        if (turn == 0) {
            if (ran != 6) turn = 1;
        } else {
            if (turn == 1) {
                if (ran != 6) turn = 0;
            }
        }
        return ran;
    }


    public void makeAlive(int id) {
        //if the stone is form the first team then it will be in 6 , 1
        if (id < 4) {
            stones[id].i = 6;
            stones[id].j = 1;
            stones[id].Alive = true;
            stones[id].locked = true;
        }
        //if the stone is form the first team then it will be in 8,13
        else {
            stones[id].i = 8;
            stones[id].j = 13;
            stones[id].Alive = true;
            stones[id].locked = true;
        }
    }

    public void move(int id, int ran) {
        //if the stone is not alive then make it alive
        if (!stones[id].Alive && ran == 6) {
            makeAlive(id);
            return;
        }
        if (!stones[id].Alive && ran != 6) {
            return;
        }
        //if the stone is alive check how it can walk
        int i = stones[id].i;
        int j = stones[id].j;

        if (i == 0) {
            int dis = 8 - j;
            if (ran <= dis) {
                System.out.println("case 1");
                stones[id].j += dis;
            } else {
                System.out.println("case 2");
                stones[id].j = 8;
                stones[id].i = ran - dis;
            }
            return;
        }
        if(i == 14){
            int dis = j - 6;
            if (ran <= dis) {
                System.out.println("case 3");
                stones[id].j -= dis;
            } else {
                System.out.println("case 4");
                stones[id].j = 6;
                stones[id].i = 14 - (ran - dis);
            }
            return;
        }

        if (i == 6) {
            if ((j + ran <= 5 && j < 5) || (j >= 9 && j + ran < 15)) {
                System.out.println("case 5");
                stones[id].j = j + ran;
            }
            else {
                if (j < 7) {
                    System.out.println("case 6");
                    int dis = 5 - j;
                    stones[id].j = 6;
                    stones[id].i = i - (ran - dis);
                } else {
                    System.out.println("case 7");
                    int dis = 14 - j;
                    stones[id].j = 14;
                    int rest = ran - dis;
                    if(rest<=2){
                        stones[id].i+=rest;
                    }
                    else{
                        stones[id].i=8;
                        rest-=2;
                        stones[id].j= 14 - rest;
                    }
                }
            }
            return;
        }
        if (i == 8) {
            if ((j <= 5 && j - ran >= 0) || (j > 8 && j - ran > 8)) {
                System.out.println("case 8");
                stones[id].j = j - ran;
            }
            else {
                if (j > 7) {
                    System.out.println("case 9");
                    int dis = j - 9;
                    stones[id].j = 8;
                    stones[id].i = 8 + (ran - dis);
                }
                else{
                    System.out.println("case 10");
                    int dis = j;
                    stones[id].j = 0;
                    int rest = ran - dis;
                    if(rest<=2){
                        stones[id].i-=rest;
                    }
                    else{
                        stones[id].i=6;
                        rest-=2;
                        stones[id].j= rest;

                    }
                }
            }
            return;
        }

        if (j == 6) {
            if ((i < 6 && i - ran >= 0) || (i > 8 && i - ran > 8)) {
                System.out.println("case 11");
                stones[id].i = i - ran;
            }
            else {
                if(i<7) {
                    System.out.println("case 12");
                    int dis = ran - i;
                    stones[id].i = 0;
                    if (dis <= 2)
                        stones[id].j += dis;
                    else {
                        stones[id].j += 2;
                        dis = dis - 2;
                        stones[id].i = dis;
                    }
                }
                else{
                    System.out.println("case 13");
                    int dis = i - 8;
                    stones[id].i = 8;
                    stones[id].j = 5 - (ran-dis);
                }
            }
            return;
        }
        if (j == 8) {
            if ((i + ran < 6 && i < 6) || (i > 8 && i + ran < 15)) {
                System.out.println("case 14");
                stones[id].i = i + ran;
            }
            else {
                if (i < 7) {
                    System.out.println("case 15");
                    int dis = 5 - i; // 4
                    stones[id].i = 6;
                    stones[id].j = 8 + (ran - dis);
                }
                else{
                    System.out.println("case 16");
                    int dis = 14 - i;
                    stones[id].i = 14;
                    int rest = ran - dis;
                    if(rest<=2){
                        stones[id].j-=rest;
                    }
                    else{
                        stones[id].j-=2;
                        rest-=2;
                        stones[id].i = 14  - rest;
                    }
                }
            }
            return;
        }
        if(j==0){

            int dis = 8 - i;
            if(ran<=dis){
                System.out.println("case 17");
                stones[id].i-=ran;
            }
            else{
                System.out.println("case 18");
                stones[id].i=6;
                int rest = ran - dis;
                stones[id].j=rest;
            }
        }


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
