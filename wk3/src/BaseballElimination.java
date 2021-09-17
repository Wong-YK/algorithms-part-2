import edu.princeton.cs.algs4.FlowEdge;
import edu.princeton.cs.algs4.FlowNetwork;
import edu.princeton.cs.algs4.FordFulkerson;
import edu.princeton.cs.algs4.In;
import java.util.ArrayList;

public class BaseballElimination {

    private final ArrayList<String> t;
    private final int[] w;
    private final int[] l;
    private final int[] r;
    private final int[][] g;

    //create a baseball division from given filename in format specified below
    public BaseballElimination(String filename) {
        In in = new In(filename);
        int numLines = Integer.parseInt(in.readLine());
        this.t = new ArrayList<String>();
        this.w = new int[numLines];
        this.l = new int[numLines];
        this.r = new int[numLines];
        this.g = new int[numLines][numLines];
        for (int lineNum = 0; lineNum < numLines; lineNum++) {
            String[] line = in.readLine().split("\s+");
            int index = 0;
            this.t.add(line[index++]);
            this.w[lineNum] = Integer.parseInt(line[index++]);
            this.l[lineNum] = Integer.parseInt(line[index++]);
            this.r[lineNum] = Integer.parseInt(line[index++]);
            while (index - 4 < numLines) {
                this.g[lineNum][index - 4] = Integer.parseInt(line[index++]);
            }
        }
    }

    //number of teams
    public int numberOfTeams() { return this.t.size(); }

    //all teams
    public Iterable<String> teams() { return this.t; }

    //number of wins for given team
    public int wins(String team) {
        int index = this.getTeamIndex(team);
        return this.w[index];
    }

    //number of losses for given team
    public int losses(String team) {
        int index = this.getTeamIndex(team);
        return this.l[index];
    }

    //number of remaining games for given team
    public int remaining(String team) {
        int index = this.getTeamIndex(team);
        return this.r[index];
    }

    //number of remaining games between team1 and team2
    public int against(String team1, String team2) {
        int index1 = this.getTeamIndex(team1);
        int index2 = this.getTeamIndex(team2);
        return this.g[index1][index2];
    }

    //is given team eliminated?
    public boolean isEliminated(String team) {
        //trivial elimination
        int maxWins = this.wins(team) + this.remaining(team);
        for (String t: this.teams()) {
            if (this.wins(t) > maxWins) { return true; }
        }
        //non-trivial elimination
        FordFulkerson ff = this.createEliminationFlowNetwork(team);
        int teamIndex = this.getTeamIndex(team);
        double maxFlow = ff.value();
        int remainingGames = 0;
        for (int row = 0, indent = 1; row < this.numberOfTeams() - 1; row++, indent++) {
            if (row != teamIndex) {
                for (int col = indent; col < this.numberOfTeams(); col++) {
                    if (col != teamIndex) { remainingGames += this.g[row][col]; }
                }
            }
        }
        return maxFlow < (double) remainingGames;
    }

    //subset R of teams that eliminates given team; null if not eliminated
    public Iterable<String> certificateOfElimination(String team) {
        FordFulkerson ff = this.createEliminationFlowNetwork(team);
        ArrayList<String> result = new ArrayList<String>();
        int games = this.getNumGameVertices();
        int teamIndex = this.getTeamIndex(team);
        for (int teamVertex = games; teamVertex < this.numberOfTeams() + games - 1; teamVertex++) {
            if (ff.inCut(teamVertex)) {
                if (teamVertex >= teamIndex + games) { result.add(this.t.get((teamVertex + 1) - games)); }
                else { result.add(this.t.get(teamVertex - games)); }
            }
        }
        return result;
    }

    //returns index of team
    private int getTeamIndex(String team) {
        for (int index = 0; index < this.numberOfTeams(); index++) {
            if (team.equals(this.t.get(index))) { return index; }
        }
        throw new IllegalArgumentException();
    }

    //returns number of game vertices
    private int getNumGameVertices() {
        return ((this.numberOfTeams() * (this.numberOfTeams() - 1)) / 2) - (this.numberOfTeams() - 1);
    }

    //creates elimination flow network
    private FordFulkerson createEliminationFlowNetwork(String team) {
        int maxWins = this.wins(team) + this.remaining(team);
        int teamIndex = this.getTeamIndex(team);
        int games = this.getNumGameVertices();
        //create flow network
        FlowNetwork fn = new FlowNetwork(games + (this.numberOfTeams() - 1) + 2);
        int s = games + (this.numberOfTeams() - 1);
        int t = games + this.numberOfTeams();
        //add edges from s to game vertices and from game vertices to team vertices
        for (int gameVertex = 0, indent = 1, row = 0; row < this.numberOfTeams() - 1; indent++, row++) {
            if (row != teamIndex) {
                for (int col = indent; col < this.numberOfTeams(); col++) {
                    if (col != teamIndex) {
                        int capacity = this.g[row][col];
                        FlowEdge e1 = new FlowEdge(s, gameVertex, capacity);
                        FlowEdge e2 = new FlowEdge(gameVertex, row + games, Integer.MAX_VALUE);
                        FlowEdge e3 = new FlowEdge(gameVertex, col + games, Integer.MAX_VALUE);
                        fn.addEdge(e1);
                        fn.addEdge(e2);
                        fn.addEdge(e3);
                        gameVertex++;
                    }
                }
            }
        }
        //add edges from team vertices to t
        for (int otherTeamIndex = 0; otherTeamIndex < this.numberOfTeams() - 1; otherTeamIndex++) {
            if (otherTeamIndex != teamIndex) {
                int capacity = maxWins - this.w[otherTeamIndex];
                FlowEdge e = new FlowEdge(otherTeamIndex + games, t, capacity);
                fn.addEdge(e);
            }
        }
        return new FordFulkerson(fn, s, t);
    }

    public static void main(String[] args) {
    }
}
