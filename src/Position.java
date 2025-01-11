public class Position {
    int i, j;

    public Position(int i, int j) {
        this.i = i;
        this.j = j;
    }

    public void newPosition(int ran) {

        if (i == 0) {
            int dis = 8 - j;
            if (ran <= dis) {
                System.out.println("case 1");
                j += ran;
            } else {
                System.out.println("case 2");
                j = 8;
                i = ran - dis;
                if (i == 6) j = 9;
            }
        } else if (i == 14) {
            int dis = j - 6;
            if (ran <= dis) {
                System.out.println("case 3");
                j -= ran;
            } else {
                System.out.println("case 4");
                j = 6;
                i = 14 - (ran - dis);
                if (i == 8) j = 5;
            }
        } else if (i == 6) {
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
        } else if (i == 8) {
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
        } else if (j == 6) {
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
        } else if (j == 8) {
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
        } else if (j == 0) {
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
        } else if (j == 14) {
            int dis = 8 - i;
            if (ran <= dis) {
                System.out.println("case 19");
                i += ran;
            } else {
                System.out.println("case 20");
                int rest = ran - dis;
                i = 8;
                j -= rest;
                if (j == 8) i++;
            }
        }
    }

    public void checkRestTillKitchen(int ran, int turn) {
        if (turn == 0) {
            if (i == 8 && j <= 5) {
                System.out.println("case 1.1");
                int dis = j;
                if (ran <= dis) {
                    j -= ran;
                } else {
                    int rst = ran - dis - 1;
                    j = rst;
                    i = 7;
                }

            } else if (i == 7 && j <= 5) {
                System.out.println("case 1.1.2");
                int dis = 6 - j;
                if (ran <= dis) {
                    j += ran;
                } else {
                    i = -1;
                    j = -1;
                }
            }

        } else if (turn == 1) {
            if (j == 6 && i <= 5) {
                System.out.println("case 1.2");
                int dis = i;
                if (ran <= dis) {
                    i -= ran;
                } else {
                    int rst = ran - dis - 1;
                    i = rst;
                    j = 7;
                }
            } else if (j == 7 && i <= 5) {
                System.out.println("case 1.2.1");
                int dis = 6 - i;
                if (ran <= dis) {
                    i += ran;
                } else {
                    i = -1;
                    j = -1;
                }
            }
        } else if (turn == 2) {
            if (i == 6 && j >= 9) {
                System.out.println("case 1.3");
                int dis = 14 - j;
                if (ran <= dis) {
                    j += ran;
                } else {
                    int rst = ran - dis - 1;
                    j = 14 - rst;
                    i = 7;
                }
            } else if (i == 7 && j >= 9) {
                System.out.println("case 1.3.1");
                int dis = j - 8;
                if (ran <= dis) {
                    j -= ran;
                } else {
                    i = -1;
                    j = -1;
                }
            }
        } else if (turn == 3) {
            if (j == 8 && i >= 9) {
                System.out.println("case 1.4");
                int dis = 14 - i;
                if (ran <= dis) {
                    i += ran;
                } else {
                    int rst = ran - dis - 1;
                    i = 14 - rst;
                    j = 7;
                }
            } else if (j == 7 && i >= 8) {
                System.out.println("case 1.1.1");
                int dis = i - 8;
                if (ran <= dis) {
                    i -= ran;
                } else {
                    i = -1;
                    j = -1;
                }
            }
        }
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;

        Position position = (Position) object;
        return i == position.i && j == position.j;
    }

    @Override
    public int hashCode() {
        int result = i;
        result = 31 * result + j;
        return result;
    }

    @Override
    public String toString() {
        return " i=" + i +
                ", j=" + j;
    }
}
