package robo77.view;

import robo77.io.Reader;
import robo77.io.Writer;

public class InputView {

    private static final String NEW_LINE = System.lineSeparator();

    private final Reader reader;
    private final Writer writer;

    public InputView(Reader reader, Writer writer) {
        this.reader = reader;
        this.writer = writer;
    }

    public String readPlayerName() {
        String message = "로보77 게임을 시작합니다!" + NEW_LINE + "이름을 입력해주세요.";
        writer.writeLine(message);
        return reader.readLine();
    }

    public String readCardToSubmit() {
        writer.writeLine("제출할 카드를 입력해주세요.");
        return reader.readLine();
    }
}
