import java.util.ArrayList;
import java.util.List;

public class Player {

    int playerID;
    public List<Stone> stones = new ArrayList<>();

    public Player(int playerID) {
        this.playerID = playerID;
        if (playerID == 0) {
            stones.add(new Stone(0, new Position(1, 1), false, false,"red"));
            stones.add(new Stone(1, new Position(1, 4), false, false,"red"));
            stones.add(new Stone(2, new Position(4, 1), false, false,"red"));
            stones.add(new Stone(3, new Position(4, 4), false, false,"red"));
        } else if (playerID == 1) {
            stones.add(new Stone(0, new Position(1, 10), false, false,"green"));
            stones.add(new Stone(1, new Position(1, 13), false, false,"green"));
            stones.add(new Stone(2, new Position(4, 10), false, false,"green"));
            stones.add(new Stone(3, new Position(4, 13), false, false,"green"));

        } else if (playerID == 2) {
            stones.add(new Stone(0, new Position(13, 13), false, false,"yellow"));
            stones.add(new Stone(1, new Position(13, 10), false, false,"yellow"));
            stones.add(new Stone(2, new Position(10, 10), false, false,"yellow"));
            stones.add(new Stone(3, new Position(10, 13), false, false,"yellow"));

        } else if (playerID == 3) {
            stones.add(new Stone(0, new Position(10, 1), false, false,"blue"));
            stones.add(new Stone(1, new Position(10, 4), false, false,"blue"));
            stones.add(new Stone(2, new Position(13, 4), false, false,"blue"));
            stones.add(new Stone(3, new Position(13, 1), false, false,"blue"));
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
            newStones.add(s.deepCopy());
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
            return MoveType.CANT_MOVE;
        }
        if (stones.get(stoneId).checkInKitchen(this.playerID, stones.get(stoneId).position)) {
            stones.remove(getStoneById(stoneId));
            System.out.println("entered the kitchen");
            return MoveType.ENTERED_THE_KITCHEN;
        }
//        System.out.println("the stone has moved to new place " + getStoneById(stoneId));
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

    public Player deepCopy() {
        Player copiedPlayer = new Player(this.playerID);
        copiedPlayer.stones = new ArrayList<>();
        for (Stone stone : this.stones) {
            copiedPlayer.stones.add(stone.deepCopy());
        }
        return copiedPlayer;
    }



    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;

        Player player = (Player) object;
        return playerID == player.playerID && stones.equals(player.stones);
    }

    @Override
    public int hashCode() {
        int result = playerID;
        result = 31 * result + stones.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Player{" +
                "playerId=" + playerID +
                ", stones=" + stones +
                "\n";
    }
}
