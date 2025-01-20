import java.util.*;


public class State {

    int turn = 0;
    boolean played = true;
    List<Player> players = List.of(new Player(0), new Player(1), new Player(2), new Player(3));
    State parent;
    double possibility;


    public State(State parent) {
        this.parent = parent;
    }

    public State(List<Player> players, int turn, boolean played, State parent) {
        this.players = players;
        this.turn = turn;
        this.played = played;
        this.parent = parent;
    }

    public boolean checkParentState() {
        if (parent != null && turn == parent.turn &&
                parent.parent != null && turn == parent.parent.turn &&
                parent.parent.parent != null && turn == parent.parent.parent.turn &&
                parent.parent.parent.parent != null && turn == parent.parent.parent.parent.turn) {
            return true;
        }
        return false;
    }

    public boolean thereIsMove(int ran,List<Stone> stones) {
        return players.get(turn).thereIsMove(ran,stones);
    }

    public int getStoneInPlace(int i, int j) {
        players.get(0).getStoneInPlace(i, j);
        players.get(1).getStoneInPlace(i, j);
        players.get(2).getStoneInPlace(i, j);
        players.get(3).getStoneInPlace(i, j);
        return -1;

    }

    public int getNerdNumber() {
        played = false;
        Random r = new Random();
        int ran;
        while ((ran = r.nextInt(7)) == 0) {
        }
        System.out.println("nerd  : " + ran);
        List<Stone> stones = getOtherStones();
        System.out.println("there is move  : " + thereIsMove(ran,stones));
        if (!thereIsMove(ran,stones)) {
            turn = (turn + 1) % 4;
            played = true;
            return ran;
        }
        System.out.println("move exist played : " + played);
        System.out.println();
        return ran;
    }

    public State move(int stoneId, int ran) {
        State newState = this.deepCopy();
        MoveType canMove2 = newState.players.get(turn).move(stoneId, ran, true,getOtherStones());
        if (canMove2 == MoveType.CANT_MOVE) {
            return null;
        }
        newState.played = true;
        boolean killed = killStone(true);
        if (ran == 6 || canMove2 == MoveType.ENTERED_THE_KITCHEN || killed) {
        } else newState.turn = (turn + 1) % 4;
        return newState;
    }

    public List<State> nextStates() {
        List<State> nextStatesList = new ArrayList<>();
        Queue<State> toDoMoveAgain = new ArrayDeque<>();

        for (int nerdNumber = 1; nerdNumber <= 6; nerdNumber++) {
            boolean flag = this.thereIsMove(nerdNumber,getOtherStones());
            if (!flag) {
                State newState = this.deepCopy();
                newState.turn = (turn + 1) % 4;
                nextStatesList.add(newState);
                continue;
            }
            for (Stone s : players.get(turn).stones) {

                State newState = this.deepCopy();
                MoveType canMove2 = newState.players.get(turn).move(s.id, nerdNumber, true,getOtherStones());
                if (canMove2 == MoveType.CANT_MOVE) continue;
                boolean killed = killStone(true);
                newState.possibility=1/24.0;
                if (nerdNumber == 6 || canMove2 == MoveType.ENTERED_THE_KITCHEN || killed) {
                    toDoMoveAgain.add(newState);
                } else {
                    newState.turn = (turn + 1) % 4;
                    nextStatesList.add(newState);
                }
            }
        }

        System.out.println("--------------------------------------");
        while(!toDoMoveAgain.isEmpty()) {
            State queueMoveAgain = toDoMoveAgain.poll();
            for (int nerdNumber = 1; nerdNumber <= 6; nerdNumber++) {
                for (Stone s : players.get(turn).stones) {
                    State newState2 = queueMoveAgain.deepCopy();
                    boolean flag = newState2.checkParentState();
                    newState2.possibility=newState2.parent.possibility * 1/24.0;
                    if(flag){
                        State state = newState2.parent.parent.parent;
                        state.turn = (turn + 1) % 4;
                        nextStatesList.add(state);
                        continue;
                    }
                    MoveType canMove2 = newState2.players.get(turn).move(s.id, nerdNumber, true,getOtherStones());
                    if (canMove2 == MoveType.CANT_MOVE) continue;
                    boolean killed = killStone(true);
                    if (nerdNumber == 6 || canMove2 == MoveType.ENTERED_THE_KITCHEN || killed) {
                        toDoMoveAgain.add(newState2);
                    } else {
                        newState2.turn = (turn + 1) % 4;
                        nextStatesList.add(newState2);
                    }
                }
            }
        }
        return nextStatesList;
    }

    public boolean killStone(boolean flag) {
        supposedToDo();
        Player player1 = players.get(turn);
        for (Player player2 : players) {
            if (player1.playerID == player2.playerID) continue;
            for (Stone playerTurnStone : player1.stones) {
                for (Stone otherStone : player2.stones) {
                    if (playerTurnStone.position.equals(otherStone.position)) {
                        if (otherStone.locked) continue;
                        if(flag) {
                            actualKill(otherStone, player2.playerID);
                        }
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public void actualKill(Stone s, int turn){
        s.updateLocationForKill(turn);
    }

    public boolean checkGoal() {
        for (Player p : players) {
            if (!p.stones.isEmpty()) {
                return false;
            }
        }
        return true;
    }

    public void supposedToDo() {
        if (turn == 0) {
            System.out.println("supposed to do is RED");
        } else if (turn == 1) {
            System.out.println("supposed to do is GREEN");
        } else if (turn == 2) {
            System.out.println("supposed to do is YELLOW");
        } else if (turn == 3) {
            System.out.println("supposed to do is BLUE");
        }
    }


    public List<Stone> getOtherStones(){
        List<Stone> stones = new ArrayList<>();
        for(Player p : players) {
            if(p.playerID == turn) continue;
            for(Stone stone : p.stones){
                stones.add(stone);
            }
        }
        return stones;
    }

    public State deepCopy() {
        List<Player> copiedPlayers = new ArrayList<>();
        for (Player player : players) {
            copiedPlayers.add(player.deepCopy());
        }
        return new State(copiedPlayers, turn,false, this);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;

        State state = (State) object;
        return turn == state.turn && played == state.played && players.equals(state.players);
    }

    @Override
    public int hashCode() {
        int result = players.hashCode();
        result = 31 * result + turn;
        result = 31 * result + Boolean.hashCode(played);
        return result;
    }


    @Override
    public String toString() {
        return "State{" + "players=" + players + ", turn=" + turn + ", played=" + played + '}';
    }


}
