package robo77.io.reader;

import robo77.exception.ExceptionMessage;
import robo77.io.Reader;

public class ConsoleReader implements Reader {

    @Override
    public String readLine() {
        String input = System.console().readLine().strip();
        validateInput(input);
        return input;
    }

    private void validateInput(String input) {
        if (input == null || input.isBlank()) {
            throw new IllegalArgumentException(ExceptionMessage.BLANK_INPUT.getMessage());
        }
    }
}
