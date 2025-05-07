import java.io.File;
import java.util.*;

public class step3 {

    //Holds our tokens
    private static List<String> tokens;
    private static DrawableTurtle turtle;
    //Current token
    private static int index;
    //TODO: This is part of step three
    private static int dist, angle, times;

    public static void main(String[] args) {

        String input = "step1\\testProgramStep3.txt";
        turtle = new DrawableTurtle();
        tokens = new ArrayList<>();

        try {
            //Scans the file as an arraylist of tokens
            Scanner scan = new Scanner(new File(input));
            while (scan.hasNext()) {
                tokens.add(scan.next());
            }
            scan.close();
        } catch (Exception e) {
            // Stops program if there is an error
            System.err.println("Error: " + e.getMessage());
        }
        //Temporary block for seeing the input file as a list of tokens with 
        // their indexes, can delete after done
        int i = 0;
        for (String x : tokens) {
            System.out.println(i + ": " + x);
            i++;
        }
        index = 0;
        //Starts the program at the beginning
        block(0);

        turtle.draw();
    }

    public static void block(int start) {
        if (!(tokens.get(start).equals("begin"))) {
            throw new IllegalArgumentException("Blocks Must start with Begin");
        }
        //Skip the Begin token
        index = index + 1;
        //Block lasts until "end" token
        while (!(tokens.get(index).equals("end"))) {
            //Breaks program at programEnd token if the end token was not 
            // parsed correctly
            if (tokens.get(index).equals("programEnd")) {
                return;
            }
            //Calls the command we are currently on
            statement(index);
        }
        
    }

    public static void statement(int current) {
        //Current COmmand
        String token = tokens.get(current);
        int tokenVal;
        
        //Commands possible in a statement list
        switch (token) {
            case "forward" -> {
                // Next token the distance moved
                tokenVal  = assignment(current + 1);
                turtle.forward(tokenVal);
                //Increases index into next command token
                index = index + 2;
            }
            case "turn" -> {
                tokenVal  = assignment(current + 1);
                turtle.turn(tokenVal);
                index = index + 2;
            }
            case "loop" -> {
                //Number of times we loop
                tokenVal  = assignment(current + 1);
                //Skips to the next command line
                index = index + 2;
                loop(tokenVal, index);
            }
            //TODO: These are a part of step 3
            //Tokens are: name '=' number
            // increase index by 3 becuase one command line uses three tokens
            case "dist" -> {
                dist = number(tokens.get(current + 2));
                index = index + 3;
            }
            case "angle" -> {
                angle  = number(tokens.get(current + 2));
                index = index + 3;
            }
            case "times" -> {
                times = number(tokens.get(current + 2));
                index = index + 3;
            }
            default -> {
                //Error if invalid token detected
                throw new IllegalArgumentException("Not Valid: " + token);
            }
        }
    }

    /**
     * TODO check functionality, should return either the value of the variable
     * or the value of the integer being parse
     * 
     * @param assign The token that is either a variable or integer
     * @return integer 
     */
    public static int assignment(int assign) {
        if (tokens.get(assign).equals("angle")) {
            return angle;
        } 
         if (tokens.get(assign).equals("dist")) {
            return dist;
        } 
        if (tokens.get(assign).equals("times")) {
            return times;
        }
        return number(tokens.get(assign));
    }

    /**
     * Looping method to help with the recursive nature of loops
     * 
     * @param count Number of times the block is looped
     * @param place Start of the block
     */
    public static void loop(int count, int place) {
        for (int i = 0; i < count; i++) {
            // Resets the index to the 'begin' token of the block before every 
            // loop
            index = place;
            block(place);
        }
        //After loop is finished, we increase to the command line after the 
        //'end' token
        index++;
    }

    /**
     * Parses a terminal as an integer
     * 
     * @param curr Token being parsed
     * @return integer value of the string
     */
    public static int number(String curr) {
        if (curr.matches("^-?\\d+$")) {
            return Integer.parseInt(curr);
        } else {
            throw new IllegalArgumentException("Not an Integer: " + curr);
        }
    }
}
