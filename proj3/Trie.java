/**
 * Prefix-Trie. Supports linear time find() and insert().
 * Should support determining whether a word is a full word in the
 * Trie or a prefix.
 * @author
 */
import java.util.*;

public class Trie {
    private TrieNode root;
    private static String sortOrder;
    private class TrieNode {
        char c;
        TreeMap<Character, TrieNode> links;
        boolean is_word;
        boolean visited = false;
        public TrieNode() {
            this.links = new TreeMap<Character, TrieNode>(new SortOrder());
            this.is_word = false;
        }
        public TrieNode(char ch){
            this.c = ch;
            this.links = new TreeMap<Character, TrieNode>(new SortOrder());
            this.is_word = false;
        }

        public TreeMap<Character, TrieNode> getChildren() {
            return this.links;
        }
    }

    private class SortOrder implements Comparator<Character> {
        @Override
        public int compare(Character c1, Character c2) {
            return sortOrder.indexOf(c1) -  sortOrder.indexOf(c2) ;
        }

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

    public void insert(String s) {
        insert(root, s, 0);
    }

    private void dfs(TrieNode node, Stack<Character> nodes_to_visit, String str){
        node.visited = true;

        for(Map.Entry<Character, TrieNode> val : node.links.entrySet()) {
            TrieNode temp = val.getValue();
            if(!temp.visited ){
                //str = str + val.getKey();
                nodes_to_visit.push(val.getKey());
                if(temp.is_word) {
                    System.out.println(str + val.getKey());
                    //str = "";
                }
                dfs(temp, nodes_to_visit, str + val.getKey());

            }
        }
    }

    public void getWords() {
        TrieNode temp = root;
        Stack<Character> nodes_to_visit = new Stack<Character>();
        dfs(root, nodes_to_visit, "");
    }

    public TrieNode insert(TrieNode node, String s, int d) {
        if(node == null) {
           node = new TrieNode();
        }

        if(d == s.length()) {
            node.is_word = true;
            return node;
        }

        char c = s.charAt(d);
        node.links.put(c, insert(node.links.get(c), s, d+1));
        return node;
    }

    public Trie() {
        this.root = new TrieNode();
        this.sortOrder = "abcdefghijklmnopqrstuvwxyz";
    }
    public Trie(String sort_order) {
        this.root = new TrieNode();
        this.sortOrder = sort_order;
        System.out.println(sort_order);
    }

    // public static void main(String[] args) {
    //     Trie t = new Trie();
    //     t.insert("hello");
    //     t.insert("hey");
    //     t.insert("goodbye");
    //     System.out.println(t.find("hell", false));
    //     System.out.println(t.find("hello", true));
    //     System.out.println(t.find("good", false));
    //     System.out.println(t.find("bye", false));
    //     System.out.println(t.find("heyy", false));
    //     System.out.println(t.find("hell", true));
    // }
}
