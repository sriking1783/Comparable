package ngordnet;
import java.util.*;
import edu.princeton.cs.algs4.Digraph;
import java.io.*;

public class WordNet {
    Digraph g;
    HashMap<Integer,String> wordnet;
  public WordNet(String sysnet_file, String hyponet_file){
      int vertices;
      try {
          g = new Digraph(countWords(sysnet_file));
          wordnet = new HashMap<Integer,String>();
          BufferedReader br = new BufferedReader(new FileReader(sysnet_file));

          for(String line; (line = br.readLine()) != null; ) {
              String[] retval = line.split(",");
              wordnet.put(Integer.parseInt(retval[0]), retval[1]);
          }
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

  }

  public boolean isNoun(String noun) {
      for(String value : wordnet.values()){
          String[] values = value.split(" ");
          if(Arrays.asList(values).contains(noun))
              return true;
      }
      return false;
  }

  public String[] nouns() {
      List<String> noun_values = new ArrayList<String>();
      for(String value :  wordnet.values()){
          String[] values = value.split(" ");
          for(String noun : values){
              if(!noun_values.contains(noun))
                  noun_values.add(noun);
          }
      }
      return convertList(noun_values);
  }

  private List<String> get_hyponyms(String noun){
      List<Integer> keys= getKey(noun);
      List<String> hypo_nyms = new ArrayList<String>();
      for(int key : keys){
          Iterable<Integer> adjacents = g.adj(key);
          for(Iterator<Integer> i = adjacents.iterator(); i.hasNext(); ) {
              int item = i.next();
              String[] values = wordnet.get(item).split(" ");
              for(String value : values){
                  if(!hypo_nyms.contains(value)){
                      hypo_nyms.add(value);
                      hypo_nyms.addAll(get_hyponyms(value));
                  }
              }
          }
      }
      return hypo_nyms;
  }

  public String[] hyponyms(String noun) {
      List<Integer> keys = getKey(noun);
      List<String> hypo_nyms = new ArrayList<String>();
      for(int key : keys) {
          Iterable<Integer> adjacents = g.adj(key);
          hypo_nyms = get_hyponyms(noun);

          String[] values = wordnet.get(key).split(" ");
          for(String value : values){
              hypo_nyms.add(value);
           }

          for(Map.Entry<Integer, String> entry : wordnet.entrySet()) {
              String[] entries = entry.getValue().split(" ");
              if(noun != null && Arrays.asList(entries).contains(noun)){
                  for(String value : entries){
                      if(!hypo_nyms.contains(value))
                          hypo_nyms.add(value);
                  }
              }
          }
      }

      return convertList(hypo_nyms);
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
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            for(String line; (line = br.readLine()) != null; ) {
                String[] items = line.split(",");
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
