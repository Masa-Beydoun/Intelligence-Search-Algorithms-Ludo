import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class State {

    int turn = 0;
    boolean played = true;
    Integer comeFrom;
    List<Player> players = List.of(new Player(0), new Player(1), new Player(2), new Player(3));

    public State(Integer comeFrom) {
        this.comeFrom = comeFrom;
    }

    public State(List<Player> players, int turn, boolean played, int comeFrom) {
        this.comeFrom = comeFrom;
        this.players = players;
        this.turn = turn;
        this.played = played;
    }

    public boolean thereIsMove(int ran) {
        return players.get(turn).thereIsMove(ran);
    }

    public int getNerdNumber() {
        played = false;
        Random r = new Random();
        int ran;
        while ((ran = r.nextInt(7)) == 0) {
        }
        System.out.println("nerd  : " + ran);
        System.out.println("there is move  : " + thereIsMove(ran));
        if (!thereIsMove(ran)) {
            turn = (turn + 1) % 4;
            played = true;
            return ran;
        }
        System.out.println("move exist played : " + played);
        System.out.println();
        return ran;
    }

    public State move(int stoneId, int ran) {
        State newState = new State(List.copyOf(players), turn, false, ran);
        MoveType canMove2 = newState.players.get(turn).move(stoneId, ran, true);
        if (canMove2 == MoveType.CANT_MOVE) {
            return null;
        }
        newState.played = true;
        boolean killed = killStone();
        if (ran == 6 || canMove2 == MoveType.ENTERED_THE_KITCHEN || killed) {
        } else newState.turn = (turn + 1) % 4;
        System.out.println(nextStates(turn));
        return newState;
    }

    public List<State> nextStates(int turn) {
        List<State> nextStatesList = new ArrayList<>();
        for (int nerdNumber = 1; nerdNumber <= 6; nerdNumber++) {
            for (Stone s : players.get(turn).stones) {
                State newState = new State(List.copyOf(players), turn, false, nerdNumber);
                MoveType canMove2 = newState.players.get(turn).move(s.id, nerdNumber, true);
                if (canMove2 == MoveType.CANT_MOVE) continue;
                newState.played = true;
                boolean killed = killStone();
                if (nerdNumber == 6 || canMove2 == MoveType.ENTERED_THE_KITCHEN || killed) {
                } else newState.turn = (turn + 1) % 4;
                nextStatesList.add(newState);
            }
        }
        return nextStatesList;

    }

    public boolean killStone() {
        supposedToDo();
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
