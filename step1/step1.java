import java.io.*;
import java.util.*;

public class step1 {

    private static Scanner scan;
    private static String token;
    private static DrawableTurtle turtle;

    public static void main(String[] args) {
        
        String input = "step1\\testProgramStep1.txt";
        turtle = new DrawableTurtle();
        
        try {
            scan = new Scanner(new File(input));
            advance();

            if ("begin".equals(token)) {
                Block();
                //Only draws if EOF reached
                if (!"programEnd".equals(token)) {
                    System.err.println("Expected 'programEnd' at the end.");
                } else {
                    turtle.draw();
                }
            } else {
                System.err.println("Program must start with 'begin'");
            }
        } catch (Exception e) {
            //Stops program if there is an error
            System.err.println("Error: " + e.getMessage());
        }
        
    }

    /**
     * Parses the lines between the begin and end token
     */
    public static void Block() {
        advance(); // Begin token

        while (!("end".equals(token))) {
            statement();
        }
        advance(); //End token
    }

    /**
     * Detects tokens and takes appropriate action
     */
    public static void statement() {
        switch (token) {
            case "forward":
                advance();
                int distance = number();
                turtle.forward(distance);
            case "turn" :
                advance();
                int angle = number();
                turtle.turn(angle);
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
            throw new IllegalArgumentException("Not an Integer: " + token) ;
        }
        return  value;
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
