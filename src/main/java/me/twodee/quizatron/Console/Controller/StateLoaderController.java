package me.twodee.quizatron.Console.Controller;

import me.twodee.quizatron.Component.Controller;
import me.twodee.quizatron.Component.Mediator;
import me.twodee.quizatron.Component.State.ObjectSerializationIOStrategy;
import me.twodee.quizatron.Component.State.State;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Map;

public class StateLoaderController implements Controller {

    private State state;
    private Map input;
    private Mediator mediator;

    public StateLoaderController(Mediator mediator, State state) {

        this.mediator = mediator;
        this.state = state;
    }

    public void setInput(Map input) {

        this.input = input;
    }
    @Override
    public void update() {
        try {
            state.load(new ObjectSerializationIOStrategy(), (Path) input.get("something"));
        }
        catch (ClassNotFoundException e) {
            mediator.setError("There was a problem loading the file. The file may be corrupted.");
        }
        catch (IOException e) {
            mediator.setError("The was a problem loading the file, " +
                                      "check if you have enough permissions to read the file.");
        }

    }
}
