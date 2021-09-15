import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class BaseballElimination {

    private final String[] t;
    private final int[] w;
    private final int[] l;
    private final int[] r;
    private final int[][] g;

    //create a baseball division from given filename in format specified below
    public BaseballElimination(String filename) {
        In in = new In(filename);
        int numLines = Integer.parseInt(in.readLine());
        this.t = new String[numLines];
        this.w = new int[numLines];
        this.l = new int[numLines];
        this.r = new int[numLines];
        this.g = new int[numLines][numLines];
        for (int lineNum = 0; lineNum < numLines; lineNum++) {
            String[] line = in.readLine().split("\s+");
            int index = 0;
            this.t[lineNum] = line[index++];
            this.w[lineNum] = Integer.parseInt(line[index++]);
            this.l[lineNum] = Integer.parseInt(line[index++]);
            this.r[lineNum] = Integer.parseInt(line[index++]);
            while (index - 4 < numLines) {
                this.g[lineNum][index - 4] = Integer.parseInt(line[index++]);
            }
        }
    }

    //number of teams
    public int numberOfTeams() { return -1; }

    //all teams
    public Iterable<String> teams() { return null; }

    //number of wins for given team
    public int wins(String team) { return -1; }

    //number of losses for given team
    public int losses(String team) { return -1; }

    //number of remaining games for given team
    public int remaining(String team) { return -1; }

    //number of remaining games between team1 and team2
    public int against(String team1, String team2) { return -1; }

    //is given team eliminated?
    public boolean isEliminated(String team) { return false; }

    //subset R of teams that eliminates given team; null if not eliminated
    public Iterable<String> certificateOfElimination(String team) { return null; }

    public static void main(String[] args) {
    }
}
