import java.util.ArrayList;
import java.util.Scanner;

import com.graphene.app.App;
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<String> program = new ArrayList<>();

        while (scanner.hasNextLine()) {
            String tokens = scanner.nextLine();
            program.add(tokens);
        }

        String stringProgram = String.join("\n", program);

        System.out.println(
                App.parseProgram(stringProgram)
        );
        
        scanner.close();
        program.clear();
    }
}
