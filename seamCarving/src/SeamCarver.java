public class SeamCarver {

    private Picture picture;

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
        return null;
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
        return -1;
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
        return null;
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