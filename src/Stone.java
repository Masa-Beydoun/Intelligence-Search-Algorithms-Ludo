import java.awt.*;

public class Stone {
    int id;
    int i,j;
    Color color;
    boolean Alive;
    boolean locked;

    public Stone(int id, int i, int j, Color color, boolean alive, boolean locked) {
        this.id = id;
        this.i = i;
        this.j = j;
        this.color = color;
        Alive = alive;
        this.locked = locked;
    }

    @Override
    public String toString() {
        return "Stone{" +
                "id=" + id +
                ", i=" + i +
                ", j=" + j +
                ", color=" + color +
                ", Alive=" + Alive +
                ", locked=" + locked +
                '}';
    }
}
