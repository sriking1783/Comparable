/**
 * Prefix-Trie. Supports linear time find() and insert().
 * Should support determining whether a word is a full word in the
 * Trie or a prefix.
 * @author
 */
import java.util.Arrays;
import java.util.TreeMap;
public class Trie {
    private TrieNode root;
    private static final int R = 128;
    private class TrieNode {
        char c;
        TreeMap<Character, TrieNode> links;
        boolean is_word;
        public TrieNode() {
            this.links = new TreeMap<Character, TrieNode>();
            this.is_word = false;
        }
        public TrieNode(char ch){
            this.c = ch;
            this.links = new TreeMap<Character, TrieNode>();
            this.is_word = false;
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
        root = new TrieNode();
    }

    public static void main(String[] args) {
        Trie t = new Trie();
        t.insert("hello");
        t.insert("hey");
        t.insert("goodbye");
        System.out.println(t.find("hell", false));
        System.out.println(t.find("hello", true));
        System.out.println(t.find("good", false));
        System.out.println(t.find("bye", false));
        System.out.println(t.find("heyy", false));
        System.out.println(t.find("hell", true));
    }
}
