import java.util.List;

public class Position {
    int i, j;

    public Position(int i, int j) {
        this.i = i;
        this.j = j;
    }

    private boolean checkCoupleCell(int i, int j, List<Stone> stoneList) {
        int count = 0;
        for (Stone stone : stoneList) {
            if (stone.position.i == i && stone.position.j == j) {
                count++;
            }
        }
        return !(count > 1);
    }
    private boolean checkSingleCell(int i, int j, List<Stone> stoneList) {
        for (Stone stone : stoneList) {
            if (stone.position.i == i && stone.position.j == j) {
                return true;
            }
        }
        return false;
    }

    public boolean getPrevious6Cells(int i, int j, List<Stone> stoneList) {
        boolean flag = true;
        if (i == 7 && j == 0) {
            i = 8;
            while (j <= 5) {
                flag &= checkSingleCell(i, j, stoneList);
                j++;
            }
        }  // true
        else if (i == 7 && j == 14) {
            i = 6;
            while (j >= 9) {
                flag &= checkSingleCell(i, j, stoneList);
                j--;
            }
        }  // true
        else if (i == 14 && j == 7 ) {
            j = 8;
            while (i >= 9) {
                flag &= checkSingleCell(i, j, stoneList);
                i--;
            }
        } //true
        else if (i == 0 && j == 7) {
            j = 6;
            while (i <= 5) {
                flag &= checkSingleCell(i, j, stoneList);
                i++;
            }
        } //true
        ////////////////////////////////////////////
        else if (i >= 9 && j == 6) {
            while (i <= 14) {
                flag &= checkSingleCell(i, j, stoneList);
                i++;
            }
            flag &= checkSingleCell(14, 6, stoneList);
            flag &= checkSingleCell(14, 7, stoneList);
            flag &= checkSingleCell(14, 8, stoneList);

            i = 14;
            j = 8;
            while (i >= 9) {
                flag &= checkSingleCell(i, j, stoneList);
                i--;
            }
        }  // true
        else if (i == 8 && j <= 5) {
            while (j <= 5) {
                flag &= checkSingleCell(i, j, stoneList);
                j++;
            }
            i = 9;
            j = 6;
            while (i <= 14) {
                flag &= checkSingleCell(i, j, stoneList);
                i++;
            }
        }  // true
        else if (i == 6 && j <= 5) {
            while (j >= 0) {
                flag &= checkSingleCell(i, j, stoneList);
                j--;
            }
            flag &= checkSingleCell(6, 0, stoneList);
            flag &= checkSingleCell(7, 0, stoneList);
            flag &= checkSingleCell(8, 0, stoneList);
            i = 8;
            j = 0;
            while (j <= 5) {
                flag &= checkSingleCell(i, j, stoneList);
                j++;
            }
        }  // true
        else if (i <= 5 && j == 6) {
            while (i <= 5) {
                flag &= checkSingleCell(i, j, stoneList);
                i++;
            }
            i = 6;
            j = 5;
            while (j >= 0) {
                flag &= checkSingleCell(i, j, stoneList);
                j--;
            }
        }  // true
        else if (i <= 5 && j == 8) {
            while (i >= 0) {
                flag &= checkSingleCell(i, j, stoneList);
                i--;
            }
            flag &= checkSingleCell(0, 8, stoneList);
            flag &= checkSingleCell(0, 7, stoneList);
            flag &= checkSingleCell(0, 6, stoneList);
            i = 0;
            j = 6;
            while (i <= 5) {
                flag &= checkSingleCell(i, j, stoneList);
                i++;
            }
        }  // true
        else if (i == 6 && j >= 9) {
            while (j >= 9) {
                flag &= checkSingleCell(i, j, stoneList);
                j--;
            }
            i = 5;
            j = 8;
            while (i >= 0) {
                flag &= checkSingleCell(i, j, stoneList);
                i--;
            }

        }  // true
        else if (i == 8 && j >= 9) {
            while (j <= 14) {
                flag &= checkSingleCell(i, j, stoneList);
                j++;
            }
            i = 6;
            j = 14;
            flag &= checkSingleCell(7, 14, stoneList);
            while (j >= 9) {
                flag &= checkSingleCell(i, j, stoneList);
                j--;
            }
            //////////////
        }  // true
        else if (i >= 9 && j == 8) {
            while (i >= 9) {
                flag &= checkSingleCell(i, j, stoneList);
                i--;
            }
            i = 8;
            j = 9;
            while (j <= 14) {
                flag &= checkSingleCell(i,j, stoneList);
                j++;
            }
        }  // true
        return flag;
    }


