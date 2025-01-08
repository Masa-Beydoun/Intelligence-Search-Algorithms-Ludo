import java.util.Arrays;
import java.util.List;

public class Player {

    public Stone[] stones = new Stone[4];
    int id;

    public Player(int id) {
        this.id = id;
        if (id == 0) {
            stones[0] = new Stone(0, new Position(1, 1), false, false, false);
            stones[1] = new Stone(1, new Position(1, 4), false, false, false);
            stones[2] = new Stone(2, new Position(4, 1), false, false, false);
            stones[3] = new Stone(3, new Position(4, 4), false, false, false);
        } else if (id == 1) {
            stones[0] = new Stone(0, new Position(1, 10), false, false, false);
            stones[1] = new Stone(1, new Position(1, 13), false, false, false);
            stones[2] = new Stone(2, new Position(4, 10), false, false, false);
            stones[3] = new Stone(3, new Position(4, 13), false, false, false);

        } else if (id == 2) {
            stones[0] = new Stone(0, new Position(13, 13), false, false, false);
            stones[1] = new Stone(1, new Position(13, 10), false, false, false);
            stones[2] = new Stone(2, new Position(10, 10), false, false, false);
            stones[3] = new Stone(3, new Position(10, 13), false, false, false);

        } else if (id == 3) {
            stones[0] = new Stone(0, new Position(10, 1), false, false, false);
            stones[1] = new Stone(1, new Position(10, 4), false, false, false);
            stones[2] = new Stone(2, new Position(13, 4), false, false, false);
            stones[3] = new Stone(3, new Position(13, 1), false, false, false);
        }
    }

    public void updateLocked() {
        stones[0].updateLocked();
        stones[1].updateLocked();
        stones[2].updateLocked();
        stones[3].updateLocked();
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (i != j && stones[i].position.i == stones[j].position.i
                        && stones[i].position.j == stones[j].position.j) {
                    stones[i].locked = true;
                    stones[j].locked = true;
                }
            }
        }
    }

    public boolean thereIsMove(int ran) {
        List<Stone> newStones = List.of(
                new Stone(stones[0].id, stones[0].position, stones[0].alive, stones[0].locked, stones[0].locked),
                new Stone(stones[1].id, stones[1].position, stones[1].alive, stones[1].locked, stones[1].locked),
                new Stone(stones[2].id, stones[2].position, stones[2].alive, stones[2].locked, stones[2].locked),
                new Stone(stones[3].id, stones[3].position, stones[3].alive, stones[3].locked, stones[3].locked)
        );
        for (Stone s : newStones) {
            if (s.move(ran, id))
                return true;
        }
        return false;
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

    @Override
    public String toString() {
        return "Player{" +
                "stones=" + Arrays.toString(stones) +
                ", id=" + id +
                '}';
    }
}
