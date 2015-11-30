import java.util.Set; /* java.util.Set needed only for challenge problem. */
import java.util.Iterator ;
/** A data structure that uses a linked list to store pairs of keys and values.
 *  Any key must appear at most once in the dictionary, but values may appear multiple
 *  times. Supports get(key), put(key, value), and contains(key) methods. The value
 *  associated to a key is the value in the last call to put with that key.
 *
 *  For simplicity, you may assume that nobody ever inserts a null key or value
 *  into your map.
 */
public class ULLMap<Key, Value> implements Iterable<Key> { //FIX ME
    /** Keys and values are stored in a linked list of Entry objects.
      * This variable stores the first pair in this linked list. You may
      * point this at a sentinel node, or use it as a the actual front item
      * of the linked list.
      */
    private Entry front;
    private int N;

    public Iterator<Key> iterator() {
        return new ULLMapIter();
    }

    public static <Key, Value> ULLMap<Value, Key> invertULLMap(ULLMap<Key, Value> map){
        ULLMap<Value, Key> inverted_um = new ULLMap<Value, Key>();
        inverted_um.put(map.get(map.front.key), map.front.key);
        Iterator<Key> umi = map.iterator();
        while(umi.hasNext()){
            Key k = umi.next();
            System.out.println(k+", "+map.get(k));
            inverted_um.put(map.get(k), k);
        }
        return inverted_um;
    }

    //@Override
    public Value get(Key k) { //FIX ME
    //FILL ME IN
          if(front == null){
              return null;
          }
          Entry temp = front;

          while(temp != null){
              if(temp.key.equals(k)){
                  return temp.val;
              }
              temp = temp.next;
          }
        return null; //FIX ME
    }

    //@Override
    public void put(Key key, Value val) { //FIX ME

        if(front == null){
            front = new Entry(key, val, null);
            N++;
            return;
        }
        Entry temp = front;

        while(temp.next != null){
            temp = temp.next;
        }
        temp.next = new Entry(key, val, null);
        N++;
    }

    //@Override
    public boolean containsKey(Key k) { //FIX ME
    //FILL ME IN
        Entry temp = front;
        while(temp != null) {
            if(temp.key.equals(k)) {
                return true;
            }
            temp = temp.next;
        }
        return false; //FIX ME
    }

    //@Override
    public int size() {
        return N; // FIX ME (you can add extra instance variables if you want)
    }

    //@Override
    public void clear() {
        front = null;
    //FILL ME IN
    }

    private class ULLMapIter implements Iterator<Key> {

       Key um;
       Entry um_entry;
      public ULLMapIter(){
          um = front.key;
          um_entry = front;
      }

      public boolean hasNext(){
          return um_entry.next != null;
      }

      public Key next(){
          if(um == null)  throw new UnsupportedOperationException();

          Key i = um_entry.next.key;
          um_entry = um_entry.next;
          return i;
      }

      public void remove(){
          throw new UnsupportedOperationException();
      }

    }


    /** Represents one node in the linked list that stores the key-value pairs
     *  in the dictionary. */
    private class Entry{

        /** Stores KEY as the key in this key-value pair, VAL as the value, and
         *  NEXT as the next node in the linked list. */
        public Entry(Key k, Value v, Entry n) { //FIX ME
            key = k;
            val = v;
            next = n;
        }

        /** Returns the Entry in this linked list of key-value pairs whose key
         *  is equal to KEY, or null if no such Entry exists. */
        public Entry get(Key k) { //FIX ME
            //FILL ME IN (using equals, not ==)
            Entry temp = this;
            if(key.equals(k)){
                return temp;
            }
            return null; //FIX ME
        }

        /** Stores the key of the key-value pair of this node in the list. */
        private Key key; //FIX ME
        /** Stores the value of the key-value pair of this node in the list. */
        private Value val; //FIX ME
        /** Stores the next Entry in the linked list. */
        private Entry next;

    }

    /* Methods below are all challenge problems. Will not be graded in any way.
     * Autograder will not test these. */
    //@Override
    public void remove(Key key) { //FIX ME SO I COMPILE
        /* if(key == null) throw new UnsupportedOperationException();
        Entry temp = front;
        while(temp != null) {
            if(key.equals(k)){
                break;
            }
            temp = temp.next;
        }
        temp
        */
        throw new UnsupportedOperationException();
    }

    //@Override
    public void remove(Key key, Value value) { //FIX ME SO I COMPILE
        throw new UnsupportedOperationException();
    }

    //@Override
    public Set<Key> keySet() { //FIX ME SO I COMPILE
        throw new UnsupportedOperationException();
    }


}
