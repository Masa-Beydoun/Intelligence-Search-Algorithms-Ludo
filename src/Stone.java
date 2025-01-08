
public class Stone {
    int id;
    Position position;
    boolean alive;
    boolean locked;
    boolean finished = false;

    public Stone(int id,Position position, boolean alive, boolean locked, boolean finished) {
        this.id = id;
        this.position = position;
        this.alive = alive;
        this.locked = locked;
        this.finished = finished;
    }

    public void makeAlive(int turn) {
        if (turn == 0) {
            position.i= 6;
            position.j = 1;
        } else if (turn == 1) {
            position.i= 1;
            position.j = 8;
        } else if (turn == 2) {
            position.i= 8;
            position.j = 13;
        } else {
            position.i= 13;
            position.j = 6;
        }
        alive = true;
        locked = true;

    }



    public int checkRestTillKitchen(int ran, int turn) {
        if (turn == 0) {
            if (position.i== 8 && position.j <= 5) {
                System.out.println("case 1.1");
                int dis = position.j;
                if (ran <= dis) {
                    position.j -= ran;
                } else {
                    int rst = ran - dis - 1;
                    position.j = rst;
                    position.i= 7;
                }
                return 1;
            }
            if (position.i== 7 && position.j <= 5) {
                System.out.println("case 1.1.2");
                int dis = 6 - position.j;
                if (ran <= dis) {
                    position.j += ran;
                    return 1;
                } else {
                    return -1;
                }
            }

        } else if (turn == 1) {
            if (position.j == 6 && position.i<= 5) {
                System.out.println("case 1.2");
                int dis = position.i;
                if (ran <= dis) {
                    position.i-= ran;
                } else {
                    int rst = ran - dis - 1;
                    position.i= rst;
                    position.j = 7;
                }
                return 1;
            }
            if (position.j == 7 && position.i<= 5) {
                System.out.println("case 1.2.1");
                int dis = 6 - position.i;
                if (ran <= dis) {
                    position.i+= ran;
                    return 1;
                } else {
                    return -1;
                }
            }
        } else if (turn == 2) {
            if (position.i== 6 && position.j >= 9) {
                System.out.println("case 1.3");
                int dis = 14 - position.j;
                if (ran <= dis) {
                    position.j += ran;
                } else {
                    int rst = ran - dis - 1;
                    position.j = 14 - rst;
                    position.i= 7;
                }
                return 1;
            }
            if (position.i== 7 && position.j >= 9) {
                System.out.println("case 1.3.1");
                int dis = position.j - 8;
                if (ran <= dis) {
                    position.j -= ran;
                    return 1;
                } else {
                    return -1;
                }
            }
        } else if (turn == 3) {
            if (position.j == 8 && position.i>= 9) {
                System.out.println("case 1.4");
                int dis = 14 - position.i;
                if (ran <= dis) {
                    position.i+= ran;
                } else {
                    int rst = ran - dis - 1;
                    position.i= 14 - rst;
                    position.j = 7;
                }
                return 1;
            }
            if (position.j == 7 && position.i>= 8) {
                System.out.println("case 1.1.1");
                int dis = position.i- 8;
                if (ran <= dis) {
                    position.i-= ran;
                    return 1;
                } else {
                    return -1;
                }
            }
        }
        return 0;
    }


    public void updateLocked() {
        if (position.i== 6 && (position.j == 1 || position.j == 12)) locked = true;
        if (position.i== 8 && (position.j == 2 || position.j == 13)) locked = true;
        if (position.j == 6 && (position.i== 2 || position.i== 13)) locked = true;
        if (position.j == 8 && (position.i== 1 || position.i== 12)) locked = true;

    }

    public void checkInKitchen(int turn) {
        if (turn == 0 && (position.i== 7 && position.j == 6)) {
            finished = true;
            alive = false;
        } else if (turn == 1 && (position.i== 6 && position.j == 7)) {
            finished = true;
            alive = false;
        } else if (turn == 2 && (position.i== 7 && position.j == 8)) {
            finished = true;
            alive = false;
        } else if (turn == 3 && (position.i== 8 && position.j == 7)) {
            finished = true;
            alive = false;
        }
    }

