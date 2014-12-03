import java.util.HashMap;
import java.util.Map;

public class WordNet {

    private Map<String, Integer> syns = new HashMap<>();
    private Map<Integer, String> glossMap = new HashMap<>();
    private Digraph hg;
    private SAP sap;

    // constructor takes the name of the two input files
    public WordNet(String synsets, String hypernyms) {
        readSynsets(synsets);
        readHypernyms(hypernyms);
        sap = new SAP(hg);
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
                if(syns.containsKey(l)) System.out.println("contains " + l);
                syns.put(l, id);
            }
            glossMap.put(id, lex[2]);
        }
    }

    private void readHypernyms(String hypernyms) {
        hg = new Digraph(glossMap.size());
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
        return syns.keySet().contains(word);
    }

    // distance between nounA and nounB (defined below)
    public int distance(String nounA, String nounB) {
        return sap.length(syns.get(nounA), syns.get(nounB));
    }

    // a synset (second field of synsets.txt) that is
    // the common ancestor of nounA and nounB
    // in a shortest ancestral path (defined below)
    public String sap(String nounA, String nounB) {
        int id = sap.ancestor(syns.get(nounA), syns.get(nounB));
        for (Map.Entry<String, Integer> e : syns.entrySet()) {
            if (id == e.getValue()) return e.getKey();
        }
        return null;
    }

    // do unit testing of this class
    public static void main(String[] args) {
        String synset = "wordnet/examples/synsets1000-subgraph.txt";
        String hypernym =
                "wordnet/examples/hypernyms1000-subgraph.txt";
        WordNet net = new WordNet(synset, hypernym);
        System.out.println(net.isNoun("a"));
        System.out.println(net.isNoun("b"));
        System.out.println(net.isNoun("z"));
    }
}