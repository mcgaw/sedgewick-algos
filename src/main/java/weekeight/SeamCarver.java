package weekeight;

import java.awt.Color;
import java.util.Arrays;

import edu.princeton.cs.algs4.Picture;


public class SeamCarver {

    private static final int BORDER_ENERGY = 1000;

    // Horizontal energy matrix where energy[y][x] is the energy of the pixel
    // in the y-th row, x-th column.
    private double[][] energy;

    // Seperately maintained color matrix to allow seams to be removed without
    // recreating Picture.
    private Color[][] colors;

    // create a seam carver object based on the given picture
    public SeamCarver(Picture picture) {
        if (picture == null) {
            throw new IllegalArgumentException("picture cannot be null");
        }

        // Store the picture's colour values in a seperate array
        // that is maintained when seams are removed.
        colors = new Color[picture.height()][];
        for (int y = 0; y < picture.height(); y++) {
            colors[y] = new Color[picture.width()];
            for (int x = 0; x < picture.width(); x++) {
                colors[y][x] = picture.get(x, y);
            }
        }
        computeEnergyValues(); 
    }

    // current picture
    public Picture picture() {
        Picture pic = new Picture(colors[0].length, colors.length);
        for (int i = 0; i < colors.length; i++) {
            for (int j = 0; j < colors[0].length; j++) {
                pic.set(j, i, colors[i][j]);
            }
        }
        return pic;
    }

    // width of current picture
    public int width() {
        return colors[0].length;
    }

    // height of current picture
    public int height() {
        return colors.length;
    }

    private void computeEnergyValues() {
        int h = colors.length;
        int w = colors[0].length;
        energy = new double[h][];
        for (int y = 0; y < h; y++) {
            energy[y] = new double[w];
            for (int x = 0; x < w; x++) {
                energy[y][x] = energy(x, y);
            }
        }
    }

    private Color getColor(int x, int y) {
        return colors[y][x];
    }

    private double xEnergySquared(int x, int y) {
        assert x != 0 && x != width() - 1;
        return Math.pow(getColor(x + 1, y).getRed() - getColor(x - 1, y).getRed(), 2) +
            Math.pow(getColor(x + 1, y).getGreen() - getColor(x - 1, y).getGreen(), 2) +
            Math.pow(getColor(x + 1, y).getBlue() - getColor(x - 1, y).getBlue(), 2);
    }

    private double yEnergySquared(int x, int y) {
        assert y != 0 && y != height() - 1;
        return Math.pow(getColor(x, y + 1).getRed() - getColor(x, y - 1).getRed(), 2) +
            Math.pow(getColor(x, y + 1).getGreen() - getColor(x, y - 1).getGreen(), 2) +
            Math.pow(getColor(x, y + 1).getBlue() - getColor(x, y - 1).getBlue(), 2);
    }

    // energy of pixel at column x and row y
    public double energy(int x, int y) {
        if (x >= width() || y >= height() || x < 0 || y < 0) {
            throw new IllegalArgumentException("x y is outside the picture area");
        }
        if (x == 0 || y == 0 || x == width() - 1 || y == height() - 1) {
            return BORDER_ENERGY;
        }
        return Math.sqrt(xEnergySquared(x, y) + yEnergySquared(x, y));
    }

    // sequence of indices for horizontal seam
    public int[] findHorizontalSeam() {
        return findSeam(true);
    }

    // sequence of indices for vertical seam
    public int[] findVerticalSeam() {
        return findSeam(false);
    }

    private double getEnergy(int x, int y, boolean horizontal) {
        if (horizontal) {
            return energy[x][y];
        }
        return energy[y][x];
    }

