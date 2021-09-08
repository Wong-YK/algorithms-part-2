import edu.princeton.cs.algs4.DijkstraSP;
import edu.princeton.cs.algs4.DirectedEdge;
import edu.princeton.cs.algs4.EdgeWeightedDigraph;
import edu.princeton.cs.algs4.Picture;
import java.awt.Color;

public class SeamCarver {

    public Picture p;
    private int v;
    private EdgeWeightedDigraph vertical;
    private EdgeWeightedDigraph horizontal;


    // create a seam carver object based on the given picture
    public SeamCarver(Picture picture) {
        this.p = picture;
        this.v = picture().height() * picture.width();
        //edge weighted digraph with veritcal paths
        this.vertical = createVerticalPathsDigraph(this.p, this.v + 2);
        //edge weighted digraph with horizontal paths
        EdgeWeightedDigraph horizontal = new EdgeWeightedDigraph(this.v + 2);
        for (int row = 0; row < this.height(); row++) {
            for (int col = 0; col < this.width() - 1; col++) {
                double energy = this.energy(col, row);
                int vertexFrom = col + (row * this.width());
                if (row != 0) {
                    horizontal.addEdge(new DirectedEdge(vertexFrom, (vertexFrom - this.width() + 1), energy));
                }
                if (row != this.height() - 1) {
                    horizontal.addEdge(new DirectedEdge(vertexFrom, (vertexFrom + this.width() + 1), energy));
                }
                horizontal.addEdge(new DirectedEdge(vertexFrom, vertexFrom + 1, energy));

            }
        }
        //edges going from lhs and to rhs virtual vertices
        for (int row = 0; row < this.height(); row++) {
            horizontal.addEdge(new DirectedEdge(this.v, row * this.width(), 0));
            horizontal.addEdge(new DirectedEdge((row * this.width()) + (this.width() - 1), this.v + 1, 1000));
        }
        this.horizontal = horizontal;
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
        Color[][] newColors = new Color[this.height() - 1][this.width()];
        for (int col = 0; col < this.width(); col++) {
            for (int row1 = 0, row2 = 0; row1 < this.height(); row1++) {
                if ( !(seam[col] == row1) ) {
                    newColors[row2++][col] = this.p.get(col, row1);
                }
            }
        }
        Picture newPicture = new Picture(this.width(), this.height() - 1);
        for (int row = 0; row < this.height() - 1; row++) {
            for (int col = 0; col < this.width(); col++) {
                newPicture.set(col, row, newColors[row][col]);
            }
        }
        this.p = newPicture;
        this.v = this.v - this.width();
    }

    // remove vertical seam from current picture
    public void removeVerticalSeam(int[] seam) {}

    private EdgeWeightedDigraph createVerticalPathsDigraph(Picture picture, int numVertices) {
        EdgeWeightedDigraph result = new EdgeWeightedDigraph(numVertices + 2);
        for (int row = 0; row < picture.height() - 1; row++) {
            for (int col = 0; col < picture.width(); col++) {
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

    //  unit testing (optional)
    public static void main(String[] args) {}
}