    public boolean move(int ran, int turn) {
        if (finished) return false;
        //if the stone is not alive then make it alive
        if (!alive && ran == 6) {
            makeAlive(turn);
            return true;
        }
        if (!alive && ran != 6) {
            return false;
        }
        if (checkRestTillKitchen(ran, turn) == 1) return true;
        if (checkRestTillKitchen(ran, turn) == -1) return false;

        if (position.i== 0) {
            int dis = 8 - position.j;
            if (ran <= dis) {
                System.out.println("case 1");
                position.j += dis;
            } else {
                System.out.println("case 2");
                position.j = 8;
                position.i= ran - dis;
                if (position.i== 6) position.j = 9;
            }
        } else if (position.i== 14) {
            int dis = position.j - 6;
            if (ran <= dis) {
                System.out.println("case 3");
                position.j -= dis;
            } else {
                System.out.println("case 4");
                position.j = 6;
                position.i= 14 - (ran - dis);
                if (position.i== 8) position.j = 5;
            }
        } else if (position.i== 6) {
            if ((position.j + ran <= 5 && position.j < 5) || (position.j >= 9 && position.j + ran < 15)) {
                System.out.println("case 5");
                position.j = position.j + ran;
            } else {
                if (position.j < 7) {
                    System.out.println("case 6");
                    int dis = 5 - position.j;
                    position.j = 6;
                    position.i= position.i- (ran - dis);
                } else {
                    System.out.println("case 7");
                    int dis = 14 - position.j;
                    position.j = 14;
                    int rest = ran - dis;
                    if (rest <= 2) {
                        position.i+= rest;
                    } else {
                        position.i= 8;
                        rest -= 2;
                        position.j = 14 - rest;
                    }
                }
            }
        } else if (position.i== 8) {
            if ((position.j <= 5 && position.j - ran >= 0) || (position.j > 8 && position.j - ran > 8)) {
                System.out.println("case 8");
                position.j = position.j - ran;
            } else {
                if (position.j > 7) {
                    System.out.println("case 9");
                    int dis = position.j - 9;
                    position.j = 8;
                    position.i= 8 + (ran - dis);
                } else {
                    System.out.println("case 10");
                    int dis = position.j;
                    position.j = 0;
                    int rest = ran - dis;
                    if (rest <= 2) {
                        position.i-= rest;
                    } else {
                        position.i= 6;
                        rest -= 2;
                        position.j = rest;

                    }
                }
            }
        } else if (position.j == 6) {
            if ((position.i< 6 && position.i- ran >= 0) || (position.i> 8 && position.i- ran > 8)) {
                System.out.println("case 11");
                position.i= position.i- ran;
            } else {
                if (position.i< 7) {
                    System.out.println("case 12");
                    int dis = ran - position.i;
                    position.i= 0;
                    if (dis <= 2)
                        position.j += dis;
                    else {
                        position.j += 2;
                        dis = dis - 2;
                        position.i= dis;
                    }
                } else {
                    System.out.println("case 13");
                    int dis = position.i- 8;
                    position.i= 8;
                    position.j = 5 - (ran - dis);
                }
            }
        } else if (position.j == 8) {
            if ((position.i+ ran < 6 && position.i< 6) || (position.i> 8 && position.i+ ran < 15)) {
                System.out.println("case 14");
                position.i= position.i+ ran;
            } else {
                if (position.i< 7) {
                    System.out.println("case 15");
                    int dis = 5 - position.i; // 4
                    position.i= 6;
                    position.j = 8 + (ran - dis);
                } else {
                    System.out.println("case 16");
                    int dis = 14 - position.i;
                    position.i= 14;
                    int rest = ran - dis;
                    if (rest <= 2) {
                        position.j -= rest;
                    } else {
                        position.j -= 2;
                        rest -= 2;
                        position.i= 14 - rest;
                    }
                }
            }
        } else if (position.j == 0) {
            int dis = 8 - position.i;
            if (ran <= dis) {
                System.out.println("case 17");
                position.i-= ran;
            } else {
                System.out.println("case 18");
                position.i= 6;
                int rest = ran - dis;
                position.j = rest;
            }
        } else if (position.j == 14) {
            int dis = 8 - position.i;
            if (ran <= dis) {
                System.out.println("case 19");
                position.i+= ran;
            } else {
                System.out.println("case 20");
                int rest = ran - dis;
                position.i= 8;
                position.j -= rest;
                if (position.j == 8) position.i++;
            }
        }

        return true;
    }

    @Override
    public String toString() {
        return "Stone{" +
                "id=" + id +
                ", i=" + position.i+
                ", position.j=" + position.j +
                ", Alive=" + alive +
                ", locked=" + locked +
                '}';
    }
}
