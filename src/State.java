import java.util.*;


public class State {

    int turn = 0;
    List<Player> players = List.of(new Player(0), new Player(1), new Player(2), new Player(3));
    State parent;

    public State(State parent) {
        this.parent = parent;
    }

    public State(List<Player> players, int turn, State parent) {
        this.players = players;
        this.turn = turn;
        this.parent = parent;
    }

    public boolean thereIsMove(int ran) {
        return players.get(turn).thereIsMove(ran);
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

    public int getStoneInPlace(int i, int j) {
        players.get(0).getStoneInPlace(i, j);
        players.get(1).getStoneInPlace(i, j);
        players.get(2).getStoneInPlace(i, j);
        players.get(3).getStoneInPlace(i, j);
        return -1;

    }

    public int getNerdNumber() {
        Random r = new Random();
        int ran;
        while ((ran = r.nextInt(7)) == 0) {
        }
        System.out.println("nerd  : " + ran);
        System.out.println("there is move  : " + thereIsMove(ran));
        if (!thereIsMove(ran)) {
            turn = (turn + 1) % 4;
            return ran;
        }
        return ran;
    }

    public State move(int stoneId, int ran) {
        State newState = this.deepCopy();
        MoveType canMove2 = newState.players.get(turn).move(stoneId, ran, true);
        if (canMove2 == MoveType.CANT_MOVE) {
            return null;
        }
        boolean killed = killStone();
        if (ran == 6 || canMove2 == MoveType.ENTERED_THE_KITCHEN || killed) {
        } else newState.turn = (turn + 1) % 4;
        return newState;
    }

    public List<State> nextStates() {
        List<State> nextStatesList = new ArrayList<>();
        Queue<State> toDoMoveAgain = new ArrayDeque<>();

        for (int nerdNumber = 1; nerdNumber <= 6; nerdNumber++) {
            boolean flag = this.thereIsMove(nerdNumber);
            if (!flag) {
                State newState = this.deepCopy();
                newState.turn = (turn + 1) % 4;
                nextStatesList.add(newState);
                continue;
            }
            for (Stone s : players.get(turn).stones) {
                State newState = this.deepCopy();
                MoveType canMove2 = newState.players.get(turn).move(s.id, nerdNumber, true);
                if (canMove2 == MoveType.CANT_MOVE) continue;
                boolean killed = killStone();
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
//            System.out.println(queueMoveAgain.players.get(0));
            for (int nerdNumber = 1; nerdNumber <= 6; nerdNumber++) {
//                System.out.println("nerd number : " + nerdNumber);
//                System.out.println("queue size : " + toDoMoveAgain.size() + "  next state list size : " + nextStatesList.size());
                for (Stone s : players.get(turn).stones) {
//                    System.out.println("stone : "+ s.id);
                    State newState2 = queueMoveAgain.deepCopy();
                    boolean flag = newState2.checkParentState();
                    if(flag){
                        nextStatesList.add(newState2.parent.parent.parent);
                        continue;
                    }
                    MoveType canMove2 = newState2.players.get(turn).move(s.id, nerdNumber, true);
                    if (canMove2 == MoveType.CANT_MOVE) continue;
//                    System.out.println("stone can move");
                    boolean killed = killStone();
                    if (nerdNumber == 6 || canMove2 == MoveType.ENTERED_THE_KITCHEN || killed) {
//                        System.out.println("adding when nerd number is 6\n");
                        toDoMoveAgain.add(newState2);
                    } else {
                        newState2.turn = (turn + 1) % 4;
//                        System.out.println("adding to list\n");
                        nextStatesList.add(newState2);
                    }
                }
            }
        }

        return nextStatesList;

    }

    public boolean killStone() {
        Player player1 = players.get(turn);
        for (Player player2 : players) {
            if (player1.playerID == player2.playerID) continue;
            for (Stone playerTurnStone : player1.stones) {
                for (Stone otherStone : player2.stones) {
                    if (playerTurnStone.position.equals(otherStone.position)) {
                        if (otherStone.locked) continue;
                        otherStone.updateLocationForKill(player2.playerID);
                        return true;
                    }
                }
            }
        }
        return false;
    }


    public boolean checkGoal() {
        for (Player p : players) {
            if (!p.stones.isEmpty()) {
                return false;
            }
        }
        return true;
    }


    public State deepCopy() {
        List<Player> copiedPlayers = new ArrayList<>();
        for (Player player : players) {
            copiedPlayers.add(player.deepCopy());
        }
        return new State(copiedPlayers, turn, this);
    }


    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;

        State state = (State) object;
        return turn == state.turn && players.equals(state.players);
    }

    @Override
    public int hashCode() {
        int result = turn;
        result = 31 * result + players.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "State{" + "players=" + players + ", turn=" + turn + "}\n";
    }


}
