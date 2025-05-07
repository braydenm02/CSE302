import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import java.io.FileNotFoundException;


/**
 * The TurtleTest is used to test the methods the Turtle Parser.
 * 
 * @implNote NOTES TO THE GRADER:
 *      
 *          The libraries here are from VSCode's JUnit library, so it
 *          may be difficult to port over to any IDE that you are using
 *          but an IDE was not used for this testing. The junits are
 *          added to classpath.
 * 
 * @author Brayden Miranda, John Belak
 */
public class TurtleTest {
   
    /**
     * Test that the number's being parsed are correct. This means that whenever
     * the parser is given a number it will reach the correct answer by parsing
     * it into an integer.
     */
    @Test
    public void testNumberValid() {
        TurtleParser parser = new TurtleParser("dummy.txt");
        assertEquals(123, parser.number("123"));
        assertEquals(0, parser.number("0"));
    }

    /**
     * Tests that whenever the parser is given a string of letters to attempt to
     * parse as a number, it will throw an illegal argument exception and say that
     * the provided value is not a valid argument.
     */
    @Test
    public void testNumberInvalid() {
        TurtleParser parser = new TurtleParser("dummy.txt");
        assertThrows(IllegalArgumentException.class, () -> {
            parser.number("abc");
        });
    }

    /**
     * Test that a block is invalid upon its creation. A block is invalid if it does not
     * start with a begin token. The provided text file is set up so that the block provided
     * does not have a begin statement, and intentionally causing a parsing error.
     */
    @Test
    public void testInvalidBlock() {
        TurtleParser parser = new TurtleParser("./Test/BlockInvalid.txt");
        assertThrows(IllegalArgumentException.class, () -> {parser.draw();});
    }

    /**
     * Tests when provided with an invalid token, it will not know how to parse it and throw
     * an exception. This means in java for example, trying to use bool in place of boolean
     * and the parser would not know what it is.
     */
    @Test
    public void testInvalidToken() {
        TurtleParser parser = new TurtleParser("./Test/InvalidToken.txt");
        assertThrows(IllegalArgumentException.class, () -> {parser.draw();});
    }

    /**
     * Tests that the attributes of the turtle parser are working up to par. This means that
     * whenever the language sets variables, it checks the value of those variables at the
     * end of the run.
     * 
     * @throws FileNotFoundException if the file provided does not exist.
     */
    @Test
    public void testAttributes() throws FileNotFoundException {
        TurtleParser parser = new TurtleParser("./Test/AttributesTest.txt");
        parser.draw();
        assertEquals(4, parser.times);
        assertEquals(40, parser.distance);
        assertEquals(60, parser.angle);
    }

    /**
     * Tests that the attributes of the turtle parser are working up to par. This means that
     * whenever the language sets variables, it checks the value of those variables at the
     * end of the run. But this also checks if the variables are being updated whenever they
     * run.
     * 
     * For example if the variable times = 4, and is then changed to 8, it needs to
     * equal 8 in this test
     * 
     * @throws FileNotFoundException if the file provided does not exist.
     */
    @Test
    public void testChangingAttributes() throws FileNotFoundException {
        TurtleParser parser = new TurtleParser("./Test/ChangeAttribute.txt");
        parser.draw();
        assertEquals(8, parser.times);
        assertEquals(80, parser.distance);
        assertEquals(120, parser.angle);
    }

}
