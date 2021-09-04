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

    @Test
    // non-edge pixel
    public void energyTest4() {
        Picture p = new Picture("https://coursera.cs.princeton.edu/algs4/assignments/seam/files/3x4.png");
        SeamCarver sc = new SeamCarver(p);
        assertEquals(Math.sqrt(52024), sc.energy(1, 2), 0.001);
    }

    @Test
    // non-edge pixel
    public void energyTest5() {
        Picture p = new Picture("https://coursera.cs.princeton.edu/algs4/assignments/seam/files/3x4.png");
        SeamCarver sc = new SeamCarver(p);
        assertEquals(Math.sqrt(52225), sc.energy(1, 1), 0.001);
    }

    @Test
    // 6 by 5 image from specification
    public void findHorizontalSeamTest1() {
        int[] expected = { 3, 2, 1, 2, 1, 2 };
        Picture p = new Picture("https://coursera.cs.princeton.edu/algs4/assignments/seam/files/6x5.png");
        SeamCarver sc = new SeamCarver(p);
        int[] actual = sc.findHorizontalSeam();
        for (int i = 0; i < expected.length || i < actual.length; i++) {
            assertEquals(expected[i], actual[i]);
        }
    }
}
