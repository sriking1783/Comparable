package ngordnet;
import java.util.Collection;

public class WordLengthProcessor implements YearlyRecordProcessor {
    public double process(YearlyRecord yearlyRecord) {
        Collection<String> words = yearlyRecord.words();
        int number_of_times = 0;
        int total_appearances = 0;
        for (String word : words) {
            number_of_times += word.length() * yearlyRecord.count(word) ;
            total_appearances += yearlyRecord.count(word);
        }

        return (float) number_of_times/total_appearances;
    }
}
