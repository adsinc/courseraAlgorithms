import java.util.LinkedList;

public class Solver {

    private Node curNode;
    private Node twinNode;
    private Board initial;

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        this.initial = initial;

        MinPQ<Node> pq = new MinPQ<>();
        pq.insert(new Node(0, initial, null));
        // delete min node
        curNode = pq.delMin();

        MinPQ<Node> twinPq = new MinPQ<>();
        twinPq.insert(new Node(0, initial.twin(), null));
        twinNode = twinPq.delMin();

        while (!(curNode.board.isGoal() || twinNode.board.isGoal())) {
            curNode = iterate(pq, curNode);
            twinNode = iterate(twinPq, twinNode);
        }
    }

    private Node iterate(MinPQ<Node> pq, Node curNode) {
        // insert all neighboring search nodes
        for (Board board : curNode.board.neighbors()) {
            if (curNode.previous == null
                    || !board.equals(curNode.previous.board))
                pq.insert(new Node(curNode.moves + 1, board, curNode));
        }
        return pq.delMin();
    }

    // is the initial board solvable?
    public boolean isSolvable() {
        return curNode.board.isGoal();
    }

    // min number of moves to solve initial board; -1 if no solution
    public int moves() {
        if (isSolvable()) return curNode.moves;
        return -1;
    }

    // sequence of boards in a shortest solution; null if no solution
    public Iterable<Board> solution() {
        if (!isSolvable()) return null;
        LinkedList<Board> result = new LinkedList<>();
        Node node = curNode;
        while (node.previous != null) {
            result.addFirst(node.board);
            node = node.previous;
        }
        result.addFirst(initial);
        return result;
    }

    private class Node implements Comparable<Node> {

        public final int moves;
        public final Board board;
        public final Node previous;

        private Node(int moves, Board board, Node previous) {
            this.moves = moves;
            this.board = board;
            this.previous = previous;
        }

        public int getPriority() {
            return board.manhattan() + moves;
        }

        @Override
        public int compareTo(Node o) {
            if (getPriority() == o.getPriority()) return 0;
            else return getPriority() - o.getPriority();
        }
    }

    // solve a slider puzzle (given below)
    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int N = in.readInt();
        int[][] blocks = new int[N][N];
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
}