    public void canMove(int ran ,List<Stone> stoneList) {
        boolean flag = true;
        if (i == 6 && j <= 5) {
            System.out.println("case 1");
            while (ran != 0) {
                if (j != 6) {
                    flag &= checkCoupleCell(i, j, stoneList);
                    j++;
                    if (j == 6) {
                        i = 5;
                    }
                } else {
                    flag &= checkCoupleCell(i, j, stoneList);
                    i--;
                }
                ran--;
            }
        }
        else if (j == 6 && i <= 5) {
            System.out.println("case 2");
            while (ran != 0) {
                if (j == 6) {
                    flag &= checkCoupleCell(i, j, stoneList);
                    i--;
                    if (i == -1) {
                        i=0;
                        j = 7;
                    }
                } else if (j == 7) {
                    flag &= checkCoupleCell(i, j, stoneList);
                    j = 8;
                } else if (j == 8) {
                    flag &= checkCoupleCell(i, j, stoneList);
                    i++;
                }
                ran--;
            }
        }
        else if (j == 7 && i == 0) {
            System.out.println("case 3");
            j++;
            flag &= checkCoupleCell(i, j, stoneList);
            ran --;
            while (ran !=0){
                flag &= checkCoupleCell(i, j, stoneList);
                i++;
                ran--;
            }
        }
        else if (j == 8 && i <= 5) {
            System.out.println("case 4");
            while (ran != 0) {
                if (j == 8) {
                    flag &= checkCoupleCell(i, j, stoneList);
                    i++;
                    if (i == 6) {
                        j = 9;
                    }
                }
                else if (i == 6) {
                    flag &= checkCoupleCell(i, j, stoneList);
                    j++;
                }
                ran--;
            }
        }
        else if (i == 6 && j >= 8) {
            System.out.println("case 5");
            while (ran != 0) {
                if(i == 6){
                    flag &= checkCoupleCell(i, j, stoneList);
                    j++;
                    if (j == 15){
                        i=7;
                        j=14;
                    }
                }
                else if(i==7){
                    flag &= checkCoupleCell(i, j, stoneList);
                    i++;
                }
                else if(i == 8){
                    flag &= checkCoupleCell(i, j, stoneList);
                    j--;
                }
                ran --;
            }

        }
        else if (i == 7 && j == 14){
            System.out.println("case 6");
            i++;
            flag &= checkCoupleCell(i, j, stoneList);
            ran --;
            while (ran != 0){
                flag &= checkCoupleCell(i, j, stoneList);
                j--;
                ran--;
            }
        }
        else if (i == 8 && j >= 9){
            System.out.println("case 7");
            while (ran != 0) {
                if (j != 8){
                    flag &= checkCoupleCell(i, j, stoneList);
                    j--;
                    if(j == 8){
                        i=9;
                    }
                } else {
                    flag &= checkCoupleCell(i, j, stoneList);
                    i++;
                }
                ran--;
            }
        }
        else if (i >= 8 && j == 8){
            System.out.println("case 8");
            while (ran != 0) {
                if (j == 8){
                    flag &= checkCoupleCell(i, j, stoneList);
                    i++;
                    if (i == 15){
                        i=14;
                        j=7;
                    }
                }
                else if (j == 7){
                    flag &= checkCoupleCell(i, j, stoneList);
                    j = 6;
                }else{
                    flag &= checkCoupleCell(i, j, stoneList);
                    i--;
                }
                ran--;
            }
        }
        else if (i == 14 && j == 7){
            System.out.println("case 9");
            j--;
            flag &= checkCoupleCell(i, j, stoneList);
            ran --;
            while (ran != 0) {
                flag &= checkCoupleCell(i, j, stoneList);
                i--;
                ran--;
            }
        }
        else if (i >= 9 && j == 6){
            System.out.println("case 10");
            while (ran != 0) {
                if(j == 6){
                    flag &= checkCoupleCell(i, j, stoneList);
                    i--;
                    if(i == 8){
                        j = 5;
                    }
                }
                else{
                    flag &= checkCoupleCell(i, j, stoneList);
                    j--;
                }
                ran--;
            }
        }
        else if (i == 8 && j <= 5){
            System.out.println("case 11");
            while (ran != 0) {
                if(i == 8){
                    flag &= checkCoupleCell(i, j, stoneList);
                    j--;
                    if(j == -1){
                        i = 7;
                        j = 0;
                    }
                }
                else if(i == 7){
                    flag &= checkCoupleCell(i, j, stoneList);
                    i--;
                }else {
                    flag &= checkCoupleCell(i, j, stoneList);
                    j++;
                }
                ran--;
            }
        }
        else if (i == 7 && j == 0){
            System.out.println("case 12");
            i--;
            flag &= checkCoupleCell(i, j, stoneList);
            ran --;
            while (ran != 0){
                flag &= checkCoupleCell(i, j, stoneList);
                j++;
                ran--;
            }
        }
        if(!flag){
            i=-1;
            j=-1;
        }
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
