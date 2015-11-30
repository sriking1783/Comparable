
public class GenericList<Blolbla> {

    public class Node {
        public Node(Blolbla value){
            this(value, null);
        }

       public Node(Blolbla value,Node next){
            this.value = value;
            this.next = next;
        }
        /** Returns the element at index i starting at this node in
         *  the linked list. */
        public Blolbla get(int i) {
            if (i == 0) return value;
            if (next == null) {
                throw new IllegalArgumentException("Index out of bounds");
            } else {
                return next.get(i - 1);
            }
        }

        /** The value stored in this node */
        Blolbla value;

        /** The next node in the list */
        Node next;
    }

    @Override
    public String toString() {
        String rep  = "[" ;
        Node cur = head;

        while(cur != null && cur.next != null) {
            rep += cur.value + ", ";
            cur = cur.next;
        }
        if (cur != null){
            rep += cur.value;
        }
        rep += "]" ;
        return rep;
    }

    public int length() {
        return length;
    }

    public Blolbla get(int i) {
        if(head == null) {
            throw new IllegalArgumentException("Index out of bounds");
        }
        return head.get(i);
    }

    public void insert(Blolbla val) {
        head = new Node(val, head);
        length += 1;
    }

    private Node head;
    private int length;

}

