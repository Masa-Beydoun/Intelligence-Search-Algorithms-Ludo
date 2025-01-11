public class Stone {
    int id;
    Position position;
    boolean alive;
    boolean locked;

    public Stone(int id, Position position, boolean alive, boolean locked) {
        this.id = id;
        this.position = position;
        this.alive = alive;
        this.locked = locked;
    }

    public void updateLocationById(int playerID) {
        if (playerID == 0) {
            if (id == 0) position = new Position(1, 1);
            if (id == 1) position = new Position(1, 4);
            if (id == 2) position = new Position(4, 1);
            if (id == 3) position = new Position(4, 4);
        } else if (playerID == 1) {
            if (id == 0) position = new Position(1, 10);
            if (id == 1) position = new Position(1, 13);
            if (id == 2) position = new Position(4, 10);
            if (id == 3) position = new Position(4, 13);
        } else if (playerID == 2) {
            if (id == 0) position = new Position(13, 13);
            if (id == 1) position = new Position(13, 10);
            if (id == 2) position = new Position(10, 10);
            if (id == 3) position = new Position(10, 13);
        } else if (playerID == 3) {
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

    public boolean checkInKitchen(int turn, Position pos) {
        if (turn == 0 && (pos.i == 7 && pos.j == 6)) {
            return true;
        } else if (turn == 1 && (pos.i == 6 && pos.j == 7)) {
            return true;
        } else if (turn == 2 && (pos.i == 7 && pos.j == 8)) {
            return true;
        } else if (turn == 3 && (pos.i == 8 && pos.j == 7)) {
            return true;
        }
        return false;
    }

    public MoveType fullMove(int ran, int turn, boolean flag) {
        Position pos = move(ran, turn, flag);
        if (pos.equals(new Position(-1, -1))) return MoveType.CANT_MOVE;
        if (checkInKitchen(turn, pos)) {
            if (flag) {
                alive = false;
                position = pos;
                return MoveType.ENTERED_THE_KITCHEN;
            }
        }
        if (flag) {
            position = pos;
        }
        updateLocked();
        return MoveType.MOVED;
    }

    //gets where the stone goes
    public Position move(int ran, int turn, boolean flag) {
        // parameters = ran : random nerd number , turn : whose turn , flag : do i want to make the move or only check it.
        if (!alive && ran == 6) {
            System.out.println("not alive random 6");
            Position pos = makeAlive(turn);
            if (flag) {
                alive = true;
                locked = true;
            }
            return pos;
        }
        if (!alive && ran != 6) {
            System.out.println("not alive random not 6");
            return new Position(-1, -1);
        }
        System.out.println("checking new position");
        Position newPos = new Position(position.i, position.j);
        newPos.checkRestTillKitchen(ran, turn);
        if (newPos.equals(new Position(-1, -1))) return newPos;

        System.out.println("new position " + newPos);
        if (!newPos.equals(position)) {
            return newPos;
        }

        newPos.i = position.i;
        newPos.j = position.j;

        newPos.newPosition(ran);
        return newPos;
    }

    @Override
    public String toString() {
        return "{" +
                "id=" + id +
                "," + position.i +
                "," + position.j +
                ", Alive=" + alive +
                ", locked=" + locked +
                '}';
    }
}
