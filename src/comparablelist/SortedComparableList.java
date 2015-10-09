/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comparablelist;

import java.util.Formatter;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author sastaputhra
 */
public class SortedComparableList {
      /** First element of list. */
    public Comparable head;
    /** Remaining elements of list. */
    public SortedComparableList tail;

    /** A list with head HEAD0 and tail TAIL0. */
    public SortedComparableList(Comparable head0, SortedComparableList tail0) {
        // REPLACE THIS LINE WITH YOUR SOLUTION
        this.head = head0;
        this.tail = tail0;
    }

    /** A list with null tail, and head = 0. */
    public SortedComparableList(){
        // REPLACE THIS LINE WITH YOUR SOLUTION
        this(0, null);
    }

    /** Inserts Comparable c into its correct location in this list. */
    public void insert(Comparable c) {
        // REPLACE THIS LINE WITH YOUR SOLUTION
        Comparable temp = head;
        SortedComparableList current = this;
        SortedComparableList tempList = tail;
        if(tempList == null){
            tail = new SortedComparableList(c, null);
        }
        else if(temp == null){
            new SortedComparableList(c, null);
        }
        else{
            
            while((c.compareTo(temp) > 0) &&(c.compareTo(tempList.head) > 0) &&(tempList.tail != null)){
                temp = tempList.head;
                current = tempList;
                tempList = tempList.tail;

            }
            
            if((c.compareTo(temp) > 0) &&(c.compareTo(tempList.head) <= 0)){
                System.out.println("1");
                SortedComparableList temporary  = new SortedComparableList();
                temporary.tail = tempList;
                temporary.head = c;
                current.tail = temporary;
            }
            
            else if(temp.compareTo(head) == 0){
                System.out.println("3");
                this.tail = new SortedComparableList(this.head, this.tail);
                this.head = c;
                
            }
            
        }

    }

    /** Returns the i-th int in this list.
     *  The first element, which is in location 0, is the 0th element.
     *  Assume i takes on the values [0, length of list - 1]. */
    public Comparable get(int i) {
        Comparable temp = head;
        SortedComparableList tempList = tail;
        
        while((i > 0)&&(tempList != null)){
            
            temp = tempList.head;
            tempList = tempList.tail;
            i--;
        }
        return temp; // REPLACE THIS LINE WITH YOUR SOLUTION
    }

    /** Adds every item in THAT to this list. */
    public void extend(SortedComparableList that) {
      Comparable temp = head;
      SortedComparableList tempList = tail;
      SortedComparableList tempThatList = that;
      while(tempThatList != null) {
          insert(tempThatList.head);
          tempThatList = tempThatList.tail;
      }

    }

    /** Returns a list consisting of the elements of L starting from
      * position START, and going all the way to the END. The head of the
      * list L is the 0th element of the list.
      *
      * This method should NOT modify L. */
    public static SortedComparableList subTail(SortedComparableList L, int start) {
        return null; // REPLACE THIS LINE WITH YOUR SOLUTION
    }

    /** Returns the sublist consisting of LEN items from list L,
     *  beginning with item START (where the first item is 0).
     *
     *  Does not modify the original list elements.
     *  Assume START and END are >= 0.
     */
    public static SortedComparableList sublist(SortedComparableList L, int start, int len) {
        return null; // REPLACE THIS LINE WITH YOUR SOLUTION
    }

    /** Removes items from L at position len+1 and later. */
    public static void expungeTail(SortedComparableList L, int len) {
        // REPLACE THIS LINE WITH YOUR SOLUTION
    }

    /**
     *  Takes this list and, wherever two or more consecutive items are
     *  equal, it removes duplicates so that only one consecutive copy
     *  remains. No two consecutive items in this list are equals at the
     *  end of this method.
     *
     *  You can assume the list is in sorted order when this method is
     *  called.
     *
     *  For example, if the input list is [ 0 0 0 0 1 1 3 3 3 4 ], the
     *  output list is [ 0 1 3 4 ].
     **/
    public void squish() {
        // REPLACE THIS LINE WITH YOUR SOLUTION
    }

    /** Duplicates each Comparable so that for every original
     *  Comparable, there will end up being two consecutive Comparables.
     *
     *  You can assume the list is in sorted order when this method is
     *  called.
     *
     *  For example, if the input list is [ 2 3 4 7 ], the
     *  output list is [ 2 2 3 3 4 4 7 7 ].
     *
     *  NOTE: Do not try to make copies of the Comparables. Set
     *  the HEAD variable equal to the HEAD variable you are trying to
     *  duplicate.
     **/
    public void twin() {
        // REPLACE THIS LINE WITH YOUR SOLUTION
    }

    /** Returns NULL if no cycle exists, else returns cycle location. */
    private int detectCycles(SortedComparableList A) {
        SortedComparableList tortoise = A;
        SortedComparableList hare = A;
        if (A == null) {
            return 0;
        }
        int cnt = 0;
        while (true) {
            cnt++;
            if (hare.tail != null) {
                hare = hare.tail.tail;
            }
            else{
                return 0;
            }
            tortoise = tortoise.tail;
            if (tortoise == null || hare == null) {
                return 0;
            }
            if (hare == tortoise) {
                return cnt;
            }
        }
    }

    /** Returns true iff X is a SortedComparableList containing the
     *  same sequence of Comparables as THIS. Cannot handle cycles. */
    public boolean equals(Object x) {
        if (!(x instanceof SortedComparableList)) {
            return false;
        }
        SortedComparableList L = (SortedComparableList) x;
        SortedComparableList p;
        for (p = this; p != null && L != null; p = p.tail, L = L.tail) {
            if (p.head != L.head) {
                return false;
            }
        }
        if (p != null || L != null) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        Formatter out = new Formatter();
        String sep;
        sep = "(";
        int cycleLocation = detectCycles(this);
        int cnt = 0;
        for (SortedComparableList p = this; p != null; p = p.tail) {
            out.format("%s%d", sep, p.head);
            sep = ", ";

            cnt++;
            if ((cnt > cycleLocation) && (cycleLocation > 0)) {
                out.format("... (cycle exists) ...");
                break;
            }
        }
        out.format(")");
        return out.toString();
    }
    
}
