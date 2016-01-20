package ngordnet;

import java.util.Map;
import java.util.TreeMap;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.ArrayList;
import java.io.*;
import java.util.Arrays;
import java.util.Collection;
public class NGramMap {

    private TreeMap<String, TimeSeries<Integer>> word_stats;
    private TimeSeries<Long> total_words;
    public NGramMap(String words_file, String total_counts_file) {
        word_stats = new TreeMap<String, TimeSeries<Integer>>();
        total_words = new TimeSeries<Long>();
        read_words_file(words_file);
        read_total_counts_file(total_counts_file);
    }

    public Integer countInYear(String word, int year) {
        if(word_stats.get(word) == null || word_stats.get(word).get(year) == null) {
            return 0;
        }
        return word_stats.get(word).get(year);
    }

    public YearlyRecord getRecord(int year) {
        HashMap<String, Integer> yearly_stats = new HashMap<String, Integer>();
        for(Map.Entry<String, TimeSeries<Integer>> entry : word_stats.entrySet()) {
            if(entry.getValue().containsKey(year))
                yearly_stats.put(entry.getKey(), entry.getValue().get(year));
        }
        return new YearlyRecord(yearly_stats);
    }

    public TimeSeries<Double> processedHistory(int startYear, int endYear, YearlyRecordProcessor yrp) {
        TimeSeries<Double> ts = new TimeSeries<Double>();
        for(int year = startYear; year <= endYear; year++) {
            double temp = yrp.process(getRecord(startYear));
            ts.put(year, temp);
        }
        return ts;

    }

    public TimeSeries<Integer> countHistory(String word, Integer... years) {
        TimeSeries<Integer> ts = new TimeSeries<Integer>();
        if(years.length == 0) {
            return word_stats.get(word);
        }
        else {
            for(int year = years[0]; year <= years[1]; year++) {
                if(countInYear(word, year) != 0) {
                    ts.put(year, countInYear(word, year));
                }
            }
        }
        return ts;
    }

    public TimeSeries<Long> totalCountHistory() {
        return total_words;
    }

    public TimeSeries<Double> weightHistory(String word, Integer... years) {
        TimeSeries<Double> ts = new TimeSeries<Double>();
        if(years.length == 0) {
            return countHistory(word).dividedBy(totalCountHistory());
        }
        else {
            return countHistory(word, years[0], years[1]).dividedBy(totalCountHistory());
        }
    }

    public TimeSeries<Double> summedWeightHistory(ArrayList<String> words, Integer... years) {
        TimeSeries<Double> ts = new TimeSeries<Double>();
        for(String word : words) {
            if(word == null)
                continue;
            TimeSeries<Double> temp = new TimeSeries<Double>();
            temp = weightHistory(word);
            for(Map.Entry<Integer, Double> entry : temp.entrySet()) {
                if(years.length == 0) {
                    if(ts.containsKey(entry.getKey())) {
                        ts.put(entry.getKey(), ts.get(entry.getKey())+ entry.getValue());
                    }
                    else {
                        ts.put(entry.getKey(), entry.getValue());
                    }
                }
                else if((years[0] <= entry.getKey()) && (years[1] >= entry.getKey())){
                    if(ts.containsKey(entry.getKey())) {
                        ts.put(entry.getKey(), ts.get(entry.getKey())+ entry.getValue());
                    }
                    else {
                        ts.put(entry.getKey(), entry.getValue());
                    }
                }
            }
        }
        return ts;
    }

    private void read_words_file(String words_file) {

        try {
            BufferedReader br = new BufferedReader(new FileReader(words_file));
            for(String line; (line = br.readLine()) != null; ) {
                String[] retval = line.split("\t");
                int year = Integer.parseInt(retval[1]);
                int count = Integer.parseInt(retval[2]);
                TimeSeries<Integer> temp = new TimeSeries<Integer>();
                if(word_stats.containsKey(retval[0])) {
                    word_stats.get(retval[0]).put(year, count);
                }
                else {
                    temp.clear();
                    temp.put(year, year);
                    word_stats.put(retval[0], temp);
                }
            }
        }
        catch(IOException iex){
            System.out.println(iex);
        }
    }

    private void read_total_counts_file(String total_counts_file) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(total_counts_file));

            for(String line; (line = br.readLine()) != null; ) {
                String[] retval = line.split(",");
                int year = Integer.parseInt(retval[0]);
                long total_count = Long.parseLong(retval[1]);
                total_words.put(year, total_count);
            }
        }
        catch(IOException iex){
            System.out.println(iex);
        }

    }
}
