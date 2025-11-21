package robo77.view;

import java.util.Scanner;
import robo77.exception.ExceptionMessage;

public class ConsoleInput {

    private static final String NEW_LINE = System.lineSeparator();

    private final Scanner scanner = new Scanner(System.in);

    public String readPlayerName() {
        String message = "로보77 게임을 시작합니다!" + NEW_LINE + "이름을 입력해주세요.";
        System.out.println(message);
        return readLine();
    }

    public String readCardToSubmit() {
        System.out.println("제출할 카드를 입력해주세요.");
        return readLine();
    }

    private String readLine() {
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
