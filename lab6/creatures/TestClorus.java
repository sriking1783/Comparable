
package creatures;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.HashMap;
import java.awt.Color;
import huglife.Direction;
import huglife.Action;
import huglife.Occupant;
import huglife.Impassible;
import huglife.Empty;

/** Tests the clorus class
 *  @authr FIXME
 */

public class TestClorus {

    @Test
    public void testChoose1() {
        Clorus c = new Clorus(2.0);
        HashMap<Direction, Occupant> surrounded = new HashMap<Direction, Occupant>();
        surrounded.put(Direction.TOP, new Impassible());
        surrounded.put(Direction.BOTTOM, new Impassible());
        surrounded.put(Direction.LEFT, new Impassible());
        surrounded.put(Direction.RIGHT, new Impassible());

      //You can create new empties with new Empty();
      //Despite what the spec says, you cannot test for Cloruses nearby yet.
      //Sorry!

        Action actual = c.chooseAction(surrounded);
        Action expected = new Action(Action.ActionType.STAY);

        assertEquals(expected, actual);
  }

  @Test
  public void testChoose2() {
      Clorus c = new Clorus(2.0);
      Plip p = new Plip(1.0);
      HashMap<Direction, Occupant> surrounded = new HashMap<Direction, Occupant>();
      surrounded.put(Direction.TOP, new Empty());
      surrounded.put(Direction.BOTTOM, p);
      surrounded.put(Direction.LEFT, new Empty());
      surrounded.put(Direction.RIGHT, new Empty());
      //You can create new empties with new Empty();
      //Despite what the spec says, you cannot test for Cloruses nearby yet.
      //Sorry!

      Action actual = c.chooseAction(surrounded);
      Action expected = new Action(Action.ActionType.ATTACK, Direction.BOTTOM);

      assertEquals(expected, actual);
}

  @Test
  public void testChoose3() {
    Clorus c = new Clorus(2.0);
    Plip p = new Plip(1.0);
    HashMap<Direction, Occupant> surrounded = new HashMap<Direction, Occupant>();
    surrounded.put(Direction.TOP, new Empty());
    surrounded.put(Direction.BOTTOM, new Empty());
    surrounded.put(Direction.LEFT, new Empty());
    surrounded.put(Direction.RIGHT, new Empty());


    Action actual = c.chooseAction(surrounded);
    Action expected = new Action(Action.ActionType.REPLICATE, Direction.TOP);
    assertEquals(actual.toString().substring(0, 9), expected.toString().substring(0, 9));
    //assertThat( (new Action(Action.ActionType.REPLICATE, Direction.BOTTOM)).class, Action.class);
  }

  @Test
  public void testChoose4() {
    Clorus c = new Clorus(0.9);
    HashMap<Direction, Occupant> surrounded = new HashMap<Direction, Occupant>();
    surrounded.put(Direction.TOP, new Empty());
    surrounded.put(Direction.BOTTOM, new Empty());
    surrounded.put(Direction.LEFT, new Empty());
    surrounded.put(Direction.RIGHT, new Empty());


    Action actual = c.chooseAction(surrounded);
    Action expected = new Action(Action.ActionType.STAY);
    assertEquals(actual.toString().substring(0, 4), expected.toString().substring(0, 4));
    //assertThat( (new Action(Action.ActionType.REPLICATE, Direction.BOTTOM)).class, Action.class);
  }


    public static void main(String[] args) {
        System.exit(jh61b.junit.textui.runClasses(TestClorus.class));
    }
}
