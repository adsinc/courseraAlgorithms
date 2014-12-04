public class Outcast {

    private WordNet wordnet;

    // constructor takes a WordNet object
    public Outcast(WordNet wordnet) {
        this.wordnet = wordnet;
    }

    // given an array of WordNet nouns, return an outcast
    public String outcast(String[] nouns) {
        /**
         * Outcast detection. Given a list of wordnet nouns A1, A2, ...,
         * An, which noun is the least related to the others?
         * To identify an outcast, compute the sum of the distances between
         * each noun and every other one:
         * di = dist(Ai, A1) + dist(Ai, A2) +   ...   + dist(Ai, An)
         * and return a noun At for which dt is maximum.
         */
        int max = -1;
        String maxWord = null;
        for (String noun : nouns) {
            int d = dist(nouns, noun);
            if (d > max || max == -1) {
                max = d;
                maxWord = noun;
            }
        }
        return maxWord;
    }

    private int dist(String[] nouns, String word) {
        int d = 0;
        for (String noun : nouns) {
            d += wordnet.distance(word, noun);
        }
        return d;
    }

    public static void main(String[] args) {
        WordNet wordnet = new WordNet(args[0], args[1]);
        Outcast outcast = new Outcast(wordnet);
        for (int t = 2; t < args.length; t++) {
            In in = new In(args[t]);
            String[] nouns = in.readAllStrings();
            StdOut.println(args[t] + ": " + outcast.outcast(nouns));
        }
    }
}