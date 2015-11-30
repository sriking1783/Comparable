/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comparablelist;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author sastaputhra
 */
public class SortedComparableListTest {
    
    public SortedComparableListTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of insert method, of class SortedComparableList.
     */
    @Test
    public void testInsert() {
        System.out.println("insert");
        SortedComparableList instance = new SortedComparableList();
        Comparable c = 2;
        
        instance.insert(c);
        // TODO review the generated test code and remove the default call to fail.
        assertEquals(0, instance.get(0));
        
        Comparable c1 = -1;
        
        instance.insert(c1);
        
        assertEquals(-1, instance.get(0));
        
        Comparable c2 = 1;
        
        instance.insert(c2);
        
        assertEquals(1, instance.get(2));
    }

    /**
     * Test of get method, of class SortedComparableList.
     */
    @Test
    public void testGet() {
        System.out.println("get");
        int i = 1;
        SortedComparableList instance = new SortedComparableList();
        Comparable expResult = 2;
        instance.insert(expResult);
        Comparable result = instance.get(i);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
    }

    /**
     * Test of extend method, of class SortedComparableList.
     */
    @Test
    public void testExtend1() {
        System.out.println("extend2");

        SortedComparableList temp2 = new SortedComparableList(2,null);
        
        SortedComparableList that = new SortedComparableList(0, null);
        
        SortedComparableList temp9 = new SortedComparableList(9,null);
        SortedComparableList temp7 = new SortedComparableList(7,temp9);
        SortedComparableList temp5 = new SortedComparableList(5,temp7);
        SortedComparableList temp3 = new SortedComparableList(3,temp5);
        
        
        SortedComparableList instance = new SortedComparableList(1, temp3);
        
        instance.extend(that);
        Comparable result = instance.get(0);
         assertEquals(0, result);
        // TODO review the generated test code and remove the default call to fail.
    }
    
    @Test
    public void testExtend2() {
        System.out.println("extend2");
        SortedComparableList temp8 = new SortedComparableList(8,null);
        SortedComparableList temp6 = new SortedComparableList(6,temp8);
        SortedComparableList temp4 = new SortedComparableList(4,temp6);
        SortedComparableList temp2 = new SortedComparableList(2,temp4);
        
        SortedComparableList that = new SortedComparableList(0, temp2);
        
        SortedComparableList temp9 = new SortedComparableList(9,null);
        SortedComparableList temp7 = new SortedComparableList(7,temp9);
        SortedComparableList temp5 = new SortedComparableList(5,temp7);
        SortedComparableList temp3 = new SortedComparableList(3,temp5);
        
        
        SortedComparableList instance = new SortedComparableList(1, temp3);
        
        instance.extend(that);
        Comparable result = instance.get(7);
         assertEquals(7, result);
        // TODO review the generated test code and remove the default call to fail.
    }

    /**
     * Test of subTail method, of class SortedComparableList.
     */
    @Test
    public void testSubTail() {
        System.out.println("subTail");
        SortedComparableList L = null;
        int start = 0;
        SortedComparableList expResult = null;
        SortedComparableList result = SortedComparableList.subTail(L, start);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of sublist method, of class SortedComparableList.
     */
    @Test
    public void testSublist() {
        System.out.println("sublist");
        SortedComparableList L = null;
        int start = 0;
        int len = 0;
        SortedComparableList expResult = null;
        SortedComparableList result = SortedComparableList.sublist(L, start, len);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of expungeTail method, of class SortedComparableList.
     */
    @Test
    public void testExpungeTail() {
        System.out.println("expungeTail");
        SortedComparableList L = null;
        int len = 0;
        SortedComparableList.expungeTail(L, len);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of squish method, of class SortedComparableList.
     */
    @Test
    public void testSquish() {
        System.out.println("squish");
        SortedComparableList instance = new SortedComparableList();
        instance.squish();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of twin method, of class SortedComparableList.
     */
    @Test
    public void testTwin() {
        System.out.println("twin");
        SortedComparableList instance = new SortedComparableList();
        instance.twin();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of equals method, of class SortedComparableList.
     */
    @Test
    public void testEquals() {
        System.out.println("equals");
        Object x = null;
        SortedComparableList instance = new SortedComparableList();
        boolean expResult = false;
        boolean result = instance.equals(x);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of toString method, of class SortedComparableList.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        SortedComparableList instance = new SortedComparableList();
        String expResult = "";
        String result = instance.toString();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
