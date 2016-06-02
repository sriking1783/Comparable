import java.util.*;
import java.io.*;
public class BoggleGame {
    private class SortOrder implements Comparator<String> {
        @Override
        public int compare(String s1, String s2) {
            if (s1.length() > s2.length()) {
               return -1;
           } else if (s1.length() < s2.length()) {
               return 1;
           } else {
               return s1.compareTo(s2);
           }
        }

    }
    public void traverseElements(char[][] boggle_strings, int height, int width, Trie t) {
        Set<String> words = new HashSet<String>();
        for(int i = 0; i < height; i++) {
            for(int j = 0; j < width; j++) {
                System.out.println();
                System.out.print(boggle_strings[i][j]+"---");
                printNeighbors(boggle_strings, i, j, height, width);
            }

        }
        searchWords(boggle_strings, height, width, t, "");
    }

    public   void printNeighbors(char[][] boggle_strings, int i, int j, int height, int width) {
        for(int x = Math.max(0, i-1); x <= Math.min(i+1, height); x++){
          for(int y = Math.max(0, j-1); y <= Math.min(j+1, width); y++){
            if((x != i || y != j) && (x < height && y < width)){
              System.out.print(boggle_strings[x][y]);
            }
          }

        }
    }

    public void getNeighbours(char[][] boggle_strings, int i, int j, int height, int width, String str, Trie t, Set<String> words) {
        //String chars = "";
        for(int x = Math.max(0, i-1); x <= Math.min(i+1, height); x++){
          for(int y = Math.max(0, j-1); y <= Math.min(j+1, width); y++){
            if((x != i || y != j) && (x < height && y < width)){
                if(t.hasAtLeastOneWord(str+boggle_strings[x][y])) {
                    getNeighbours(boggle_strings, x, y, height, width, str+boggle_strings[x][y], t, words);
                }
                else if(t.find(str, true) && (!words.contains(str))) {
                    //System.out.println(str);
                    words.add(str);
                }
            }
          }
        }
    }

    public void searchWords(char[][] boggle_strings, int height, int width, Trie t, String str) {
        String chars = "";
        Set<String> words = new TreeSet<String>(
        new Comparator<String>() {
            @Override
            public int compare(String s1, String s2) {
                if (s1.length() > s2.length()) {
                    return -1;
                } else if (s1.length() < s2.length()) {
                    return 1;
                } else {
                    return s1.compareTo(s2);
                }
            }
        });
        for(int i = 0; i < height; i++) {
            for(int j = 0; j < width; j++) {
                    getNeighbours(boggle_strings, i, j, height, width, Character.toString(boggle_strings[i][j]), t, words);
            }
        }
        for(String word : words) {
            System.out.println(word);
        }

    }
}
