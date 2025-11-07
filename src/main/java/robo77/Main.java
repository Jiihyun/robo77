package robo77;

import robo77.io.Reader;
import robo77.io.Writer;
import robo77.io.reader.ConsoleReader;
import robo77.io.writer.ConsoleWriter;
import robo77.view.InputView;
import robo77.view.OutputView;

public class Main {
    public static void main(String[] args) {
        Reader reader = new ConsoleReader();
        Writer writer = new ConsoleWriter();
        InputView inputView = new InputView(reader, writer);
        OutputView outputView = new OutputView(writer);
        RoboGame roboGame = new RoboGame(inputView, outputView);
        roboGame.run();

    }
}
