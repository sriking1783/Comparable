package ngordnet;

import java.util.Map;
import java.util.TreeMap;
import java.util.HashMap;
import java.util.ArrayList;
import java.io.*;
import java.util.Arrays;
public class NGramMap {

    private TreeMap<String, TreeMap<Integer, Integer>> word_stats;
    private TreeMap<Integer, Long> total_words;
    public NGramMap(String words_file, String total_counts_file) {
        word_stats = new TreeMap<String, TreeMap<Integer, Integer>>();
        total_words = new TreeMap<Integer, Long>();
        read_words_file(words_file);
        read_total_counts_file(total_counts_file);
    }

    public Integer countInYear(String word, int year) {
        return word_stats.get(word).get(year);
    }

    public YearlyRecord getRecord(int year) {
        HashMap<String, Integer> yearly_stats = new HashMap<String, Integer>();
        for(Map.Entry<String, TreeMap<Integer, Integer>> entry : word_stats.entrySet()) {
            TreeMap<Integer, Integer> yearly_counts = entry.getValue();
            for(Map.Entry<Integer, Integer> entry1 : yearly_counts.entrySet()) {
                if(entry1.getKey() == year) {
                    yearly_stats.put(entry.getKey(), entry1.getValue());
                }
            }
        }
        return new YearlyRecord(yearly_stats);
    }

    public TimeSeries<Integer> countHistory(String word) {
        TimeSeries<Integer> ts = new TimeSeries<Integer>();
        for(Map.Entry<String, TreeMap<Integer, Integer>> entry : word_stats.entrySet()) {
            TreeMap<Integer, Integer> yearly_counts = entry.getValue();
            if(entry.getKey().equals(word)) {
                for(Map.Entry<Integer, Integer> entry1 : yearly_counts.entrySet()) {
                    ts.put(entry1.getKey(), entry1.getValue());
                }
            }
        }
        return ts;
    }

    public TimeSeries<Long> totalCountHistory() {
        TimeSeries<Long> ts = new TimeSeries<Long>();
        for(Map.Entry<Integer, Long> entry : total_words.entrySet()) {
           ts.put(entry.getKey(), entry.getValue());
        }

        return ts;
    }

    public TimeSeries<Double> weightHistory(String word) {
        TimeSeries<Double> ts = new TimeSeries<Double>();
        for(Map.Entry<String, TreeMap<Integer, Integer>> entry : word_stats.entrySet()) {
            TreeMap<Integer, Integer> yearly_counts = entry.getValue();
            if(entry.getKey().equals(word)) {
                for(Map.Entry<Integer, Integer> entry1 : yearly_counts.entrySet()) {
                    double data = total_words.get(entry1.getKey());
                    ts.put(entry1.getKey(), entry1.getValue()/data);
                }
            }
        }
        return ts;
    }

    public TimeSeries<Double> summedWeightHistory(ArrayList<String> words) {
        return null;
    }

    private void read_words_file(String words_file) {

        try {
            BufferedReader br = new BufferedReader(new FileReader(words_file));
            for(String line; (line = br.readLine()) != null; ) {
                String[] retval = line.split("\t");
                int year = Integer.parseInt(retval[1]);
                int count = Integer.parseInt(retval[2]);
                if(word_stats.containsKey(retval[0])) {
                    word_stats.get(retval[0]).put(year, count);
                }
                else {
                    TreeMap<Integer, Integer> temp_year_counts = new TreeMap<Integer, Integer>();
                    temp_year_counts.put(year, year);
                    word_stats.put(retval[0], temp_year_counts);
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
