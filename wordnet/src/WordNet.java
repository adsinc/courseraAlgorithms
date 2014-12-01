import java.util.*;

public class WordNet {

   private Map<Integer, Set<String>> synsetsMap = new HashMap<>();
   private Map<Integer, String> glossMap = new HashMap<>();
   private Map<Integer, Set<Integer>> hypernymsMap = new HashMap<>();

   // constructor takes the name of the two input files
   public WordNet(String synsets, String hypernyms) {
      readSynsets(synsets);
      readHypernyms(hypernyms);
   }

   private void readSynsets(String synsets) {
      // 36,AND_circuit AND_gate,a circuit in a computer that fires only when all of its inputs fire
      In ssIn = new In(synsets);
      String[] lines = ssIn.readAllStrings();
      for (String line: lines) {
         String[] lex = line.split(",");
         try {
            Integer id = Integer.parseInt(lex[0]);
            Set<String> sset = new HashSet<>();
            Collections.addAll(sset, lex[1].split(" "));
            synsetsMap.put(id, sset);
            glossMap.put(id, lex[2]);
         } catch (Exception e) {
            throw new IllegalArgumentException();
         }
      }
   }

   private void readHypernyms(String hypernyms) {
      //164,21012,56099
      In hnIn = new In(hypernyms);
      String[] lines = hnIn.readAllStrings();
      for(String line: lines) {
         String[] lex = line.split(",");
         try {
            Integer id = Integer.parseInt(lex[0]);
            Set<Integer> hset = new HashSet<>();
            for (int i = 1; i < lex.length; i++)
               hset.add(Integer.parseInt(lex[i]));
            hypernymsMap.put(id, hset);
         } catch (Exception e) {
            throw new IllegalArgumentException();
         }
      }
   }

   private Set<String> nounsSet() {
      Set<String> nouns = new HashSet<>();
      for(Set<String> s : synsetsMap.values())
         nouns.addAll(s);
      return nouns;
   }

   // returns all WordNet nouns
   public Iterable<String> nouns() {
       return nounsSet();
   }

   // is the word a WordNet noun?
   public boolean isNoun(String word) {
      return nounsSet().contains(word);
   }

   // distance between nounA and nounB (defined below)
   public int distance(String nounA, String nounB) {
      return -1;
   }

   // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
   // in a shortest ancestral path (defined below)
   public String sap(String nounA, String nounB) {
      return null;
   }

   // do unit testing of this class
   public static void main(String[] args) {

   }
}