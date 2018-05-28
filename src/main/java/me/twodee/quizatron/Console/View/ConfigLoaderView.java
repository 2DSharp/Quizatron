package me.twodee.quizatron.Console.View;

import com.jfoenix.controls.JFXButton;
import javafx.scene.control.Label;
import me.twodee.quizatron.Component.Mediator;
import me.twodee.quizatron.Component.State.State;
import me.twodee.quizatron.Component.View;

import me.twodee.quizatron.Model.Entity.Configuration.Configuration;
import me.twodee.quizatron.Model.Service.ConfigurationManager;


import java.nio.file.Path;

public class ConfigLoaderView implements View {

    private Path file;
    private Label loadedQuizNameLbl;
    private JFXButton startBtn;
    private State state;
    private Mediator mediator;

    public ConfigLoaderView(Mediator mediator, State state) {

        this.mediator = mediator;
        this.state = state;
    }

    public void setOutput(Label loadedQuizNameLbl, JFXButton startBtn) {

        this.loadedQuizNameLbl = loadedQuizNameLbl;
        this.startBtn = startBtn;
    }

    public void display() {

        if (!mediator.hasError()) {

            Configuration configuration = state.get("configuration");
            loadedQuizNameLbl.setText(configuration.getName());
            startBtn.setDisable(false);
        }

        else {
            System.out.println(mediator.getError());
        }
    }
}
