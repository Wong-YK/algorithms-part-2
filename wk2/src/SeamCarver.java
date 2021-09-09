import edu.princeton.cs.algs4.DijkstraSP;
import edu.princeton.cs.algs4.DirectedEdge;
import edu.princeton.cs.algs4.EdgeWeightedDigraph;
import edu.princeton.cs.algs4.Picture;
import java.awt.Color;

public class SeamCarver {

    private Picture p;
    private int v;
    private EdgeWeightedDigraph horizontal;
    private EdgeWeightedDigraph vertical;


    // create a seam carver object based on the given picture
    public SeamCarver(Picture picture) {
        if (picture == null) {
            throw new IllegalArgumentException();
        }
        this.p = picture;
        this.v = picture().height() * picture.width();
        //edge weighted digraph with horizontal paths
        this.horizontal = createHorizontalPathsDigraph();
        //edge weighted digraph with veritcal paths
        this.vertical = createVerticalPathsDigraph();
    }

    // current picture
    public Picture picture() {return this.p;}

    // width of current picture
    public int width() {return this.p.width();}

    // height of current picture
    public int height() {return this.p.height();}

    // energy of pixel at column x and row y
    public double energy(int x, int y) {
        if (x < 0 || x > this.width() - 1 || y < 0 || y > this.height() - 1) {
            throw new IllegalArgumentException();
        }
        // the energy of outer pixels is 1000
        if (x == 0 || x == this.width() - 1 || y == 0 || y == this.height() - 1) {
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
    public int[] findHorizontalSeam() {
        DijkstraSP dsp = new DijkstraSP(this.horizontal, this.v);
        int[] result = new int[this.width()];
        int index = 0;
        for (DirectedEdge edge: dsp.pathTo(this.v + 1)) {
            if (edge.to() == this.v + 1) { index ++; }
            else { result[index++] = edge.to() / this.width(); }
        }
        return result;
    }

    // sequence of indices for vertical seam
    public int[] findVerticalSeam() {
        DijkstraSP dsp = new DijkstraSP(this.vertical, this.v);
        int[] result = new int[this.height()];
        int index = 0;
        for (DirectedEdge edge: dsp.pathTo(this.v + 1)) {
            if (edge.to() == this.v + 1) { index++; }
            else {result[index++] = edge.to() % this.width(); }
        }
        return result;
    }

    // remove horizontal seam from current picture
    public void removeHorizontalSeam(int[] seam) {
        if (seam == null) {
            throw new IllegalArgumentException();
        }
        Picture newPicture = new Picture(this.width(), this.height() - 1);
        for (int col = 0; col < this.width(); col++) {
            for (int row1 = 0, row2 = 0; row1 < this.height(); row1++) {
                if ( !(seam[col] == row1) ) {
                    newPicture.set(col, row2++, this.p.get(col, row1));
                }
            }
        }
        this.p = newPicture;
        this.v = this.v - this.width();
        this.horizontal = createHorizontalPathsDigraph();
        this.vertical = createVerticalPathsDigraph();
    }

    // remove vertical seam from current picture
    public void removeVerticalSeam(int[] seam) {
        if (seam == null) {
            throw new IllegalArgumentException();
        }
        Picture newPicture = new Picture(this.width() - 1, this.height());
        for (int row = 0; row < this.height(); row++) {
            for (int col1 = 0, col2 = 0; col1 < this.width(); col1++) {
                if ( seam[row] != col1) {
                    Color c = this.p.get(col1, row);
                    newPicture.set(col2++, row, c);
                }
            }
        }
        this.p = newPicture;
        this.v = this.v - this.height();
        this.horizontal = createHorizontalPathsDigraph();
        this.vertical = createVerticalPathsDigraph();
    }

    // create edge weighted digraph with horizontal paths
    private EdgeWeightedDigraph createHorizontalPathsDigraph() {
        EdgeWeightedDigraph result = new EdgeWeightedDigraph(this.v + 2);
        for (int row = 0; row < this.p.height(); row++) {
            for (int col = 0; col < this.p.width() - 1; col++) {
                double energy = this.energy(col, row);
                int vertexFrom = col + (row * this.width());
                if (row != 0) {
                    result.addEdge(new DirectedEdge(vertexFrom, (vertexFrom - this.width() + 1), energy));
                }
                if (row != this.height() - 1) {
                    result.addEdge(new DirectedEdge(vertexFrom, (vertexFrom + this.width() + 1), energy));
                }
                result.addEdge(new DirectedEdge(vertexFrom, vertexFrom + 1, energy));

            }
        }
        //edges going from lhs and to rhs virtual vertices
        for (int row = 0; row < this.height(); row++) {
            result.addEdge(new DirectedEdge(this.v, row * this.width(), 0));
            result.addEdge(new DirectedEdge((row * this.width()) + (this.width() - 1), this.v + 1, 1000));
        }
        return result;
    }

    // create edge weighted digraph with vertical paths
    private EdgeWeightedDigraph createVerticalPathsDigraph() {
        EdgeWeightedDigraph result = new EdgeWeightedDigraph(this.v + 2);
        for (int row = 0; row < this.p.height() - 1; row++) {
            for (int col = 0; col < this.p.width(); col++) {
                double energy = this.energy(col, row);
                int vertexFrom = col + (row * this.width());
                if (col != 0) {
                    result.addEdge(new DirectedEdge(vertexFrom, vertexFrom + this.width() - 1, energy));
                }
                if (col != this.width() - 1) {
                    result.addEdge(new DirectedEdge(vertexFrom, vertexFrom + this.width() + 1, energy));
                }
                result.addEdge(new DirectedEdge(vertexFrom, vertexFrom + this.width(), energy));

            }
        }
        //edges going from top and to bottom virtual vertices
        for (int col = 0; col < this.width(); col++) {
            result.addEdge(new DirectedEdge(this.v, col, 0));
            result.addEdge(new DirectedEdge(col + ((this.height() - 1) * this.width()), this.v + 1, this.energy(col, this.height() - 1)));
        }
        return result;
    }

    private boolean isValidSeam(int[] seam, boolean horizontal) {
        if (seam == null) { return false; }
        if (horizontal) {
            if (seam.length != this.width()) { return false; }
            for (int row: seam) {
                if (row < 0 || row > this.height() - 1) { return false; }
            }
        }
        if (!horizontal) {
            if (seam.length != this.height()) { return false; }
            for (int col: seam) {
                if (col < 0 || col > this.width() - 1) { return false; }
            }
        }
        for (int i = 1; i < seam.length; i++) {
            if (Math.abs(seam[i] - seam[i - 1]) != 1) { return false; }
        }
        return true;
    }


    //  unit testing (optional)
    public static void main(String[] args) {}
}