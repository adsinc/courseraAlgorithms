import java.awt.*;
import java.util.Arrays;

import static java.lang.Math.min;

public class SeamCarver {

    private Picture picture;
	private final static int BorderEnergy = 195075;

    /**
     * create a seam carver object based on the given picture
     *
     * @param picture
     */
    public SeamCarver(Picture picture) {
        this.picture = new Picture(picture);
    }

    /**
     * current picture
     *
     * @return
     */
    public Picture picture() {
        return picture;
    }

    /**
     * width of current picture
     *
     * @return
     */
    public int width() {
        return picture.width();
    }

    /**
     * height of current picture
     *
     * @return
     */
    public int height() {
        return picture.height();
    }

    /**
     * energy of pixel at column x and row y
     *
     * @param x
     * @param y
     * @return
     */
    public double energy(int x, int y) {
        if(x < 0 || y < 0 
                || x > width() - 1 || y > height() - 1)
            throw new IndexOutOfBoundsException();
		if(x == 0 || y == 0
				|| x == width() - 1 || y == height() - 1)
			return BorderEnergy;

        return sqGradient(picture.get(x - 1, y), picture.get(x + 1, y))
				+ sqGradient(picture.get(x, y - 1), picture.get(x, y + 1));
    }

	private int sqGradient(Color c1, Color c2) {
		int rd = c1.getRed() - c2.getRed();
		int gd = c1.getGreen() - c2.getGreen();
		int bd = c1.getBlue() - c2.getBlue();
		return rd * rd + gd * gd + bd * bd;
	}

    /**
     * sequence of indices for horizontal seam
     *
     * @return
     */
    public int[] findHorizontalSeam() {
        return null;
    }

    /**
     * sequence of indices for vertical seam
     *
     * @return
     */
    public int[] findVerticalSeam() {
        double[][] ens = new double[width()][height()];
        int[][] paths = new int[width()][height()];
        for(int x = 0; x < width(); x++)
            for(int y = 0; y < height(); y++) {
                ens[x][y] = energy(x, y);
                if(y > 0) {
                    double val = ens[x][y - 1];
                    paths[x][y] = x;
                    if(x > 0) {
                        double m = ens[x - 1][y - 1];
                        if(m < val) {
                            val = m;
                            paths[x][y] = x - 1;
                        }
                    }
                    if(x < width() - 1) {
                        double m = ens[x + 1][y - 1];
                        if(m < val) {
                            val = m;
                            paths[x][y] = x + 1;
                        }
                    }
                    ens[x][y] += val;
                } else paths[x][y] = 0;
            }

        int[] res = new int[height()];

        //todo
        for(int y = height() - 1; y >= 0; y--) {
            int minidx = 0;
            double min = ens[minidx][height() - 1];
            for (int x = 0; x < width(); x++) {
                double val = ens[x][y];
                if (val < min) {
                    min = val;
                    minidx = x;
                }
            }
            res[y] = minidx;
        }
        return res;
    }

    /**
     * remove horizontal seam from current picture
     *
     * @param seam
     */
    public void removeHorizontalSeam(int[] seam) {
		checkSeam(seam, width(), height());
    }

    /**
     * remove vertical seam from current picture
     *
     * @param seam
     */
    public void removeVerticalSeam(int[] seam) {
		checkSeam(seam, height(), width());
    }

	private void checkSeam(int[] seam, int reqSize, int reqRange) {
		if(seam == null) throw new NullPointerException();
		if(reqSize <= 1 || reqRange <= 1)
			throw new IllegalArgumentException();
		if(seam.length != reqSize)
			throw new IllegalArgumentException("seam.length must be " + reqSize);
		if(seam.length > 1) {
			int prev = -1;
			for(int v : seam) {
				if(v < 0 || v > reqRange - 1)
					throw new IllegalArgumentException();
				if(prev > 0) {
					int diff = prev - v;
					if(diff < -1 || diff > 1)
						throw new IllegalArgumentException();
				}
				prev = v;
			}
		}
	}
}