package ngordnet;
import java.util.HashMap;
import java.util.TreeMap;
import java.util.NavigableSet;
import java.util.Collection;
import java.util.TreeSet;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;
public class YearlyRecord {
    private TreeMap<String, Integer> wordstocount;
    private TreeMap<String, Integer> rankMap;
    private TreeMap<Number,Set<String>> countstowords;
    private int size;
    private boolean cached;

    public YearlyRecord(){
        wordstocount = new TreeMap<String, Integer>();
        countstowords = new TreeMap<Number, Set<String>>();
        rankMap = new TreeMap<String, Integer>();
        size = 0;
        cached = false;
    }

    public YearlyRecord(HashMap<String, Integer> otherMap) {
        wordstocount = new TreeMap<String, Integer>(otherMap);
        countstowords = new TreeMap<Number, Set<String>>();
        rankMap = new TreeMap<String, Integer>();
        size = 0;
        Number count;
        for(String word : wordstocount.keySet()) {
            count = wordstocount.get(word);
            if(countstowords.containsKey(count)) {
                countstowords.get(count).add(word);
            }
            else {
                Set<String> temp = new HashSet<String>();
                temp.add(word);
                countstowords.put(count, temp);
            }
            size = otherMap.size();
            cached = false;
        }

    }

    public int count(String word) {
        return wordstocount.get(word);
    }

    public void put(String word, int count) {
        wordstocount.put(word, count);
        if(countstowords.containsKey(count)) {
            countstowords.get(count).add(word);
        }
        else{
            Set<String> temp = new HashSet<String>();
            temp.add(word);
            countstowords.put(count, temp);
        }
        size += 1;
    }

    public Collection<String> words() {
        return wordstocount.keySet();
    }

    public Collection<Number> counts() {
        return countstowords.keySet();
    }

    public int size() {
        return size;
    }

    public int rank(String word) {
        if(!cached) {
            int i = size();
            for(Set<String> wordset : countstowords.values()) {
                for(String w : wordset) {
                    rankMap.put(w, i--);
                }
                cached = true;
            }
        }
        return(Integer) rankMap.get(word);

    }

}
