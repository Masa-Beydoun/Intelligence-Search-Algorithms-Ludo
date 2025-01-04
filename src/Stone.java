import java.awt.*;

public class Stone {
    int id;
    int i, j;
    Color color;
    boolean alive;
    boolean locked;

    public Stone(int id, int i, int j, Color color, boolean alive, boolean locked) {
        this.id = id;
        this.i = i;
        this.j = j;
        this.color = color;
        this.alive = alive;
        this.locked = locked;
    }

    public void makeAlive() {
        if (id < 4) {
            i = 6;
            j = 1;
            alive = true;
            locked = true;
        } else if (id < 8) {
            i = 1;
            j = 8;
            alive = true;
            locked = true;
        } else if (id < 12) {
            i = 8;
            j = 13;
            alive = true;
            locked = true;
        } else {
            i = 13;
            j = 6;
            alive = true;
            locked = true;
        }
    }

    public void move(int ran) {
        //if the stone is not alive then make it alive
        if (!alive && ran == 6) {
            makeAlive();
            return;
        }
        if (!alive && ran != 6) {
            return;
        }
        //if the stone is alive check how it can walk
//        int i = i;
//        int j = j;

        if (i == 0) {
            int dis = 8 - j;
            if (ran <= dis) {
                System.out.println("case 1");
                j += dis;
            } else {
                System.out.println("case 2");
                j = 8;
                i = ran - dis;
                if (i == 6) j = 9;
            }
            return;
        }
        if (i == 14) {
            int dis = j - 6;
            if (ran <= dis) {
                System.out.println("case 3");
                j -= dis;
            } else {
                System.out.println("case 4");
                j = 6;
                i = 14 - (ran - dis);
                if (i == 8) j = 5;
            }
            return;
        }

        if (i == 6) {
            if ((j + ran <= 5 && j < 5) || (j >= 9 && j + ran < 15)) {
                System.out.println("case 5");
                j = j + ran;
            } else {
                if (j < 7) {
                    System.out.println("case 6");
                    int dis = 5 - j;
                    j = 6;
                    i = i - (ran - dis);
                } else {
                    System.out.println("case 7");
                    int dis = 14 - j;
                    j = 14;
                    int rest = ran - dis;
                    if (rest <= 2) {
                        i += rest;
                    } else {
                        i = 8;
                        rest -= 2;
                        j = 14 - rest;
                    }
                }
            }
            return;
        }
        if (i == 8) {
            if ((j <= 5 && j - ran >= 0) || (j > 8 && j - ran > 8)) {
                System.out.println("case 8");
                j = j - ran;
            } else {
                if (j > 7) {
                    System.out.println("case 9");
                    int dis = j - 9;
                    j = 8;
                    i = 8 + (ran - dis);
                } else {
                    System.out.println("case 10");
                    int dis = j;
                    j = 0;
                    int rest = ran - dis;
                    if (rest <= 2) {
                        i -= rest;
                    } else {
                        i = 6;
                        rest -= 2;
                        j = rest;

                    }
                }
            }
            return;
        }

        if (j == 6) {
            if ((i < 6 && i - ran >= 0) || (i > 8 && i - ran > 8)) {
                System.out.println("case 11");
                i = i - ran;
            } else {
                if (i < 7) {
                    System.out.println("case 12");
                    int dis = ran - i;
                    i = 0;
                    if (dis <= 2)
                        j += dis;
                    else {
                        j += 2;
                        dis = dis - 2;
                        i = dis;
                    }
                } else {
                    System.out.println("case 13");
                    int dis = i - 8;
                    i = 8;
                    j = 5 - (ran - dis);
                }
            }
            return;
        }
        if (j == 8) {
            if ((i + ran < 6 && i < 6) || (i > 8 && i + ran < 15)) {
                System.out.println("case 14");
                i = i + ran;
            } else {
                if (i < 7) {
                    System.out.println("case 15");
                    int dis = 5 - i; // 4
                    i = 6;
                    j = 8 + (ran - dis);
                } else {
                    System.out.println("case 16");
                    int dis = 14 - i;
                    i = 14;
                    int rest = ran - dis;
                    if (rest <= 2) {
                        j -= rest;
                    } else {
                        j -= 2;
                        rest -= 2;
                        i = 14 - rest;
                    }
                }
            }
            return;
        }
        if (j == 0) {

            int dis = 8 - i;
            if (ran <= dis) {
                System.out.println("case 17");
                i -= ran;
            } else {
                System.out.println("case 18");
                i = 6;
                int rest = ran - dis;
                j = rest;
            }
            return;
        }
        if(j==14){
            int dis = 8 - i;
            if (ran <= dis) {
                System.out.println("case 19");
                i+=ran;
            }
            else{
                System.out.println("case 20");
                int rest = ran - dis;
                i=8;
                j-=rest;
                if(j==8) i++;
            }
        }


    }

    @Override
    public String toString() {
        return "Stone{" +
                "id=" + id +
                ", i=" + i +
                ", j=" + j +
                ", color=" + color +
                ", Alive=" + alive +
                ", locked=" + locked +
                '}';
    }
}
