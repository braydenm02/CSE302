import java.io.*;
import java.util.*;

public class step2 {

    private static Scanner scan;
    private static String token;
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
        System.out.println(token);
        advance(); // Begin token
        while (!("end".equals(token))) {
            statement();
        }
        System.out.println(token);
        advance(); // End token
    }

    /**
     * Detects tokens and takes appropriate action
     */
    public static void statement() {
        switch (token) {
            case "forward":
                advance();
                System.err.print("forward: ");
                int distance = number();
                turtle.forward(distance);
            case "turn":
                advance();
                System.err.print("turn: ");
                int angle = number();
                turtle.turn(angle);
            case "loop":
                advance();
                System.err.print("loop: ");
                int count = number();
                loop(count);
            case "end":
                System.err.println("end");
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
            System.err.println(value);
            advance();
        } else {
            throw new IllegalArgumentException("Not an Integer: " + token);
        }
        return value;
    }

    public static void loop(int count) {
        if (!"begin".equals(token)) {
            throw new IllegalArgumentException("Expected 'begin' after loop count.");
        }
        for (int i = 0; i < count; i++) {
            block();
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
