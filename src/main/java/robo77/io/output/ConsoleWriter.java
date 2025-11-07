package robo77.io.output;

import robo77.io.Writer;

public class ConsoleWriter implements Writer {

    @Override
    public void writeLine(String message) {
        System.out.println(message);
    }
}
