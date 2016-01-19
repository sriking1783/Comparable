package ngordnet;
import java.util.*;
import edu.princeton.cs.algs4.Digraph;
import java.io.*;

public class WordNet {
    Digraph g;
    HashMap<Integer,String> wordnet;
    Set<String> all_nouns;
    public WordNet(String sysnet_file, String hyponet_file){
        int vertices;
        try {
            wordnet = new HashMap<Integer,String>();
            g = new Digraph(countWords(sysnet_file));
            BufferedReader br1 = new BufferedReader(new FileReader(hyponet_file));

            for(String line; (line = br1.readLine()) != null; ) {
                  String[] retval = line.split(",");
                  int i = -1;
                  for(String ret: retval) {
                      i++;
                      if(i == 0)
                          continue;
                      g.addEdge(Integer.parseInt(retval[0]), Integer.parseInt(ret));
                  }
            }
        }
        catch(IOException iex){
            System.out.println(iex);
        }
        all_nouns = nouns();

    }

    public boolean isNoun(String noun) {
        return all_nouns.contains(noun);
    }

    public Set<String> nouns() {
        Set<String>noun_values = new HashSet<String>();
        for(String value :  wordnet.values()){
            String[] values = value.split(" ");
            Collections.addAll(noun_values, values);
        }
        return noun_values;
    }


    public Set<String> depth_first(int key) {
        Set<String> hypo_nyms = new HashSet<String>();
        for(int w : g.adj(key)) {
            String[] values = wordnet.get(w).split(" ");
            Collections.addAll(hypo_nyms, values);
            hypo_nyms.addAll(depth_first(w));
        }
        return hypo_nyms;
    }

    public Set<String> hyponyms(String noun) {
        List<Integer> keys = getKey(noun);
        Set<String> hypo_nyms = new HashSet<String>();
        for(int key : keys) {
            String[] values = wordnet.get(key).split(" ");
            Collections.addAll(hypo_nyms, values);
            hypo_nyms.addAll(depth_first(key));

        }

        return hypo_nyms;
    }

    private String[] convertList(List<String> string_list){
        String string_array[] = new String[string_list.size()];
        for(int i = 0; i < string_list.size(); i++) {
            string_array[i] = string_list.get(i);
        }

        return string_array;
    }

    private List<Integer> getKey(String value) {
        List<Integer> key = new ArrayList<Integer>();
        for(Map.Entry<Integer, String> entry : wordnet.entrySet()) {
            if((value == null && entry.getValue() == null) || (value != null && value.equals(entry.getValue())) || (value != null && Arrays.asList(entry.getValue().split(" ")).contains(value))) {
                key.add(entry.getKey());
            }
        }
        return key;
     }

    private int countWords(String filename) throws IOException {
        InputStream is = new BufferedInputStream(new FileInputStream(filename));
         try {
          byte[] c = new byte[1024];
          int count = 0;
          int readChars = 0;
          boolean empty = true;
          int i = 0;
          try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
              for(String line; (line = br.readLine()) != null; ) {
                  String[] items = line.split(",");
                  wordnet.put(Integer.parseInt(items[0]), items[1]);
                  count += items.length;
              }
          }
          return (count == 0 && !empty) ? 1 : count;
      } finally {
          is.close();
      }
    }
    private int countLines(String filename) throws IOException {
        InputStream is = new BufferedInputStream(new FileInputStream(filename));
         try {
          byte[] c = new byte[1024];
          int count = 0;
          int readChars = 0;
          boolean empty = true;
          while ((readChars = is.read(c)) != -1) {
              empty = false;
              for (int i = 0; i < readChars; ++i) {
                  if (c[i] == '\n') {
                      ++count;
                  }
              }
          }
          return (count == 0 && !empty) ? 1 : count;
      } finally {
          is.close();
      }
    }
}
