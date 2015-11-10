package synthesizer;
import org.junit.Test;
import static org.junit.Assert.*;

/** Tests the ArrayRingBuffer class.
 *  @author Josh Hug
 */

public class TestArrayRingBuffer {
    @Test
    public void someTest() {
        ArrayRingBuffer arb = new ArrayRingBuffer(10);
        //BoundedQueue arb = new NaiveArrayBoundedQueue(4);
        arb.enqueue(33.1); // 33.1    0    0    0
        arb.enqueue(44.8); // 33.1 44.8    0    0
        arb.enqueue(62.3); // 33.1 44.8 62.3    0
        arb.enqueue(-3.4); // 33.1 44.8 62.3 -3.4
        assertEquals(33.1,arb.dequeue(), 1e-6);// 44.8 62.3 -3.4    0 (returns 33.1)
        System.out.println(arb.capacity());

    }

    /** Calls tests for ArrayRingBuffer. */
    public static void main(String[] args) {
        jh61b.junit.textui.runClasses(TestArrayRingBuffer.class);
    }
}
