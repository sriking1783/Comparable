import java.util.*;
import java.io.*;
public class Boggle {


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
      BoggleGame bg = new BoggleGame();
      bg.traverseElements(boggle_strings, height, width, t);
    }
}
