import java.util.*;

public class WeightedTrie {
    public TrieNode root;
    private class TrieNode {
        HashMap<Character, TrieNode> links;
        boolean is_word;
        String word;
        boolean visited = false;
        double weight;
        public TrieNode() {
            this.links = new HashMap<Character, TrieNode>();
            this.is_word = false;
            this.weight = 0;
        }
    }

    public HashMap<String, Double> search(String word, int dist, int k) {
        HashMap<String, Double> matched_words = new HashMap<String, Double>();
        int size = word.length();
        int[] currentRow = new int[size + 1];

        for(int i = 0; i < size; i++) {
            currentRow[i] = i;
        }

        for(Character c: root.links.keySet()){
            searchRec(root.links.get(c), c, word, "", matched_words, currentRow, dist);
        }
        return matched_words;
    }

    public void searchRec(TrieNode node, char letter, String word, String substr, HashMap<String, Double> matched_words, int[] previousRow, int dist) {
        int size = previousRow.length;
        int[] currentRow = new int[size];
        currentRow[0] = previousRow[0] + 1;
        int minimumElement = currentRow[0];
        int replaceCost, insertCost, deleteCost;
        int i;
        for(i = 1; i < size; i++) {
            char c = word.charAt(i - 1);
            insertCost = currentRow[i - 1] + 1;
            deleteCost = previousRow[i] + 1;
            replaceCost = (c == letter) ? previousRow[i - 1] : (previousRow[i - 1] + 1);
            currentRow[i] = minimum(insertCost, deleteCost, replaceCost);
        }

        if(currentRow[size - 1] <= dist && node.is_word && substr.length() > 0) {
            matched_words.put(node.word, node.weight);
        }

        minimumElement = Arrays.stream(currentRow).min().getAsInt();
        if(minimumElement <= dist) {
            for(Character c: node.links.keySet()){
                searchRec(node.links.get(c), c, word, substr+c, matched_words, currentRow, dist);
            }
        }
    }

    private int minimum(int a, int b, int c) {
        if(a < b && a < c)
            return a;
        else if(b < a && b < c)
            return b;
        else
            return c;
    }

    public boolean find(String s, boolean isFullWord) {
        TrieNode temp = root;

        for(int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if(temp.links.containsKey(c)) {
                temp = temp.links.get(c);
            }
            else {
                return false;
            }
        }

        return (temp.is_word == isFullWord);
    }

    public Double findWeight(String s) {
        TrieNode temp = root;

        for(int i = 0; i < s.length(); i++){
            char c = s.charAt(i);
            if(temp.links.containsKey(c)) {
                temp = temp.links.get(c);
            }
            else {
                return 0.0;
            }
        }

        return temp.weight;
    }

    public HashMap<String, Double> findMatches(String prefix, int k) {
        TrieNode temp = root;
        String str = "";
        for(int i = 0; i < prefix.length(); i++){
            char c = prefix.charAt(i);
            if(temp.links.containsKey(c)) {
                str = str + c;
                temp = temp.links.get(c);
            }
            else {
                return null;
            }
        }
        HashMap<String, Double> string_matches = new HashMap<String, Double>();
        Stack<Character> nodes_to_visit = new Stack<Character>();
        dfs(temp, nodes_to_visit, string_matches, str);
        return string_matches;
    }

    private void dfs(TrieNode node, Stack<Character> nodes_to_visit, HashMap<String, Double> string_matches, String str) {
        node.visited = true;
        for(Map.Entry<Character, TrieNode> val : node.links.entrySet()) {
            TrieNode temp = val.getValue();
                if(!temp.visited ){
                    char ch = val.getKey();
                    nodes_to_visit.push(ch);
                    if(temp.is_word) {
                        //System.out.println(str + ch);
                        string_matches.put(str + ch, temp.weight);
                        //str = "";
                    }
                    dfs(temp, nodes_to_visit, string_matches, str + ch);
                }
        }
    }

    public void insert(String word, double weight) {
        insert(root, word, 0, weight);
    }

    public TrieNode insert(TrieNode node, String s, int d, double weight) {
        if(node == null) {
           node = new TrieNode();
        }

        if(d == s.length()) {
            node.is_word = true;
            node.weight = weight;
            node.word = s;
            return node;
        }

        char c = s.charAt(d);
        node.links.put(c, insert(node.links.get(c), s, d+1, weight));
        return node;
    }

    public WeightedTrie() {
        this.root = new TrieNode();
    }
}
