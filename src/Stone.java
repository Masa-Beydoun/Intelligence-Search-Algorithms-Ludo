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

    public Position checkRestTillKitchen(int ran, int turn) {
        Position pos = new Position(position.i, position.j);
        if (turn == 0) {
            if (position.i == 8 && position.j <= 5) {
                System.out.println("case 1.1");
                int dis = position.j;
                if (ran <= dis) {
                    pos.j -= ran;
                } else {
                    int rst = ran - dis - 1;
                    pos.j = rst;
                    pos.i = 7;
                }
                return pos;
            }
            if (position.i == 7 && position.j <= 5) {
                System.out.println("case 1.1.2");
                int dis = 6 - position.j;
                if (ran <= dis) {
                    pos.j += ran;
                    return pos;
                } else {
                    return new Position(-1, -1);
                }
            }

        } else if (turn == 1) {
            if (position.j == 6 && position.i <= 5) {
                System.out.println("case 1.2");
                int dis = position.i;
                if (ran <= dis) {
                    pos.i -= ran;
                } else {
                    int rst = ran - dis - 1;
                    pos.i = rst;
                    pos.j = 7;
                }
                return pos;
            }
            if (position.j == 7 && position.i <= 5) {
                System.out.println("case 1.2.1");
                int dis = 6 - position.i;
                if (ran <= dis) {
                    pos.i += ran;
                    return pos;
                } else {
                    return new Position(-1, -1);
                }
            }
        } else if (turn == 2) {
            if (position.i == 6 && position.j >= 9) {
                System.out.println("case 1.3");
                int dis = 14 - position.j;
                if (ran <= dis) {
                    pos.j += ran;
                } else {
                    int rst = ran - dis - 1;
                    pos.j = 14 - rst;
                    pos.i = 7;
                }
                return pos;
            }
            if (position.i == 7 && position.j >= 9) {
                System.out.println("case 1.3.1");
                int dis = position.j - 8;
                if (ran <= dis) {
                    pos.j -= ran;
                    return pos;
                } else {
                    return new Position(-1, -1);
                }
            }
        } else if (turn == 3) {
            if (position.j == 8 && position.i >= 9) {
                System.out.println("case 1.4");
                int dis = 14 - position.i;
                if (ran <= dis) {
                    pos.i += ran;
                } else {
                    int rst = ran - dis - 1;
                    pos.i = 14 - rst;
                    pos.j = 7;
                }
                return pos;
            }
            if (position.j == 7 && position.i >= 8) {
                System.out.println("case 1.1.1");
                int dis = position.i - 8;
                if (ran <= dis) {
                    pos.i -= ran;
                    return pos;
                } else {
                    return new Position(-1, -1);
                }
            }
        }
        return pos;
    }

    public void updateLocked() {
        if (position.i == 6 && (position.j == 1 || position.j == 12)) locked = true;
        if (position.i == 8 && (position.j == 2 || position.j == 13)) locked = true;
        if (position.j == 6 && (position.i == 2 || position.i == 13)) locked = true;
        if (position.j == 8 && (position.i == 1 || position.i == 12)) locked = true;

    }

    public boolean checkInKitchen(int turn,Position pos) {
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

    public MoveType move(int ran, int turn, boolean flag) {
        // parameters = ran : random nerd number , turn : whose turn , flag : do i want to make the move or only check it.
        System.out.println(this);
        if (!alive && ran == 6) {
            System.out.println("not alive random 6");
            Position pos = makeAlive(turn);
            if (flag) {
                alive = true;
                locked = true;
                position.i = pos.i;
                position.j = pos.j;
            }
            return MoveType.MOVED;
        }
        if (!alive && ran != 6) {
            System.out.println("not alive random not 6");
            return MoveType.CANT_MOVE;
        }
        System.out.println("checking new position");
        Position newPos = checkRestTillKitchen(ran, turn);
        if (newPos.equals(new Position(-1, -1))) return MoveType.CANT_MOVE;
        if (checkInKitchen(turn,newPos)){
            if(flag){
                alive = false;
                position = newPos;
                return MoveType.MOVED;
            }
        }
        System.out.println("new position " + newPos);
        if (!newPos.equals(position)) {
            if (flag) {
                position = newPos;
            }
            return MoveType.MOVED;
        }

        newPos.i = position.i;
        newPos.j = position.j;

        if (newPos.i == 0) {
            int dis = 8 - position.j;
            if (ran <= dis) {
                System.out.println("case 1");
                newPos.j += ran;
            } else {
                System.out.println("case 2");
                newPos.j = 8;
                newPos.i = ran - dis;
                if (newPos.i == 6) newPos.j = 9;
            }
        } else if (newPos.i == 14) {
            int dis = newPos.j - 6;
            if (ran <= dis) {
                System.out.println("case 3");
                newPos.j -= ran;
            } else {
                System.out.println("case 4");
                newPos.j = 6;
                newPos.i = 14 - (ran - dis);
                if (newPos.i == 8) newPos.j = 5;
            }
        } else if (newPos.i == 6) {
            if ((newPos.j + ran <= 5 && newPos.j < 5) || (newPos.j >= 9 && newPos.j + ran < 15)) {
                System.out.println("case 5");
                newPos.j = newPos.j + ran;
            } else {
                if (newPos.j < 7) {
                    System.out.println("case 6");
                    int dis = 5 - newPos.j;
                    newPos.j = 6;
                    newPos.i = newPos.i - (ran - dis);
                } else {
                    System.out.println("case 7");
                    int dis = 14 - newPos.j;
                    newPos.j = 14;
                    int rest = ran - dis;
                    if (rest <= 2) {
                        newPos.i += rest;
                    } else {
                        newPos.i = 8;
                        rest -= 2;
                        newPos.j = 14 - rest;
                    }
                }
            }
        } else if (newPos.i == 8) {
            if ((newPos.j <= 5 && newPos.j - ran >= 0) || (newPos.j > 8 && newPos.j - ran > 8)) {
                System.out.println("case 8");
                newPos.j = newPos.j - ran;
            } else {
                if (newPos.j > 7) {
                    System.out.println("case 9");
                    int dis = newPos.j - 9;
                    newPos.j = 8;
                    newPos.i = 8 + (ran - dis);
                } else {
                    System.out.println("case 10");
                    int dis = newPos.j;
                    newPos.j = 0;
                    int rest = ran - dis;
                    if (rest <= 2) {
                        newPos.i -= rest;
                    } else {
                        newPos.i = 6;
                        rest -= 2;
                        newPos.j = rest;

                    }
                }
            }
        } else if (newPos.j == 6) {
            if ((newPos.i < 6 && newPos.i - ran >= 0) || (newPos.i > 8 && newPos.i - ran > 8)) {
                System.out.println("case 11");
                newPos.i = newPos.i - ran;
            } else {
                if (newPos.i < 7) {
                    System.out.println("case 12");
                    int dis = ran - newPos.i;
                    newPos.i = 0;
                    if (dis <= 2)
                        newPos.j += dis;
                    else {
                        newPos.j += 2;
                        dis = dis - 2;
                        newPos.i = dis;
                    }
                } else {
                    System.out.println("case 13");
                    int dis = newPos.i - 8;
                    newPos.i = 8;
                    newPos.j = 5 - (ran - dis);
                }
            }
        } else if (newPos.j == 8) {
            if ((newPos.i + ran < 6 && newPos.i < 6) || (newPos.i > 8 && newPos.i + ran < 15)) {
                System.out.println("case 14");
                newPos.i = newPos.i + ran;
            } else {
                if (newPos.i < 7) {
                    System.out.println("case 15");
                    int dis = 5 - newPos.i; // 4
                    newPos.i = 6;
                    newPos.j = 8 + (ran - dis);
                } else {
                    System.out.println("case 16");
                    int dis = 14 - newPos.i;
                    newPos.i = 14;
                    int rest = ran - dis;
                    if (rest <= 2) {
                        newPos.j -= rest;
                    } else {
                        newPos.j -= 2;
                        rest -= 2;
                        newPos.i = 14 - rest;
                    }
                }
            }
        } else if (newPos.j == 0) {
            int dis = 8 - newPos.i;
            if (ran <= dis) {
                System.out.println("case 17");
                newPos.i -= ran;
            } else {
                System.out.println("case 18");
                newPos.i = 6;
                int rest = ran - dis;
                newPos.j = rest;
            }
        } else if (newPos.j == 14) {
            int dis = 8 - newPos.i;
            if (ran <= dis) {
                System.out.println("case 19");
                newPos.i += ran;
            } else {
                System.out.println("case 20");
                int rest = ran - dis;
                newPos.i = 8;
                newPos.j -= rest;
                if (newPos.j == 8) newPos.i++;
            }
        }
        if (flag) {
            position.i = newPos.i;
            position.j = newPos.j;
        }
        return MoveType.MOVED;
    }

    @Override
    public String toString() {
        return "Stone{" +
                "id=" + id +
                ", " + position.i +
                ", " + position.j +
                ", Alive=" + alive +
                ", locked=" + locked +
                '}';
    }
}
