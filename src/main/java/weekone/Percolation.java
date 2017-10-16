package weekone;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private int n;
    private boolean[] sites;
    private int numberOpenSites;
    private final WeightedQuickUnionUF uf;
    private final WeightedQuickUnionUF uf2;

    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("n must be greater than 0");
        }
        this.n = n;
        int numSites = (n + 2) * n;
        sites = new boolean[numSites];
        uf = new WeightedQuickUnionUF(numSites);
        uf2 = new WeightedQuickUnionUF(numSites);

        // Open percolation start and finish sites.
        uf.union(0, 0);
        uf.union(numSites - 1, numSites - 1);
        // Only start site for backwash checking.
        uf2.union(0, 0);
    }

    private int position(int row, int col) {
        return (row * n) + (col - 1);
    }

    private void throwIfOutOfRange(int row, int col) {
        if (row > n || row < 1 || col < 1 || col > n) {
            throw new IllegalArgumentException("row or column outside of percolation dimension");
        }
    }

    public void open(int row, int col) {
        throwIfOutOfRange(row, col);
        int pos = position(row, col);

        if (!isOpen(row, col)) {
            // Open this site.
            sites[pos] = true;
            numberOpenSites++;

            // Check each adjacent site and join open ones.
            int[] adjacents = new int[] {-1, -1, -1, -1};
            // Left
            if (col != 1) {
                adjacents[0] = pos - 1;
            }
            // Up
            if (row != 1) {
                adjacents[1] = pos - n;
            }
            else {
                // Connect to top virtual site.
                uf.union(pos, 0);
                uf2.union(pos, 0);
            }
            // Right
            if (col != n) {
                adjacents[2] = pos + 1;
            }
            // Down
            if (row != n) {
                adjacents[3] = pos + n;
            } else {
                // Connect to bottom virtual site.
                uf.union(pos, sites.length-1);
            }

            for (int adj: adjacents) {
                if (adj == -1)
                    continue;
                if (sites[adj]) {
                    uf.union(pos, adj);
                    uf2.union(pos, adj);
                }
            }
        }
    }

    public boolean isOpen(int row, int col) {
        throwIfOutOfRange(row, col);
        return (sites[position(row, col)]);
    }

    public boolean isFull(int row, int col) {
        throwIfOutOfRange(row, col);
        int pos = position(row, col);
        return uf2.connected(0, pos);
    }

    public int numberOfOpenSites() {
        return numberOpenSites;
    }

    public boolean percolates() {
        // Check the start and finish have same root site.
        return uf.connected(0, sites.length - 1);
    }

    private static void print(Percolation p) {
        for (int i = 0; i <= p.n + 1; i++) {
            StringBuffer row = new StringBuffer();
            for (int j = 1; j <= p.n; j++) {
                if (!p.sites[p.position(i, j)]) {
                    row.append("x");
                }
                else {
                    row.append("o");
                }
            }
        }
    }
}