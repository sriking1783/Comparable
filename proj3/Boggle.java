import java.util.*;
import java.io.*;
public class Boggle {
    public static void traverseElements(char[][] boggle_strings, int height, int width, Trie t) {
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

    public static void printNeighbors(char[][] boggle_strings, int i, int j, int height, int width) {
        for(int x = Math.max(0, i-1); x <= Math.min(i+1, height); x++){
          for(int y = Math.max(0, j-1); y <= Math.min(j+1, width); y++){
            if((x != i || y != j) && (x < height && y < width)){
              System.out.print(boggle_strings[x][y]);
            }
          }

        }
    }

    public static void getNeighbours(char[][] boggle_strings, int i, int j, int height, int width, String str, Trie t, Set<String> words) {
        ArrayList<String> chars = new ArrayList<String>();
        //String chars = "";
        for(int x = Math.max(0, i-1); x <= Math.min(i+1, height); x++){
          for(int y = Math.max(0, j-1); y <= Math.min(j+1, width); y++){
            if((x != i || y != j) && (x < height && y < width)){
                if(t.hasAtLeastOneWord(str+boggle_strings[x][y])) {
                    //str = str+boggle_strings[x][y];
                    getNeighbours(boggle_strings, x, y, height, width, str+boggle_strings[x][y], t, words);
                }
                else if(t.find(str, true) && (!words.contains(str))) {
                    System.out.println(str);
                    words.add(str);
                }
              chars.add(Integer.toString(x)+","+ Integer.toString(y));
            }
          }
        }
    }

    public static void searchWords(char[][] boggle_strings, int height, int width, Trie t, String str) {
        String chars = "";
        Set<String> words = new TreeSet<String>();
        for(int i = 0; i < height; i++) {
            for(int j = 0; j < width; j++) {
                    getNeighbours(boggle_strings, i, j, height, width, Character.toString(boggle_strings[i][j]), t, words);
            }
        }


    }

    public static void main(String[] args) {
      System.out.println(Arrays.toString(args));
      int total_number_of_words = 1; //k
      int width = 4; //n
      int height = 4; //m
      String path_to_dictionary = "/usr/share/dict/words"; //d
      String filename = "/Users/sastaputhra/HomeWork/cs61b_spring/skeleton/proj3/test";
      char[][] boggle_strings;
      ArrayList<String> strings = new ArrayList<String>();

      try(BufferedReader br = new BufferedReader(new FileReader(filename))) {
          for(String line; (line = br.readLine()) != null;) {
              strings.add(line);
          }
      }

      catch(FileNotFoundException ex) {
          ex.printStackTrace();
      }
      catch(IOException ex) {
          ex.printStackTrace();
      }

      height = strings.size();
      width  = strings.get(0).length();
      boggle_strings = new char[height][width];
      int j = 0;
      for(String str : strings) {
          for(int i = 0; i < str.length(); i++) {
            boggle_strings[j][i] = str.charAt(i);
          }
          j++;
      }

      for(int i = 0; i < height; i++) {
          for(int k = 0; k < width; k++) {
              System.out.print(boggle_strings[i][k]);
          }
          System.out.println();
      }

      String temp = "";

      Trie t = new Trie();
      try(BufferedReader br = new BufferedReader(new FileReader(path_to_dictionary))) {
          for(String line; (line = br.readLine()) != null;) {
              t.insert(line);
          }
      }
      catch(FileNotFoundException ex) {
          ex.printStackTrace();
      }
      catch(IOException ex) {
          ex.printStackTrace();
      }

      traverseElements(boggle_strings, height, width, t);
    }
}
