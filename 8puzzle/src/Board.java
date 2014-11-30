import java.util.LinkedList;
import java.util.List;

public class Board {

    private int[][] blocks;

    // construct a board from an N-by-N array of blocks
    // (where blocks[i][j] = block in row i, column j)
    public Board(int[][] blocks) {
        this.blocks = new int[blocks.length][blocks.length];
        for (int i = 0; i < blocks.length; i++)
            System.arraycopy(blocks[i], 0, this.blocks[i], 0, blocks.length);
    }

    // board dimension N
    public int dimension() {
        return blocks.length;
    }

    // number of blocks out of place
    public int hamming() {
        int humming = 0;
        int position = 1;
        for (int[] row : blocks) {
            for (int block : row) {
                if (block > 0 && block != position) humming++;
                position++;
            }
        }
        return humming;
    }

    // sum of Manhattan distances between blocks and goal
    public int manhattan() {
        int manhattan = 0;
        for (int r = 0; r < dimension(); r++)
            for (int c = 0; c < blocks[r].length; c++) {
                int block = blocks[r][c];
                if (block == 0) continue;
                int correctRow = (block - 1) / dimension();
                int correctCol = (block - 1) % dimension();
                manhattan += abs(correctRow - r) + abs(correctCol - c);
            }
        return manhattan;
    }

    private int abs(int n) {
        if (n < 0) return -n;
        return n;
    }

    // is this board the goal board?
    public boolean isGoal() {
        int position = 1;
        for (int[] row : blocks) {
            for (int block : row) {
                if (block > 0 && block != position) return false;
                position++;
            }
        }
        return true;
    }

    // a board obtained by exchanging two adjacent blocks in the same row
    public Board twin() {
        int[][] copy = new int[dimension()][dimension()];
        boolean changed = false;
        for (int r = 0; r < dimension(); r++)
            for (int c = 0; c < blocks[r].length; c++) {
                copy[r][c] = blocks[r][c];
                if (!changed && c > 0 && copy[r][c] != 0 && copy[r][c - 1] != 0) {
                    changed = true;
                    int tmp = copy[r][c];
                    copy[r][c] = copy[r][c - 1];
                    copy[r][c - 1] = tmp;
                }
            }
        return new Board(copy);
    }

    // does this board equal y?
    public boolean equals(Object y) {
        if (y == null
                || getClass() != y.getClass()
                || dimension() != ((Board) y).dimension())
            return false;
        Board other = (Board) y;
        boolean isEqual = true;
        for (int i = 0; i < dimension(); i++)
            for (int j = 0; j < dimension(); j++)
                if (blocks[i][j] != other.blocks[i][j]) {
                    isEqual = false;
                    break;
                }
        return isEqual;
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        List<Board> result = new LinkedList<>();
        // shift row, col
        LinkedList<Integer[]> shifts = new LinkedList<>();
        shifts.add(new Integer[]{-1, 0});
        shifts.add(new Integer[]{0, 1});
        shifts.add(new Integer[]{1, 0});
        shifts.add(new Integer[]{0, -1});
        int zeroRow = -1;
        int zeroCol = -1;
        for (int r = 0; r < dimension(); r++)
            for (int c = 0; c < blocks[r].length; c++)
                if (blocks[r][c] == 0) {
                    zeroRow = r;
                    zeroCol = c;
                }

        while (shifts.size() > 0) {
            boolean cellFinded = false;
            int newZeroRow = -1;
            int newZeroCol = -1;
            while (!cellFinded && shifts.size() > 0) {
                Integer[] sft = shifts.removeFirst();
                newZeroRow = zeroRow + sft[0];
                newZeroCol = zeroCol + sft[1];
                if (newZeroRow >= 0 && newZeroRow < dimension()
                        && newZeroCol >= 0 && newZeroCol < dimension())
                    cellFinded = true;
            }
            if (!cellFinded) continue;
            int[][] copyBlocks = new int[blocks.length][blocks.length];

            for (int i = 0; i < blocks.length; i++)
                System.arraycopy(blocks[i], 0, copyBlocks[i], 0, blocks.length);

            copyBlocks[zeroRow][zeroCol] = copyBlocks[newZeroRow][newZeroCol];
            copyBlocks[newZeroRow][newZeroCol] = 0;
            result.add(new Board(copyBlocks));
        }
        return result;
    }

    // string representation of the board (in the output format specified below)
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(dimension() + "\n");
        for (int i = 0; i < dimension(); i++) {
            for (int j = 0; j < dimension(); j++) {
                s.append(String.format("%2d ", blocks[i][j]));
            }
            s.append("\n");
        }
        return s.toString();
    }
}