import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Player {

    public List<Stone> stones = new ArrayList<>();
    int id;

    public Player(int id) {
        this.id = id;
        if (id == 0) {
            stones.add(new Stone(0, new Position(1, 1), false, false));
            stones.add(new Stone(1, new Position(1, 4), false, false));
            stones.add(new Stone(2, new Position(4, 1), false, false));
            stones.add(new Stone(3, new Position(4, 4), false, false));
        } else if (id == 1) {
            stones.add(new Stone(0, new Position(1, 10), false, false));
            stones.add(new Stone(1, new Position(1, 13), false, false));
            stones.add(new Stone(2, new Position(4, 10), false, false));
            stones.add(new Stone(3, new Position(4, 13), false, false));

        } else if (id == 2) {
            stones.add(new Stone(0, new Position(13, 13), false, false));
            stones.add(new Stone(1, new Position(13, 10), false, false));
            stones.add(new Stone(2, new Position(10, 10), false, false));
            stones.add(new Stone(3, new Position(10, 13), false, false));

        } else if (id == 3) {
            stones.add(new Stone(0, new Position(10, 1), false, false));
            stones.add(new Stone(1, new Position(10, 4), false, false));
            stones.add(new Stone(2, new Position(13, 4), false, false));
            stones.add(new Stone(3, new Position(13, 1), false, false));
        }
    }

    public void updateLocked() {
        stones.get(0).updateLocked();
        stones.get(1).updateLocked();
        stones.get(2).updateLocked();
        stones.get(3).updateLocked();
        for (Stone s1 : stones) {
            for (Stone s2 : stones) {
                if (s1.id != s2.id && s1.position.i == s2.position.i
                        && s1.position.j == s2.position.j) {
                    s1.locked = true;
                    s2.locked = true;
                }
            }
        }
    }

    public boolean thereIsMove(int ran) {
        List<Stone> newStones = new ArrayList<>();
        for(Stone s :stones){
            newStones.add(new Stone(s.id, s.position, s.alive, s.locked));
        }

        for (Stone s : newStones) {
            MoveType movee = s.move(ran, id, false);
            System.out.println(movee);
            if (movee == MoveType.MOVED || movee == MoveType.ENTERED_THE_KITCHEN) {
                return true;
            }
        }
        return false;
    }

    public MoveType move(int stoneId,int ran,boolean flag){
        MoveType type = stones.get(stoneId).move(ran, this.id, flag);
        if(type == MoveType.CANT_MOVE){
            System.out.println("stone can't move");
            return MoveType.CANT_MOVE;
        }
        if(stones.get(stoneId).checkInKitchen(this.id,stones.get(stoneId).position)){
            stones.remove(getStoneById(stoneId));
            System.out.println("stone entered the kitchen");
            return MoveType.ENTERED_THE_KITCHEN;
        }
        System.out.println("the stone has moved to new place " + getStoneById(stoneId));
        return MoveType.MOVED;
    }
    public Stone getStoneInPlace(int i, int j) {
        for (Stone s : stones) {
            if (s.position.i == i && s.position.j == j) {
                return s;
            }
        }
        System.out.println("no stone in this place");
        return null;
    }


    public Stone getStoneById(int id){
        for (Stone s : stones) {
            if (s.id == id) {
                return s;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return "Player{" +
                "stones=" + stones +
                ", id=" + id +
                '}';
    }
}