    private int[] findSeam(boolean horizontal) {

        int picHeight;
        int picWidth;

        if (horizontal) {
            picHeight = energy[0].length;
            picWidth = energy.length;
        } else {
            picHeight = energy.length;
            picWidth = energy[0].length;
        }

        // There is a natural DAG in topological order by considering all
        // the vertical seams that can be formed.
        double[][] pathEnergy = new double[picHeight][];
        int[][] pathEdge = new int[picHeight][];
        pathEdge[0] = new int[picWidth];

        // Init the algorithm.
        pathEnergy[0] = new double[picWidth];
        Arrays.fill(pathEnergy[0], BORDER_ENERGY);
        for (int x = 1; x < picHeight; x++) {
            pathEnergy[x] = new double[picWidth];
            Arrays.fill(pathEnergy[x], Double.POSITIVE_INFINITY);
        }

        for (int y = 0; y < picHeight - 1; y++) {
            // Relax each of the pixels in the next row.
            pathEdge[y+1] = new int[picWidth];
            for (int x = 0; x < picWidth; x++) {
                // Need to consider pixels down-left down-righ and down.
                double e;
                if (x != 0) {
                    e = getEnergy(x - 1, y + 1, horizontal) + pathEnergy[y][x];
                    if (e < pathEnergy[y + 1][x - 1]) {
                        pathEnergy[y + 1][x - 1] = e;
                        pathEdge[y + 1][x - 1] = x;
                    }
                }
                e = getEnergy(x, y + 1, horizontal) + pathEnergy[y][x];
                if (e < pathEnergy[y + 1][x]) {
                    pathEnergy[y + 1][x] = e;
                    pathEdge[y + 1][x] = x;
                }
                if (x != picWidth - 1) {
                    e = getEnergy(x + 1, y + 1, horizontal) + pathEnergy[y][x];
                    if (e < pathEnergy[y + 1][x + 1]) {
                        pathEnergy[y + 1][x + 1] = e;
                        pathEdge[y + 1][x + 1] = x;
                    }
                }
            }
        }

        // Read off the path starting from the pixel at the bottom
        // showing the smallest accumulation of energy.
        int lastPixel = 0;
        double smallestEnergy = Double.POSITIVE_INFINITY;
        for (int x = 0; x < picWidth; x++) {
            double pixelEnergy = pathEnergy[picHeight - 1][x]; 
            if (pixelEnergy < smallestEnergy) {
                lastPixel = x;
                smallestEnergy = pixelEnergy;
            }
        }

        int[] shortestPath = new int[picHeight];
        for (int y = picHeight - 1; y >= 0; y--) {
            shortestPath[y] = lastPixel;
            lastPixel = pathEdge[y][lastPixel];
        }
        return shortestPath;
    }

    private void checkSeamArgument(int[] seam, int pictureDimension1, int pictureDimenstion2) {
        if (seam == null) {
            throw new IllegalArgumentException("seam cannot be null");
        }
        if (seam.length != pictureDimension1) {
            throw new IllegalArgumentException("seam length incorrect or invalid picture size");
        }
        if (pictureDimenstion2 <= 1) {
            throw new IllegalArgumentException("no seams available");
        }
 
    }

    private Color[][] createColors(int w, int h) {
        Color[][] colors = new Color[h][];
        for (int i = 0; i < h; i++) {
            colors[i] = new Color[w];
        }
        return colors;
    }

    // remove horizontal seam from current picture
    public void removeHorizontalSeam(int[] seam) {
        checkSeamArgument(seam, width(), height());

        // Init new energy matrix with reduced height.
        int newHeight = energy.length - 1;
        Color[][] newColors = createColors(seam.length, newHeight);

        // Copy across the energy matrix without the energy values
        // of the seam pixels.
        for (int i = 0; i < seam.length; i++) {
            if (seam[i] < 0 || seam[i] > height() - 1) {
                throw new IllegalArgumentException("invalid seam array");
            }
            int offset = 0;
            for (int j = 0; j < newHeight; j++) {
                if (j == seam[i]) {
                    offset = 1;
                }
                Color c = colors[j + offset][i];
                assert c != null;
                newColors[j][i] = c;
            }
        }
        colors = newColors;
        computeEnergyValues();
    }

    // remove vertical seam from current picture
    public void removeVerticalSeam(int[] seam) {
        checkSeamArgument(seam, height(), width());

        // Init new energy matrix with reduced width.
        int newWidth = energy[0].length - 1;
        Color[][] newColors = createColors(newWidth, seam.length);

        // Copy across the energy matrix without the energy values
        // of the seam pixels.
        for (int i = 0; i < seam.length; i++) {
            if (seam[i] < 0 || seam[i] > width() - 1) {
                throw new IllegalArgumentException("invalid seam array");
            }
            System.arraycopy(colors[i], 0, newColors[i], 0, seam[i]);
            System.arraycopy(colors[i], seam[i]+1, newColors[i], seam[i], newWidth - seam[i]);
        }
        
        colors = newColors;
        computeEnergyValues();
    }
 }