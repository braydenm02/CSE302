import java.util.regex.Pattern;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class step1 {

    private String digit = "(0|-?[1-9]{0-9}*)";
    private Pattern pattern = Pattern.compile(digit);

    public static void main(String[] args) {
        String input = "";
        Scanner scan = new Scanner(input);

        String token = scan.next();
        if ("begin".equals(token)) {
            Block(scan);
        }
    }

    public static void Block(Scanner scan) {
        String token = scan.next();

        // Acts as a statement list,
        // parsing the file
        while (!("end".equals(token))) {
            statement_list(scan);
            token = scan.next();
        }
    }

    public static void statement_list(Scanner scan) {
        String token = scan.next();

        if ("forward".equals(token)) {

        } else if ("turn".equals(token)) {

        } else {
            return;
        }
    }
}
