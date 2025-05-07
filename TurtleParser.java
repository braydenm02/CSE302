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
    private List<String> tokens = new ArrayList<String>();

    /**
     * The turtle that is being used to actually draw the contents. For the
     * turtle parser it is used
     */
    private DrawableTurtle turtle = new DrawableTurtle();

    /**
     * Indexing used in loops for repeated actions.
     */
    private int index;

    /**
     * The distance that the turtle should be traveling, stored here while its in
     * its parsing loop. This is a persistent data form when used to store a variable
     * about the language being used. For example whenever "distance = 50" exists, that
     * means that the variable distance becomes 50 until it is changed.
     */
    private int distance;

    /**
     * The angle that the turtle is to turn. This is stored here while its parsing.
     * This is persitent data that is stored whenever the note "angle = 50" exists.
     * That means the variable of angle is 50 until it is changed.
     */
    private int angle;

    /**
     * Times is a variable present in the language, and is used to determine
     * the number of times that a loop will occur.
     */
    private int times;

    /**
     * Stores the filepath that the turtle parser is reading from. 
     * This is the source file for the language being parsed.
     */
    private String filePath;

    // === *** Constructor *** === //

    public TurtleParser(String filePath) {
        this.filePath = filePath;
    }

    // === *** Parsing Methods *** === //

    public void draw() {

        try (Scanner scan = new Scanner(new File(filePath))) {

            while(scan.hasNext()) {
                tokens.add(scan.next());
            }

            scan.close();

        } catch (FileNotFoundException error) {

        }

        block(0);

        turtle.draw();
        
    }

    private void block(int start) {

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

    private void statement(int current) {

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

    private int assignment(int assign) { 
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

    private void loop(int count, int place) {
        for (int i = 0; i < count; i++) {

            index = place;
            block(place);

        }
        index++;
    }

    private int number(String curr) {
        if (curr.matches("^-?\\d+$")) {
            return Integer.parseInt(curr);
        } else {
            throw new IllegalArgumentException("Not an Integer: " + curr);
        }
    }

    public static void main(String[] args) {
        TurtleParser parser = new TurtleParser("./Work/testProgramStep3.txt");
        parser.draw();
    }


}
