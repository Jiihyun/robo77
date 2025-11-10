package robo77;

import robo77.controller.RoboGameController;
import robo77.io.Reader;
import robo77.io.Writer;
import robo77.io.reader.ConsoleReader;
import robo77.io.writer.ConsoleWriter;
import robo77.view.InputView;
import robo77.view.OutputView;

public class RoboGameApplication {
    public static void main(String[] args) {
        Reader reader = new ConsoleReader();
        Writer writer = new ConsoleWriter();
        InputView inputView = new InputView(reader, writer);
        OutputView outputView = new OutputView(writer);
        RoboGameController roboGameController = new RoboGameController(inputView, outputView);
        roboGameController.run();
    }
}
