import java.util.*;


public class State {

    int turn = 0;
    boolean played = true;
    List<Player> players = List.of(new Player(0),
            new Player(1),
            new Player(2),
            new Player(3));
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
                parent.parent.parent != null && turn == parent.parent.parent.turn) {
            return true;
        }
        return false;
    }

    public boolean thereIsMove(int ran, List<Stone> stones) {
        return players.get(turn).thereIsMove(ran, stones);
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
        System.out.println("there is move  : " + thereIsMove(ran, stones));
        if (!thereIsMove(ran, stones)) {
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
        MoveType canMove2 = newState.players.get(turn).move(stoneId, ran, true, getOtherStones());
        if (canMove2 == MoveType.CANT_MOVE) {
            return null;
        }
        newState.played = true;
        newState.possibility = 1 / 24.0;
        boolean killed = killStone();
        if (!(ran == 6 || canMove2 == MoveType.ENTERED_THE_KITCHEN || killed)) {
            newState.turn = (turn + 1) % 4;
        }
        if (newState.parent != null && newState.parent.possibility != 0.0)
            newState.possibility = newState.parent.possibility * 1 / 24.0;
        else
            newState.possibility = 1 / 24.0;
        return newState;
    }

    public List<State> getListWithoutRep(String mode) {
        List<State> nextStatesWillBeEdited;
        List<State> visited = new ArrayList<>();
        if (mode.equals("simple"))
            nextStatesWillBeEdited = simpleNextState();
        else
            nextStatesWillBeEdited = advancedNextStates();

        System.out.println(nextStatesWillBeEdited);

        List<State> updatedStates = new ArrayList<>();

        for (State s : nextStatesWillBeEdited) {
//            if (s.turn == turn) continue; //TODO
            boolean isVisited = false;
            for (State v : visited) {
                if (s.equals(v)) {
                    v.possibility += s.possibility;
                    isVisited = true;
                    break;
                }
            }
            if (!isVisited && s != null) {
                visited.add(s);
                updatedStates.add(s);
            }
        }

        System.out.println("next state sizes : " + updatedStates.size());

        return updatedStates;
    }


    public List<State> simpleNextState() {
        List<State> nextStatesList = new ArrayList<>();

        for (int nerdNumber = 1; nerdNumber <= 6; nerdNumber++) {

            if (!this.thereIsMove(nerdNumber, getOtherStones()) || this.checkParentState()) {
                System.out.println("");
                nextStatesList.add(checkFirst(this));
                continue;
            }
            for (Stone s : players.get(turn).stones) {
                State nwState = move(s.id, nerdNumber);
                if (nwState == null) {
                    continue;
                }
                nextStatesList.add(nwState);
            }
        }
        System.out.println("next states size: " + nextStatesList.size());

        System.out.println("next states : " + nextStatesList);
        System.out.println("------------------");
        return nextStatesList;
    }


    public State checkFirst(State state) {
        State newState = state.deepCopy();
        newState.possibility = 1 / (6.0);
        newState.turn = (turn + 1) % 4;
        return newState;
    }


    public List<State> advancedNextStates() {
        List<State> nextStatesList = new ArrayList<>();
        Queue<State> toDoMoveAgain = new ArrayDeque<>();
        toDoMoveAgain.add(this);

        while (!toDoMoveAgain.isEmpty()) {
            State queueMoveAgain = toDoMoveAgain.poll();
            if (queueMoveAgain == null) continue;
            if(queueMoveAgain.checkParentState()){
                nextStatesList.add(checkFirst(queueMoveAgain));
            }
            for (int nerdNumber = 1; nerdNumber <= 6; nerdNumber++) {
                if (!queueMoveAgain.thereIsMove(nerdNumber, getOtherStones())) {
                    continue;
                }
                for (Stone s : players.get(turn).stones) {
                    State nwState = move(s.id, nerdNumber);
                    if (nwState == null) {
                        continue;
                    }
                    if(nwState.turn == turn){
                        toDoMoveAgain.add(nwState);
                    }
                    else nextStatesList.add(nwState);
                }
            }
        }

        return nextStatesList;

    }

    public List<State> nerdSimpleNextState(int nerdNumber) {
        List<State> nextStatesList = new ArrayList<>();

        if (!this.thereIsMove(nerdNumber, getOtherStones()) || this.checkParentState()) {
            nextStatesList.add(checkFirst(this));
            return nextStatesList;
        }
        for (Stone s : players.get(turn).stones) {
            State nwState = move(s.id, nerdNumber);
            if (nwState == null) {
                continue;
            }
            nextStatesList.add(nwState);
        }
        return nextStatesList;
    }

    public List<State> nerdAdvancedNextStates(int nerdNumber) {
        List<State> nextStatesList = new ArrayList<>();
        Queue<State> toDoMoveAgain = new ArrayDeque<>();
        toDoMoveAgain.add(this);

        while (!toDoMoveAgain.isEmpty()) {
            State queueMoveAgain = toDoMoveAgain.poll();
            if (queueMoveAgain == null) continue;
            if (!queueMoveAgain.thereIsMove(nerdNumber, getOtherStones()) || queueMoveAgain.checkParentState()) {
                nextStatesList.add(queueMoveAgain);
                continue;
            }
            for (Stone s : players.get(turn).stones) {
                State nwState = move(s.id, nerdNumber);
                if (nwState == null) {
                    continue;
                }
                if (nwState.turn == turn) {
                    toDoMoveAgain.add(nwState);
                } else {
                    nextStatesList.add(nwState);
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
//                    System.out.println("player 1 : " + playerTurnStone.position + "player 2 : " + otherStone.position);
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

    public List<Stone> getOtherStones() {
        List<Stone> stones = new ArrayList<>();
        for (Player p : players) {
            if (p.playerID == turn) continue;
            for (Stone stone : p.stones) {
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
        return new State(copiedPlayers, turn, false, this);
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
