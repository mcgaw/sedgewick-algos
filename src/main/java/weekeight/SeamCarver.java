package weekeight;

import java.util.Arrays;

import edu.princeton.cs.algs4.Picture;


public class SeamCarver {

    private static final int BORDER_ENERGY = 1000;

    private Picture picture;

    // Horizontal energy matrix where energy[y][x] is the energy of the pixel
    // in the y-th row, x-th column.
    private double[][] energy;

    // create a seam carver object based on the given picture
    public SeamCarver(Picture picture) {
        if (picture == null) {
            throw new IllegalArgumentException("picture cannot be null");
        }
        this.picture = new Picture(picture);
        energy = new double[picture.height()][];
        // Precompute the energy of all the pixels.
        for (int y = 0; y < picture.height(); y++) {
            energy[y] = new double[picture.width()];
            for (int x = 0; x < picture.width(); x++) {
                energy[y][x] = energy(x, y);
            }
        }
    }

    // current picture
    public Picture picture() {
        return new Picture(this.picture);
    }

    // width of current picture
    public int width() {
        return this.picture.width();
    }

    // height of current picture
    public int height() {
        return this.picture.height();
    }

    private double xEnergySquared(int x, int y) {
        assert x != 0 && x != width() - 1;
        return Math.pow(picture.get(x + 1, y).getRed() - picture.get(x - 1, y).getRed(), 2) +
            Math.pow(picture.get(x + 1, y).getGreen() - picture.get(x - 1, y).getGreen(), 2) +
            Math.pow(picture.get(x + 1, y).getBlue() - picture.get(x - 1, y).getBlue(), 2);
    }

    private double yEnergySquared(int x, int y) {
        assert y != 0 && y != height() - 1;
        return Math.pow(picture.get(x, y + 1).getRed() - picture.get(x, y - 1).getRed(), 2) +
            Math.pow(picture.get(x, y + 1).getGreen() - picture.get(x, y - 1).getGreen(), 2) +
            Math.pow(picture.get(x, y + 1).getBlue() - picture.get(x, y - 1).getBlue(), 2);
    }

    // energy of pixel at column x and row y
    public double energy(int x, int y) {
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
            picHeight = picture.width();
            picWidth = picture.height(); 
        } else {
            picHeight = picture.height();
            picWidth = picture.width(); 
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

    private void checkSeamArgument(int[] seam, int pictureDimension) {
        if (seam == null) {
            throw new IllegalArgumentException("seam cannot be null");
        }
        if (seam.length != pictureDimension || pictureDimension <= 1) {
            throw new IllegalArgumentException("seam length incorrect or invalid picture size");
        }
 
    }

    // remove horizontal seam from current picture
    public void removeHorizontalSeam(int[] seam) {
        checkSeamArgument(seam, picture.width());
    }

    // remove vertical seam from current picture
    public void removeVerticalSeam(int[] seam) {
        checkSeamArgument(seam, picture.height());
    }
 }