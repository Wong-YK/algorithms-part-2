import org.junit.Test;
import static org.junit.Assert.*;

public class BaseballEliminationTest {

    @Test
    //Trivial case
    public void isEliminatedTest1() {
        BaseballElimination be = new BaseballElimination("teams4.txt");
        assertTrue(be.isEliminated("Montreal"));
    }
}
