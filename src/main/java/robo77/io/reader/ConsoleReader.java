package robo77.io.reader;

import java.util.Scanner;
import robo77.exception.ExceptionMessage;
import robo77.io.Reader;

public class ConsoleReader implements Reader {

    private final Scanner scanner = new Scanner(System.in);

    @Override
    public String readLine() {
        String input = scanner.nextLine().strip();
        validateInput(input);
        return input;
    }

    private void validateInput(String input) {
        if (input == null || input.isBlank()) {
            throw new IllegalArgumentException(ExceptionMessage.BLANK_INPUT.getMessage());
        }
    }
}
