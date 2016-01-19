/* Starter code for NgordnetUI (part 7 of the project). Rename this file and
   remove this comment. */

package ngordnet;
import edu.princeton.cs.introcs.StdIn;
import edu.princeton.cs.introcs.In;
import java.util.Arrays;
import java.util.Set;
import java.util.HashSet;
import java.util.ArrayList;

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
            WordNet wn ;
            Plotter plotter = new Plotter();
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
                    ngm = new NGramMap("/Users/sastaputhra/HomeWork/cs61b_spring/skeleton/proj1/demos/ngrams/all_words.csv",
                                    "/Users/sastaputhra/HomeWork/cs61b_spring/skeleton/proj1/demos/ngrams/total_counts.csv");

                    plotter.plotAllWords(ngm, tokens, startDate, endDate);
                    break;
                case "hyponyms":
                    wn = new WordNet("/Users/sastaputhra/HomeWork/cs61b_spring/skeleton/proj1/demos/wordnet/synsets.txt",
                                           "/Users/sastaputhra/HomeWork/cs61b_spring/skeleton/proj1/demos/wordnet/hyponyms.txt");
                    Set<String> words = wn.hyponyms(tokens[0]);
                    System.out.println(Arrays.toString(words.toArray()));
                    break;
                case "hypohist":
                    wn = new WordNet("/Users/sastaputhra/HomeWork/cs61b_spring/skeleton/proj1/demos/wordnet/synsets.txt",
                                           "/Users/sastaputhra/HomeWork/cs61b_spring/skeleton/proj1/demos/wordnet/hyponyms.txt");
                    ngm = new NGramMap("/Users/sastaputhra/HomeWork/cs61b_spring/skeleton/proj1/demos/ngrams/all_words.csv",
                                    "/Users/sastaputhra/HomeWork/cs61b_spring/skeleton/proj1/demos/ngrams/total_counts.csv");
                    Set<String> wordnets = new HashSet<String>();
                    for(String token : tokens) {
                        wordnets.addAll(wn.hyponyms(token));
                    }
                    ArrayList<String> temp_words = new ArrayList<String>();
                    for(String w : wordnets) {
                        System.out.println(w);
                        temp_words.add(w);
                    }
                    String[] array_words = new String[temp_words.size()];
                    int i = 0;
                    plotter.plotAllWords(ngm, temp_words.toArray(array_words), startDate, endDate);

                    break;
                case "wordlength":
                    ngm = new NGramMap("/Users/sastaputhra/HomeWork/cs61b_spring/skeleton/proj1/demos/ngrams/all_words.csv",
                                    "/Users/sastaputhra/HomeWork/cs61b_spring/skeleton/proj1/demos/ngrams/total_counts.csv");
                    WordLengthProcessor yrp = new WordLengthProcessor();
                    plotter.plotProcessedHistory(ngm, startDate, endDate, yrp);
                    break;
                case "zipf":
                    ngm = new NGramMap("/Users/sastaputhra/HomeWork/cs61b_spring/skeleton/proj1/demos/ngrams/all_words.csv",
                                    "/Users/sastaputhra/HomeWork/cs61b_spring/skeleton/proj1/demos/ngrams/total_counts.csv");

                    plotter.plotZipfsLaw(ngm, Integer.parseInt(tokens[0]));

                    break;
                default:
                    System.out.println("Invalid command.");
                    break;
            }
        }


    }
}
