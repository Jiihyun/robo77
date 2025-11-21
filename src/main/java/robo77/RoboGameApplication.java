package robo77;

import robo77.controller.RoboGameController;
import robo77.view.ConsoleInput;
import robo77.view.output.ConsoleOutput;

public class RoboGameApplication {
    public static void main(String[] args) {
        ConsoleInput consoleInput = new ConsoleInput();
        ConsoleOutput consoleOutput = new ConsoleOutput();
        RoboGameController roboGameController = new RoboGameController(consoleInput, consoleOutput);
        roboGameController.run();
    }
}
