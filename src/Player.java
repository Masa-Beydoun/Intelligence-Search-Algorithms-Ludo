import java.util.ArrayList;
import java.util.List;

public class Player {

    int playerID;
    public List<Stone> stones = new ArrayList<>();

    public Player(int playerID) {
        this.playerID = playerID;
        if (playerID == 0) {
            stones.add(new Stone(0, new Position(1, 1), false, false));
            stones.add(new Stone(1, new Position(1, 4), false, false));
            stones.add(new Stone(2, new Position(4, 1), false, false));
            stones.add(new Stone(3, new Position(4, 4), false, false));
        } else if (playerID == 1) {
            stones.add(new Stone(0, new Position(1, 10), false, false));
            stones.add(new Stone(1, new Position(1, 13), false, false));
            stones.add(new Stone(2, new Position(4, 10), false, false));
            stones.add(new Stone(3, new Position(4, 13), false, false));

        } else if (playerID == 2) {
            stones.add(new Stone(0, new Position(13, 13), false, false));
            stones.add(new Stone(1, new Position(13, 10), false, false));
            stones.add(new Stone(2, new Position(10, 10), false, false));
            stones.add(new Stone(3, new Position(10, 13), false, false));

        } else if (playerID == 3) {
            stones.add(new Stone(0, new Position(10, 1), false, false));
            stones.add(new Stone(1, new Position(10, 4), false, false));
            stones.add(new Stone(2, new Position(13, 4), false, false));
            stones.add(new Stone(3, new Position(13, 1), false, false));
        }
    }

    public void updateLocked() {
        if (!stones.isEmpty()) stones.get(0).updateLocked();
        if (stones.size() > 1) stones.get(1).updateLocked();
        if (stones.size() > 2) stones.get(2).updateLocked();
        if (stones.size() > 3) stones.get(3).updateLocked();
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

    public boolean thereIsMove(int ran,List<Stone> stones) {
        List<Stone> newStones = new ArrayList<>();
        for (Stone s : this.stones) {
            newStones.add(new Stone(s.id, s.position, s.alive, s.locked));
        }

        for (Stone s : newStones) {
            MoveType moveType = s.fullMove(ran, playerID, false,stones);
            if (moveType == MoveType.MOVED || moveType == MoveType.ENTERED_THE_KITCHEN) {
                return true;
            }
        }
        return false;
    }

    public MoveType move(int stoneId, int ran, boolean flag,List<Stone> stones) {
        MoveType type = getStoneById(stoneId).fullMove(ran, this.playerID, flag, stones);
        if (type == MoveType.CANT_MOVE) {
//            System.out.println("stone can't move");
            return MoveType.CANT_MOVE;
        }
        if (this.stones.get(stoneId).checkInKitchen(this.playerID, this.stones.get(stoneId).position)) {
            this.stones.remove(getStoneById(stoneId));
//            System.out.println("stone entered the kitchen");
            return MoveType.ENTERED_THE_KITCHEN;
        }
        System.out.println("the stone has moved to new place " + getStoneById(stoneId));
        updateLocked();
        return MoveType.MOVED;
    }

    public List<Stone> getStoneInPlace(int i, int j) {
        List<Stone> stonesInPlace = new ArrayList<>();
        for (Stone s : stones) {
            if (s.position.i == i && s.position.j == j) {
                stonesInPlace.add(s);
            }
        }
        return stonesInPlace;
    }

    public Stone getStoneById(int id) {
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
                "playerId=" + playerID +
                ", stones=" + stones +
                "\n";
    }
}
