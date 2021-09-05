import edu.princeton.cs.algs4.Picture;
import org.junit.Test;
import java.awt.Color;
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

    @Test
    // 6 by 5 image from specification
    public void findVerticalSeamTest1() {
        int[] expected = { 5, 4, 3, 2, 3 };
        Picture p = new Picture("https://coursera.cs.princeton.edu/algs4/assignments/seam/files/6x5.png");
        SeamCarver sc = new SeamCarver(p);
        int[] actual = sc.findVerticalSeam();
        for (int i = 0; i < expected.length || i < actual.length; i++) {
            assertEquals(expected[i], actual[i]);
        }
    }

    @Test
    //removing horizontal seam from 6 by 5 image
    public void removeHorizontalSeamTest1() {
        Picture p1 = new Picture("https://coursera.cs.princeton.edu/algs4/assignments/seam/files/6x5.png");
        SeamCarver sc = new SeamCarver(p1);
        sc.removeHorizontalSeam(sc.findHorizontalSeam());
        Picture p2 = new Picture(6, 4);
        int[][] notRemoved = {
                {0, 0}, {1, 0}, {2, 0}, {3, 0}, {4, 0}, {5, 0},
                {0, 1}, {1, 1}, {2, 2}, {3, 1}, {4, 2}, {5, 1},
                {0, 3}, {1, 3}, {2, 3}, {3, 3}, {4, 3}, {5, 3},
                {0, 4}, {1, 4}, {2, 4}, {3, 4}, {4, 4}, {5, 4}
        };

        int index = 0;
        for (int row = 0; row < 4; row++) {
            for (int col = 0; col < 6; col++) {
                Color c = p1.get(notRemoved[index][0], notRemoved[index][1]);
                index++;
                p2.set(col, row, c);
            }
        }
        assertEquals(p2, sc.p);
    }
}
