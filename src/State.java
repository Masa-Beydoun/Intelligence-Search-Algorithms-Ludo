import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class State {

    List<Player> players = List.of(
            new Player(0),
            new Player(1),
            new Player(2),
            new Player(3)
    );
    int turn = 0;
    boolean played = true;
    Integer comeFrom;

    public State(Integer comeFrom) {
        this.comeFrom = comeFrom;
//        System.out.println(players);
    }

    public State(List<Player> players, int turn, boolean played, int comeFrom) {
        this.comeFrom = comeFrom;
        this.players = players;
        this.turn = turn;
        this.played = played;
    }

    public boolean thereIsMove(int ran) {
        return players.get(turn).thereIsMove(ran);
        //TODO in player class
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
            System.out.println();
            return ran;
        }
        System.out.println("move exist played : " + played);
        System.out.println();
        return ran;
    }

    public State move(int stoneId, int ran) {
        System.out.println();


        //create new state
        State newState = new State(ran);

        newState.players = List.copyOf(players);
        newState.turn = turn;
        newState.played = false;

        //calling the move function in Player class , then in stone class
        MoveType canMove2 = newState.players.get(turn).move(stoneId,ran,true);
        if (canMove2 == MoveType.CANT_MOVE) {
            return null;
        }
        newState.played = true;
        if (ran != 6 || canMove2 != MoveType.ENTERED_THE_KITCHEN) newState.turn = (turn + 1) % 4;
        return newState;

    }


    public List<State> nextStates() {

        List<State> nextStates = new ArrayList<>();
        for (int nerdNumber = 1; nerdNumber <= 6; nerdNumber++) {
            int id;
            if (turn == 0)
                id = 0;
            else if (turn == 1)
                id = 4;
            else if (turn == 2)
                id = 8;
            else {
                id = 12;
            }
            int n = id + 4;
            for (; id < n; id++) {
                nextStates.add(move(nerdNumber, id));
            }
        }
        return nextStates;

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

    public boolean checkGoal() {
        for (Player p : players) {
            if(!p.stones.isEmpty()){
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
    public String toString() {
        return "State{" +
                "players=" + players +
                ", turn=" + turn +
                ", played=" + played +
                '}';
    }
}
