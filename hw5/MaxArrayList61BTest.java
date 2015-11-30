import org.junit.Test;
import static org.junit.Assert.*;
import java.util.List;


public class MaxArrayList61BTest{

	@Test
    public void testBasic(){
    	List<Integer> L = new MaxArrayList61B<Integer>();
    	L.add(5);
        L.add(3);
        assertTrue(L.contains(5));        
        assertFalse(L.contains(3));
    }

	/** Runs tests. */
    public static void main(String[] args) {
        jh61b.junit.textui.runClasses(MaxArrayList61BTest.class);
    }
}