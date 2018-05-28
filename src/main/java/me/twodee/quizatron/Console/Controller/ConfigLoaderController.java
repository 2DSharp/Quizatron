package me.twodee.quizatron.Console.Controller;

import me.twodee.quizatron.Component.Controller;
import me.twodee.quizatron.Component.Mediator;
import me.twodee.quizatron.Component.State.State;
import me.twodee.quizatron.Model.Entity.Configuration.Configuration;
import me.twodee.quizatron.Model.Service.ConfigurationManager;

import java.io.FileNotFoundException;
import java.nio.file.Path;

public class ConfigLoaderController implements Controller {

    private State state;
    private Path file;
    private ConfigurationManager configurationManager;
    private Mediator mediator;

    public ConfigLoaderController(Mediator mediator, State state, ConfigurationManager configurationManager) {

        this.mediator = mediator;
        this.state = state;
        this.configurationManager = configurationManager;
    }

    public void setInput(Path file) {

        this.file = file;
    }
    public void update() {

        try {

            loadConfigToState(file);
        }
        catch (FileNotFoundException e) {

            mediator.setError("The file you entered couldn't be found");
        }
    }

    private void loadConfigToState(Path file) throws FileNotFoundException {

        Configuration configuration = configurationManager.loadConfiguration(file);
        state.set("configuration", configuration);
        state.set("homedir", file.getParent().toAbsolutePath().toString());
    }
}
