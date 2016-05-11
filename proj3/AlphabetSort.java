import java.io.IOException;
import java.util.Arrays;
import java.io.FileNotFoundException;
import java.io.BufferedReader;
import java.io.FileReader;
public class AlphabetSort {
    public static void main(String[] args) {
        String fileName = args[0];
        Trie t = null;
        try (BufferedReader br = new BufferedReader(new FileReader("/Users/sastaputhra/HomeWork/cs61b_spring/skeleton/proj3/"+fileName))) {
            String line;
            int i = 0;
            while ((line = br.readLine()) != null) {
                //System.out.println(line);
                if(i == 0){
                    t = new Trie(line);
                }
                else {
                    t.insert(line);
                }
                i++;
            }
            t.getWords();
        }
        catch(FileNotFoundException ex){
            ex.printStackTrace();
        }
        catch(IOException ex){
            ex.printStackTrace();
        }
    }
}
