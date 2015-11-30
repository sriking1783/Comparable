import org.junit.Test;
import static org.junit.Assert.*;
import java.util.List; // Class to hold a list of objects
import java.util.Scanner; // Class to read in keyboard etc entry

import java.util.Iterator;

/** ULLMapTest. You should write additional tests.
 *  @author Josh Hug
 */

public class ULLMapTest  {
    @Test
    public void testBasic() {
        ULLMap<String, String> um = new ULLMap<String, String>();
        um.put("Gracias", "Dios Basado");
        assertEquals(um.get("Gracias"), "Dios Basado");
    }

    @Test
    public void testContainsKey() {
        ULLMap<Integer, String> um = new ULLMap<Integer, String>();
        um.put(0, "zero");
        um.put(1, "one");
        um.put(2, "two");
        assertEquals(um.containsKey(1), true);
        assertEquals(um.containsKey(3), false);
    }

    @Test
    public void testSize() {
        ULLMap<Integer, String> um = new ULLMap<Integer, String>();
        um.put(0, "zero");
        um.put(1, "one");
        um.put(2, "two");
        assertEquals(um.size(), 3);
    }

    @Test
    public void testIterator() {
        ULLMap<Integer, String> um = new ULLMap<Integer, String>();
        um.put(0, "zero");
        um.put(1, "one");
        um.put(2, "two");
        Iterator<Integer> umi = um.iterator();
        System.out.println(umi.next());
        System.out.println(umi.next());
        assertEquals(umi.hasNext(), false);
    }

    @Test
    public void testinvertULLMap(){
        ULLMap<Integer, String> um = new ULLMap<Integer, String>();
        um.put(0, "zero");
        um.put(1, "one");
        um.put(2, "two");
        ULLMap<String, Integer> inverted_um=  ULLMap.invertULLMap(um);
        Iterator<String> umi = inverted_um.iterator();
        System.out.println(umi.next());
        System.out.println(umi.next());
        assertEquals(inverted_um.containsKey("Zero"), true);
    }


    /** Runs tests. */
    public static void main(String[] args) {
        jh61b.junit.textui.runClasses(ULLMapTest.class);
    }
}
