import java.io.*;
import java.util.*;

public class step2 {

    //Global Private variables to help with parsing file
    private static Scanner scan;
    private static String token;
    private static int curNum;
    private static DrawableTurtle turtle;

    public static void main(String[] args) {

        String input = "step1\\testProgramStep2.txt";
        turtle = new DrawableTurtle();

        try {
            scan = new Scanner(new File(input));
            advance();

            if ("begin".equals(token)) {
                block();
                // Only draws if EOF reached
                if (!"programEnd".equals(token)) {
                    System.err.println("Expected 'programEnd' at the end.");
                } else {
                    turtle.draw();
                }
            } else {
                System.err.println("Program must start with 'begin'");
            }
        } catch (Exception e) {
            // Stops program if there is an error
            System.err.println("Error: " + e.getMessage());
        }

    }

    /**
     * Parses the lines between the begin and end token
     */
    public static void block() {
        advance(); // Begin token
        while (!("end".equals(token))) {
            statement();
        }
        advance(); // End token
    }

    /**
     * Detects tokens and takes appropriate action
     */
    public static void statement() {
        switch (token) {
            case "forward":
                advance();
                curNum = number();
                turtle.forward(curNum);
                break;
            case "turn":
                advance();
                curNum= number();
                turtle.turn(curNum);
                break;
            case "loop":
                advance();
                curNum = number();
                loop(curNum);
                break;
            case "end":
                break;
            default:
                throw new IllegalArgumentException("Invalid Command: " + token);
        }
    }

    /**
     * Parses the next token as an integer if possible
     * 
     * @return integer value of token
     */
    private static int number() {
        int value = 0;
        if (token.matches("^-?\\d+$")) {
            value = Integer.parseInt(token);
            advance();
        } else {
            throw new IllegalArgumentException("Not an Integer: " + token);
        }
        return value;
    }

    /**
     * Runs a nested block of code a certain number of times
     * @param count Number of repeated runs
     */
    public static void loop(int count) {
        if (!"begin".equals(token)) {
            throw new IllegalArgumentException("Expected 'begin' after loop count.");
        }

        // Helps with running the loops
        List<String> toklist = new ArrayList<>();
        List<Integer> vallist = new ArrayList<>();


        //Same functionality as block() but allows us to copy the values into 
        // the temporary arrays
        advance(); // Begin Token
        while (!token.equals("end")) {
            toklist.add(token);
            statement();
            vallist.add(curNum);
        }
        advance(); //End token

        // The block of code is run once before this looping funcionality, so
        // we run tthrough the lists count - 1 times
        for (int i = 0; i < count - 1; i++) {
            for (int j = 0; j < toklist.size(); j++) {
                String currtok = toklist.get(j);
                int curval = vallist.get(j);

                if (currtok.equals("forward")) {
                    turtle.forward(curval);
                }

                if (currtok.equals("turn")) {
                    turtle.turn(curval);
                }   
            }
        }
        
    }

    /**
     * Method for setting tokens and detecting EOF
     */
    private static void advance() {
        if (scan.hasNext()) {
            token = scan.next();
        } else {
            token = "programEnd";
        }
    }
}
