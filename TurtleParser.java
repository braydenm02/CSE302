import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import Work.DrawableTurtle;
import Work.step1;
import Work.step2;
import Work.step3;

/**
 * The TurtleParser is a program that reads from a text file indicating turtle
 * graphics movements and interprets it to draw the corresponding graphics.
 * 
 * This is the final step of all of the work detailed in the Work package.
 * To see how the project progressed:
 * 
 * @see step1 for the class that satisfied the first step.
 * @see step2 for the class that satisfied the second step.
 * @see step3 for the class that satisfied the third step.
 * 
 * @author Brayden Miranda, John Belak
 * @Date 05-07-2025
 */
public class TurtleParser {
    
    // === *** Attributes *** === //

    /**
     * The list of all of the tokens parsed by this class.
     */
    protected List<String> tokens = new ArrayList<String>();

    /**
     * The turtle that is being used to actually draw the contents. For the
     * turtle parser it is used
     */
    protected DrawableTurtle turtle = new DrawableTurtle();

    /**
     * Indexing used in loops for repeated actions.
     */
    protected int index;

    /**
     * The distance that the turtle should be traveling, stored here while its in
     * its parsing loop. This is a persistent data form when used to store a variable
     * about the language being used. For example whenever "distance = 50" exists, that
     * means that the variable distance becomes 50 until it is changed.
     */
    protected int distance;

    /**
     * The angle that the turtle is to turn. This is stored here while its parsing.
     * This is persitent data that is stored whenever the note "angle = 50" exists.
     * That means the variable of angle is 50 until it is changed.
     */
    protected int angle;

    /**
     * Times is a variable present in the language, and is used to determine
     * the number of times that a loop will occur.
     */
    protected int times;

    /**
     * Stores the filepath that the turtle parser is reading from. 
     * This is the source file for the language being parsed.
     */
    protected String filePath;

    // === *** Constructor *** === //

    /**
     * Creates the TurtleParser. This only assigns the filepath, but does not actually
     * draw the provided source file.
     * 
     * @param filePath the filepath.
     */
    public TurtleParser(String filePath) {
        this.filePath = filePath;
    }

    // === *** Drawing and Parsing Methods *** === //

    /**
     * Draws the conents of the file. This will parse out the contents of the provided
     * file when the turle parser was created. If the provided filepath is not a real
     * file by the time this draw method is called, a FileNotFoundException will be
     * thrown.
     * 
     * @implSpec Preconditions: The file path provided upon construction is a real file and is
     *                  formatted correctly at the time this method is called.
     * 
     * 
     * @throws FileNotFoundException if the file provided upon construction is not
     *                      a real file by the time this method is called.
     */
    public void draw() throws FileNotFoundException {

        Scanner scan = new Scanner(new File(filePath));

            while(scan.hasNext()) {
                tokens.add(scan.next());
            }

            scan.close();

        block(0);

        turtle.draw();
        
    }

    /**
     * A block is an entire "block" being parsed. A block is anything that is surrounded
     * by the "begin" and "end" keywords. A block may contain several loops or anything
     * else.
     * 
     * @implSpec Preconditions: The index provided is a token indicating the start of a block.
     * 
     * @throws IllegalArgumentException if the provided start token is "begin"
     * 
     * @param start the index of the token that is being processed.
     */
    protected void block(int start) {

        if (!(tokens.get(start).equals("begin"))) {
            throw new IllegalArgumentException("Blocks must start with Begin");
        }

        index++;

        while (!tokens.get(index).equals("end")) {
            if (tokens.get(index).equals("programEnd")) {
                return;
            }
            statement(index);
        }

    }

    /**
     * Used whenever there is an assignment operator, and parses the assigning
     * of values that are used. Each assignment is a reserved word for storing
     * the key attributes of the Turtle parser.
     * 
     * @implSpec Preconditions: The index provided is the index of a reserved variable
     *                  name.
     * 
     * @throws IllegalArgumentException if the index of the token provided is
     *                              not a reserved word for variable
     *                              assignment.
     * 
     * @impleNote Reserved Words: foward, turn, loop, dist, angle, times
     * 
     * 
     * @param current the index of the token that is being parsed by the statement.
     */
    protected void statement(int current) {

        String token = tokens.get(current);
        int tokenVal;

        if (token.equals("forward")) {

            tokenVal = assignment(current + 1);
            turtle.forward(tokenVal);
            index += 2;

        }

        else if (token.equals("turn")) {

            tokenVal = assignment(current + 1);
            turtle.turn(tokenVal);
            index += 2;

        }

        else if (token.equals("loop")) {

            tokenVal = assignment(current + 1);
            index += 2;
            loop(tokenVal, index);

        }

        else if (token.equals("dist")) {

            distance = number(tokens.get(current + 2));
            index += 3;

        }

        else if (token.equals("angle")) {
            angle = number(tokens.get(current + 2));
            index += 3;
        }

        else if (token.equals("times")) {
            times = number(tokens.get(current + 2));
            index += 3;
        }

        else {
            throw new IllegalArgumentException("Invalid Token: " + token);
        }

    }

    /**
     * Provided with a token index, will return the variable the token at
     * that index is trying to modify.
     * 
     * @implNote Preconditions: The parameter is the index of a token that
     *              corresponds to a variable within the turtle environment
     * 
     * @implNote Postconditions: A value equal to the specified token's
     *                      variable correspondance.
     * 
     * @param assign the index of the token that is attempting to alter
     *                  an attribute of the turtle parser environment.
     * 
     * 
     * @return the value of the variable.
     */
    protected int assignment(int assign) { 
        if (tokens.get(assign).equals("angle")) {
            return angle;
        }

        if (tokens.get(assign).equals("dist")) {
            return distance;
        }

        if (tokens.get(assign).equals("times")) {
            return times;
        }

        return number(tokens.get(assign));
    }

    /**
     * When provided with a loop, the loop may contain several blocks, and
     * so whenever a loop is encountered it will call the block as to parse
     * the contents of the loop. This is because the loop may contain several
     * blocks.
     * 
     * @implNote Preconditions: place is an index of a token representing a
     *                          start of a block.
     * 
     * @param count the number of blocks inside the loop
     * @param place the index of the block
     */
    protected void loop(int count, int place) {
        for (int i = 0; i < count; i++) {

            index = place;
            block(place);

        }
        index++;
    }

    /**
     * Ensures that the number matches the pattern of an integer. This is one
     * of the smallest units in the grammar, just something that is an integer.
     * 
     * @implNote Precondition: the string can be parsed to an integer
     * 
     * @implNote PostCondition: a value equal to the string's written integer
     *              value.
     * 
     * @throws IllegalArgumentException if the value passed is not an integer.
     * 
     * @param curr a string that has the 
     */
    protected int number(String curr) {
        if (curr.matches("^-?\\d+$")) {
            return Integer.parseInt(curr);
        } else {
            throw new IllegalArgumentException("Not an Integer: " + curr);
        }
    }

}
