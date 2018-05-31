package me.twodee.quizatron.Console.Controller;

import me.twodee.quizatron.Component.Controller;
import me.twodee.quizatron.Component.Mediator;
import me.twodee.quizatron.Component.State.State;
import me.twodee.quizatron.Model.Entity.Configuration.Configuration;
import me.twodee.quizatron.Model.Mapper.ConfigurationMapper;
import me.twodee.quizatron.Model.Service.QuizDataService;

import java.io.FileNotFoundException;
import java.nio.file.Path;

public class ConfigLoaderController implements Controller {

    private State state;
    private Path file;
    private ConfigurationMapper configurationMapper;
    private Mediator mediator;
    private QuizDataService quizDataService;

    public ConfigLoaderController(Mediator mediator, QuizDataService quizDataService) {

        this.mediator = mediator;
        this.state = state;
        this.quizDataService = quizDataService;
    }

    public void setInput(Path file) {

        this.file = file;
    }
    public void update() {

        try {

            quizDataService.loadConfig(file);
        }
        catch (FileNotFoundException e) {

            mediator.setError("The file you entered couldn't be found");
        }
    }
}
