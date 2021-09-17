import org.junit.Test;
import static org.junit.Assert.*;

public class BaseballEliminationTest {

    @Test
    //Trivial case
    public void isEliminatedTest1() {
        BaseballElimination be = new BaseballElimination("teams4.txt");
        assertTrue(be.isEliminated("Montreal"));
    }

    @Test
    //Non-trivial case (Detroit example from specification)
    public void isEliminatedTest2() {
        BaseballElimination be = new BaseballElimination("teams5.txt");
        assertTrue(be.isEliminated("Detroit"));
    }

    @Test
    //Non-trivial case (Detroit example from specification)
    public void certificateOfEliminationTest1() {
        BaseballElimination be = new BaseballElimination("teams5.txt");
        Iterable<String> r = be.certificateOfElimination("Detroit");
        for (String team: r) {
            assertTrue(team.equals("New_York") || team.equals("Baltimore")
                    || team.equals("Boston") || team.equals("Toronto"));
        }
    }

    @Test
    //Trivial case (Montreal example from specification)
    public void certificateOfEliminationTest2() {
        BaseballElimination be = new BaseballElimination("teams4.txt");
        Iterable<String> r = be.certificateOfElimination("Montreal");
        for (String team: r) { assertTrue(team.equals("Atlanta")); }
    }

    @Test
    //Trivial case (Phliladelphia example from specification)
    public void certificateOfEliminationTest3() {
        BaseballElimination be = new BaseballElimination("teams4.txt");
        Iterable<String> r = be.certificateOfElimination("Philadelphia");
        for (String team: r) { assertTrue(team.equals("Atlanta") || team.equals("New_York")); }
    }

}
