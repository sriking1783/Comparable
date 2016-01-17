/* Starter code for NgordnetUI (part 7 of the project). Rename this file and
   remove this comment. */

package ngordnet;
import edu.princeton.cs.introcs.StdIn;
import edu.princeton.cs.introcs.In;
import java.util.Arrays;
import java.util.Set;

/** Provides a simple user interface for exploring WordNet and NGram data.
 *  @author [yournamehere mcjones]
 */
public class NgordnetUI {
    public static void main(String[] args) {
        In in = new In("./ngordnet/ngordnetui.config");
        System.out.println("Reading ngordnetui.config...");

        String wordFile = in.readString();
        String countFile = in.readString();
        String synsetFile = in.readString();
        String hyponymFile = in.readString();
        System.out.println("\nBased on ngordnetui.config, using the following: "
                           + wordFile + ", " + countFile + ", " + synsetFile +
                           ", and " + hyponymFile + ".");
        int startDate = 1500;
        int endDate =2000;
        String word = "";
        NGramMap ngm;
        while (true) {
            System.out.print("> ");
            String line = StdIn.readLine();
            String[] rawTokens = line.split(" ");
            String command = rawTokens[0];
            String[] tokens = new String[rawTokens.length - 1];
            System.arraycopy(rawTokens, 1, tokens, 0, rawTokens.length - 1);

            switch (command) {
                case "quit":
                    return;
                case "help":
                    In in_help = new In("help.txt");
                    String helpStr = in_help.readAll();
                    System.out.println(helpStr);
                    break;
                case "range":
                    startDate = Integer.parseInt(tokens[0]);
                    endDate = Integer.parseInt(tokens[1]);
                    System.out.println("Start date: " + startDate);
                    System.out.println("End date: " + endDate);
                    break;
                case "count":
                    int year = Integer.parseInt(tokens[1]);
                    word = tokens[0];
                    ngm = new NGramMap("/Users/sastaputhra/HomeWork/cs61b_spring/skeleton/proj1/demos/ngrams/all_words.csv",
                                    "/Users/sastaputhra/HomeWork/cs61b_spring/skeleton/proj1/demos/ngrams/total_counts.csv");
                    System.out.println(ngm.countInYear(word, year));
                    break;
                case "history":
                    word = tokens[0];
                    ngm = new NGramMap("/Users/sastaputhra/HomeWork/cs61b_spring/skeleton/proj1/demos/ngrams/all_words.csv",
                                    "/Users/sastaputhra/HomeWork/cs61b_spring/skeleton/proj1/demos/ngrams/total_counts.csv");

                    Plotter plotter = new Plotter();
                    plotter.plotWeightHistory(ngm, word, startDate, endDate);

                    break;
                case "hyponyms":
                    WordNet wn = new WordNet("/Users/sastaputhra/HomeWork/cs61b_spring/skeleton/proj1/demos/wordnet/synsets.txt", "/Users/sastaputhra/HomeWork/cs61b_spring/skeleton/proj1/demos/wordnet/hyponyms.txt");
                    Set<String> words = wn.hyponyms(tokens[0]);
                    System.out.println(Arrays.toString(words.toArray()));
                    break;
                case "hypohist":
                    break;
                default:
                    System.out.println("Invalid command.");
                    break;
            }
        }


    }
}
