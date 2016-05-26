import java.util.*;
/**
 * Implements autocomplete on prefixes for a given dictionary of terms and weights.
 */
public class Autocomplete {
    WeightedTrie t;
    /**
     * Initializes required data structures from parallel arrays.
     * @param terms Array of terms.
     * @param weights Array of weights.
     */
    public Autocomplete(String[] terms, double[] weights) {
        t = new WeightedTrie();
        for(int i = 0; i < terms.length; i++) {
            t.insert(terms[i], weights[i]);
        }
    }

    /**
     * Find the weight of a given term. If it is not in the dictionary, return 0.0
     * @param term
     * @return
     */
    public double weightOf(String term) {
        return t.findWeight(term);
    }

    /**
     * Return the top match for given prefix, or null if there is no matching term.
     * @param prefix Input prefix to match against.
     * @return Best (highest weight) matching string in the dictionary.
     */
    public String topMatch(String prefix) {
        return topMatches(prefix, 1).iterator().next().toString();
    }

    /**
     * Returns the top k matching terms (in descending order of weight) as an iterable.
     * If there are less than k matches, return all the matching terms.
     * @param prefix
     * @param k
     * @return
     */
    public Iterable<String> topMatches(String prefix, int k) {
        HashMap<String, Double> matches =  t.findMatches(prefix, k);
        LinkedHashSet<String> matched_words = new LinkedHashSet<String>();
        if(matches == null) {
            Iterator<String> it =  spellCheck(prefix, 1, k).iterator();
            int i = 0;
            System.out.println("00000000000");
            while (it.hasNext()) {
                String word = it.next();
                System.out.println(word);
                matched_words.add(word);
                i++;
                it.remove(); // avoids a ConcurrentModificationException
            }
        }
        else{
            Iterator it =  sortByValue(matches).entrySet().iterator();
            int i = 0;
            while (it.hasNext() && i < k) {
                Map.Entry pair = (Map.Entry)it.next();
                matched_words.add(pair.getKey().toString());
                i++;
                it.remove(); // avoids a ConcurrentModificationException
            }
        }

        return matched_words;
    }

    private HashMap<String, Double> sortByValue(HashMap<String, Double> matches){
        List<Map.Entry<String, Double>> list =
            new LinkedList<Map.Entry<String, Double>>(matches.entrySet());

        Collections.sort(list, new Comparator<Map.Entry<String, Double>>(){
            public int compare(Map.Entry<String, Double> o1, Map.Entry<String, Double> o2){
                return(o1.getValue()).compareTo(o2.getValue());
            }
        });
        Collections.reverse(list);
        HashMap<String, Double> sortedMatches = new LinkedHashMap<String, Double>();
        for(Iterator<Map.Entry<String, Double>> it = list.iterator(); it.hasNext();){
            Map.Entry<String, Double> entry = it.next();
            sortedMatches.put(entry.getKey(), entry.getValue());
        }
        return sortedMatches;
    }

    /**
     * Returns the highest weighted matches within k edit distance of the word.
     * If the word is in the dictionary, then return an empty list.
     * @param word The word to spell-check
     * @param dist Maximum edit distance to search
     * @param k    Number of results to return
     * @return Iterable in descending weight order of the matches
     */
    public Iterable<String> spellCheck(String word, int dist, int k) {
        LinkedList<String> results = new LinkedList<String>();
        /* YOUR CODE HERE; LEAVE BLANK IF NOT PURSUING BONUS */
        //search(word, dist, k);
        return t.search(word, dist, k);
    }
    /**
     * Test client. Reads the data from the file,
     * then repeatedly reads autocomplete queries from standard input and prints out the top k matching terms.
     * @param args takes the name of an input file and an integer k as command-line arguments
     */
    public static void main(String[] args) {
        // initialize autocomplete data structure
        In in = new In(args[0]);
        int N = in.readInt();
        String[] terms = new String[N];
        double[] weights = new double[N];
        for (int i = 0; i < N; i++) {
            weights[i] = in.readDouble();   // read the next weight
            in.readChar();                  // scan past the tab
            terms[i] = in.readLine();       // read the next term
        }

        Autocomplete autocomplete = new Autocomplete(terms, weights);

        // process queries from standard input
        int k = Integer.parseInt(args[1]);
        while (StdIn.hasNextLine()) {
            String prefix = StdIn.readLine();
            for (String term : autocomplete.topMatches(prefix, k))
                StdOut.printf("%14.1f  %s\n", autocomplete.weightOf(term), term);
        }
    }
}
