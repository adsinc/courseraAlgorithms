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
		checkSeam(seam, width());
    }

    /**
     * remove vertical seam from current picture
     *
     * @param seam
     */
    public void removeVerticalSeam(int[] seam) {
		checkSeam(seam, height());
    }

	private void checkSeam(int[] seam, int required) {
		if(seam == null) throw new NullPointerException();
		if(seam.length != required)
			throw new IllegalArgumentException("seam.length must be " + required);
	}
}