import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class WordNet {

    private Map<String, Set<Integer>> syns = new HashMap<>();
    private Map<Integer, String> synMap = new HashMap<>();
    private Digraph hg;
    private SAP sap;

    // constructor takes the name of the two input files
    public WordNet(String synsets, String hypernyms) {
        readSynsets(synsets);
        readHypernyms(hypernyms);
        DirectedCycle cycle = new DirectedCycle(hg);
        if (!isRootedDAG() || cycle.hasCycle())
            throw new IllegalArgumentException();
        sap = new SAP(hg);
    }

    private boolean isRootedDAG() {
        int roots = 0;
        for (int i = 0; i < hg.V(); i++) {
            if (!hg.adj(i).iterator().hasNext()) roots++;
        }
        return roots == 1;
    }

    private void readSynsets(String synsets) {
        // 36,AND_circuit AND_gate,a circuit in a
        // computer that fires only when all of its inputs fire
        In ssIn = new In(synsets);
        String[] lines = ssIn.readAllLines();
        for (String line : lines) {
            String[] lex = line.split(",");
            int id = Integer.parseInt(lex[0]);
            for (String l : lex[1].split(" ")) {
                if (!syns.containsKey(l)) syns.put(l, new HashSet<Integer>());
                syns.get(l).add(id);
            }
            synMap.put(id, lex[1]);
        }
    }

    private void readHypernyms(String hypernyms) {
        hg = new Digraph(synMap.size());
        //164,21012,56099
        In hnIn = new In(hypernyms);
        String[] lines = hnIn.readAllLines();
        for (String line : lines) {
            String[] lex = line.split(",");
            int id = Integer.parseInt(lex[0]);
            for (int i = 1; i < lex.length; i++)
                hg.addEdge(id, Integer.parseInt(lex[i]));
        }
    }

    // returns all WordNet nouns
    public Iterable<String> nouns() {
        return syns.keySet();
    }

    // is the word a WordNet noun?
    public boolean isNoun(String word) {
        checkArg(word);
        return syns.keySet().contains(word);
    }

    // distance between nounA and nounB (defined below)
    public int distance(String nounA, String nounB) {
        checkArg(nounA);
        checkArg(nounB);
        return sap.length(syns.get(nounA), syns.get(nounB));
    }

    // a synset (second field of synsets.txt) that is
    // the common ancestor of nounA and nounB
    // in a shortest ancestral path (defined below)
    public String sap(String nounA, String nounB) {
        checkArg(nounA);
        checkArg(nounB);
        int id = sap.ancestor(syns.get(nounA), syns.get(nounB));
        return synMap.get(id);
    }

    private void checkArg(String word) {
        if (word == null)
            throw new NullPointerException();
        if (!syns.containsKey(word))
            throw new IllegalArgumentException();
    }

    // do unit testing of this class
    public static void main(String[] args) {
        String synset = "wordnet/examples/synsets15.txt";
        String hypernym =
                "wordnet/examples/hypernyms15Path.txt";
        WordNet net = new WordNet(synset, hypernym);
        System.out.println(net.sap("invalid", "Aphis_fabae"));
        System.out.println(net.distance("festoon", "invalid"));
    }
}