import edu.princeton.cs.algs4.EdgeWeightedDigraph;
import edu.princeton.cs.algs4.Picture;
import java.awt.*;

public class SeamCarver {

    private Picture p;
    private EdgeWeightedDigraph g;

    // create a seam carver object based on the given picture
    public SeamCarver(Picture picture) {
        this.p = picture;
        EdgeWeightedDigraph g = new EdgeWeightedDigraph(picture().height() * picture.width() + 2);
        this.g = g;
    }

    // current picture
    public Picture picture() {return this.p;}

    // width of current picture
    public int width() {return this.p.width();}

    // height of current picture
    public int height() {return this.p.height();}

    // energy of pixel at column x and row y
    public double energy(int x, int y) {
        // the energy of outer pixels is 1000
        if (x == 0 || x == this.width() || y == 0 || y == this.height()) {
            return 1000.0;
        }
        // otherwise the energy of a pixel is calculated using the dual-gradient energy function
        Color xPlusColor = this.p.get(x + 1, y);
        Color xMinusColor = this.p.get(x - 1, y);
        int xDeltaRed = xPlusColor.getRed() - xMinusColor.getRed();
        int xDeltaGreen = xPlusColor.getGreen() - xMinusColor.getGreen();
        int xDeltaBlue = xPlusColor.getBlue() - xMinusColor.getBlue();
        int xDeltaSquared = (xDeltaRed * xDeltaRed) + (xDeltaGreen * xDeltaGreen) + (xDeltaBlue * xDeltaBlue);
        Color yPlusColor = this.p.get(x, y + 1);
        Color yMinusColor = this.p.get(x, y - 1);
        int yDeltaRed = yPlusColor.getRed() - yMinusColor.getRed();
        int yDeltaGreen = yPlusColor.getGreen() - yMinusColor.getGreen();
        int yDeltaBlue = yPlusColor.getBlue() - yMinusColor.getBlue();
        int yDeltaSquared = (yDeltaRed * yDeltaRed) + (yDeltaGreen * yDeltaGreen) + (yDeltaBlue * yDeltaBlue);
        return Math.sqrt(xDeltaSquared + yDeltaSquared);
    }

    // sequence of indices for horizontal seam
    public int[] findHorizontalSeam() {return null;}

    // sequence of indices for vertical seam
    public int[] findVerticalSeam() {return null;}

    // remove horizontal seam from current picture
    public void removeHorizontalSeam(int[] seam) {}

    // remove vertical seam from current picture
    public void removeVerticalSeam(int[] seam) {}

    //  unit testing (optional)
    public static void main(String[] args) {}
}