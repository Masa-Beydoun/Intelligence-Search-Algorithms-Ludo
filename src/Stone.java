import java.util.List;
import java.util.Map;

public class Stone {
    int id;
    Position position;
    boolean alive;
    boolean locked;
    String color;

    public Stone(int id, Position position, boolean alive, boolean locked,String color) {
        this.id = id;
        this.position = position;
        this.alive = alive;
        this.locked = locked;
        this.color = color;
    }

    public void updateLocationForKill(int turn) {
        if (turn == 0) {
            if (id == 0) position = new Position(1, 1);
            if (id == 1) position = new Position(1, 4);
            if (id == 2) position = new Position(4, 1);
            if (id == 3) position = new Position(4, 4);
        } else if (turn == 1) {
            if (id == 0) position = new Position(1, 10);
            if (id == 1) position = new Position(1, 13);
            if (id == 2) position = new Position(4, 10);
            if (id == 3) position = new Position(4, 13);
        } else if (turn == 2) {
            if (id == 0) position = new Position(13, 13);
            if (id == 1) position = new Position(13, 10);
            if (id == 2) position = new Position(10, 10);
            if (id == 3) position = new Position(10, 13);
        } else if (turn == 3) {
            if (id == 0) position = new Position(10, 1);
            if (id == 1) position = new Position(10, 4);
            if (id == 2) position = new Position(13, 4);
            if (id == 3) position = new Position(13, 1);
        }
        System.out.println("position after the killing "+position);
        alive = false;
    }

    public Position makeAlive(int turn) {
        if (turn == 0) {
            return new Position(6, 1);
        } else if (turn == 1) {
            return new Position(1, 8);
        } else if (turn == 2) {
            return new Position(8, 13);
        } else {
            return new Position(13, 6);
        }
    }

    public void updateLocked() {
        if (position.i == 6 && (position.j == 1 || position.j == 12)) {
            locked = true;
            return;
        }
        if (position.i == 8 && (position.j == 2 || position.j == 13)) {
            locked = true;
            return;
        }
        if (position.j == 6 && (position.i == 2 || position.i == 13)) {
            locked = true;
            return;
        }
        if (position.j == 8 && (position.i == 1 || position.i == 12)) {
            locked = true;
            return;
        }
        locked = false;
    }

    private static final Map<Integer, Position> KITCHEN_POSITIONS = Map.of(
            0, new Position(7, 6),
            1, new Position(6, 7),
            2, new Position(7, 8),
            3, new Position(8, 7)
    );

    public boolean checkInKitchen(int turn, Position pos) {
        if(turn == 0){
            if(pos.i==7 && pos.j==6){
                return true;
            }
        }
        if(turn == 1){
            if(pos.i==6 && pos.j==7){
                return true;
            }
        }
        if(turn == 2){
            if(pos.i==7 && pos.j==8){
                return true;
            }
        }
        if(turn == 3){
            if(pos.i==8 && pos.j==7){
                return true;
            }
        }
        return false;
    }

    public MoveType fullMove(int ran, int turn, boolean flag,List<Stone> stones) {
        Position pos = move(ran, turn, flag,stones);
        if (pos.equals(new Position(-1, -1))) return MoveType.CANT_MOVE;
        if (checkInKitchen(turn, pos)) {
            if (flag) {
                alive = false;
                position = pos;
            }
            return MoveType.ENTERED_THE_KITCHEN;
        }
        if (flag) {
            position = pos;
        }
        updateLocked();
        return MoveType.MOVED;
    }

    public Position move(int ran, int turn, boolean flag, List<Stone> stones) {
        if (!alive && ran == 6) {
//            System.out.println("not alive random 6");
            Position pos = makeAlive(turn);
            if (flag) {
                alive = true;
                locked = true;
            }
            return pos;
        }
        if (!alive && ran != 6) {
//            System.out.println("not alive random not 6");
            return new Position(-1, -1);
        }
//        System.out.println("checking new position");
        Position newPos = new Position(position.i, position.j);
        newPos.checkRestTillKitchen(ran, turn);
        if (newPos.equals(new Position(-1, -1))) return newPos;

//        System.out.println("new position " + newPos);
        if (!newPos.equals(position)) {
            return newPos;
        }

        newPos.i = position.i;
        newPos.j = position.j;

        newPos.canMove(ran, stones);
        return newPos;
    }
    public Stone deepCopy() {
        return new Stone(
                this.id,
                new Position(this.position.i, this.position.j),
                this.alive,
                this.locked,
                this.color
        );
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;

        Stone stone = (Stone) object;
        return id == stone.id && alive == stone.alive && locked == stone.locked && position.equals(stone.position) && color.equals(stone.color);
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + position.hashCode();
        result = 31 * result + Boolean.hashCode(alive);
        result = 31 * result + Boolean.hashCode(locked);
        return result;
    }




    @Override
    public String toString() {
        return "{" +
                "id=" + id +
                "," + position.i +
                "," + position.j +
                ", Alive=" + alive +
                ", locked=" + locked +
                "}\n";
    }
}
