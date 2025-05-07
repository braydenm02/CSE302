import java.io.File;
import java.util.*;

public class step2 {
    private static List<String> tokens;
    private static DrawableTurtle turtle;
    private static int index;

    public static void main(String[] args) {

        String input = "step1\\testProgramStep2.txt";
        turtle = new DrawableTurtle();
        tokens = new ArrayList<>();

        try {
            Scanner scan = new Scanner(new File(input));
            while (scan.hasNext()) {
                tokens.add(scan.next());
            }
            scan.close();
        } catch (Exception e) {
            // Stops program if there is an error
            System.err.println("Error: " + e.getMessage());
        }
        
        index = 0;
        block(0);

        turtle.draw();
    }

    public static void block(int start) {
        if (!(tokens.get(start).equals("begin"))) {
            throw new IllegalArgumentException("Blocks Must start with Begin");
        }
        index = index + 1;
        while (!(tokens.get(index).equals("end"))) {
            if (tokens.get(index).equals("programEnd")) {
                return;
            }
            statement(index);
        }
        
    }

    public static void statement(int current) {
        String token = tokens.get(current);
        int tokenVal;
        
        switch (token) {
            case "forward" -> {
                tokenVal  = number(tokens.get(current + 1));
                turtle.forward(tokenVal);
                index = index + 2;
            }
            case "turn" -> {
                tokenVal  = number(tokens.get(current + 1));
                turtle.turn(tokenVal);
                index = index + 2;
            }
            case "loop" -> {
                tokenVal  = number(tokens.get(current + 1));
                index = index + 2;
                loop(tokenVal, index);
            }
            default -> {
                throw new IllegalArgumentException("Not Valid: " + token);
            }
        }
    }

    public static void loop(int count, int place) {
        for (int i = 0; i < count; i++) {
            index = place;
            block(place);
        }
        index++;
    }

    public static int number(String curr) {
        if (curr.matches("^-?\\d+$")) {
            return Integer.parseInt(curr);
        } else {
            throw new IllegalArgumentException("Not an Integer: " + curr);
        }
    }
}
