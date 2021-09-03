import edu.princeton.cs.algs4.Picture;
import org.junit.Test;
import static org.junit.Assert.*;

public class SeamCarverTest {

    @Test
    // corner pixel
    public void energyTest1() {
        Picture p = new Picture("https://coursera.cs.princeton.edu/algs4/assignments/seam/files/3x4.png");
        SeamCarver sc = new SeamCarver(p);
        assertEquals(1000.0, sc.energy(0, 0), 0.001);
    }

    @Test
    // vertical edge pixel
    public void energyTest2() {
        Picture p = new Picture("https://coursera.cs.princeton.edu/algs4/assignments/seam/files/3x4.png");
        SeamCarver sc = new SeamCarver(p);
        assertEquals(1000.0, sc.energy(p.width(), p.height() / 2), 0.001);
    }

    @Test
    // horizontal edge pixel
    public void energyTest3() {
        Picture p = new Picture("https://coursera.cs.princeton.edu/algs4/assignments/seam/files/3x4.png");
        SeamCarver sc = new SeamCarver(p);
        assertEquals(1000.0, sc.energy(p.width() / 2, p.height()), 0.001);
    }
}
