// Make sure to make this class a part of the synthesizer package
package synthesizer;
import java.util.Arrays;
public class ArrayRingBuffer extends AbstractBoundedQueue {
  /* Index for the next dequeue or peek. */
  private int first;
  /* Index for the next enqueue. */
  private int last;
  /* Array for storing the buffer data. */
  private double[] rb;

  /** Create a new ArrayRingBuffer with the given capacity. */
  public ArrayRingBuffer(int capacity) {
    // TODO: Create new array with capacity elements.
    //       first, last, and fillCount should all be set to 0.
    //       this.capacity should be set appropriately. Note that the local variable
    //       here shadows the field we inherit from AbstractBoundedQueue.
        this.first = 0;
        this.last = 0;
        this.fillCount = 0;
        this.capacity = capacity;
        rb = new double[capacity];
  }

  /** Adds x to the end of the ring buffer. If there is no room, then
    * throw new RuntimeException("Ring buffer overflow")
    */
  public void enqueue(double x) {
    // TODO: Enqueue the item. Don't forget to increase fillCount and update last.
    // is there room?
      if(this.isFull()){
          throw new RuntimeException("Ring buffer overflow");
      }

      rb[last] = x;

      if(fillCount == 0) {
          first = fillCount;
      }
      last ++;
      if(last == capacity){
        last = 0;
      }


      fillCount++;
      //System.out.println(Arrays.toString(rb));
      //System.out.println(x+", "+fillCount+" enqueued");
  }

  /** Dequeue oldest item in the ring buffer. If the buffer is empty, then
    * throw new RuntimeException("Ring buffer underflow");
    */
  public double dequeue() {
    // TODO: Dequeue the first item. Don't forget to decrease fillCount and update first.
      if(this.isEmpty()){
          throw new RuntimeException("Ring buffer underflow");
      }
      double x = rb[first];
      for(int i = first; i< fillCount-1; i++){
          rb[i] = rb[i + 1];
      }
      //rb[first] = 0;
      first++;
      if(first == capacity){
        first = 0;
      }

      fillCount--;

      return x;
  }

  /** Return oldest item, but don't remove it. */
  public double peek() {
    // TODO: Return the first item. None of your instance variables should change.
      if(this.isEmpty()){
          throw new RuntimeException("Ring buffer underflow");
      }
      return rb[first];
  }

}
