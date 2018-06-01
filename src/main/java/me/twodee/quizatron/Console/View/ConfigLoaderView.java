package me.twodee.quizatron.Console.View;

import com.jfoenix.controls.JFXButton;
import javafx.scene.control.Label;
import me.twodee.quizatron.Component.Mediator;
import me.twodee.quizatron.Component.View;

import me.twodee.quizatron.Model.Entity.Configuration.Configuration;
import me.twodee.quizatron.Model.Service.QuizDataService;


import java.nio.file.Path;

public class ConfigLoaderView implements View {

    private Label loadedQuizNameLbl;
    private JFXButton startBtn;
    private JFXButton pauseBtn;
    private JFXButton stopBtn;
    private Mediator mediator;
    private QuizDataService quizDataService;

    public ConfigLoaderView(Mediator mediator, QuizDataService quizDataService) {

        this.mediator = mediator;
        this.quizDataService = quizDataService;
    }

    public void setOutput(Label loadedQuizNameLbl, JFXButton startBtn, JFXButton pauseBtn, JFXButton stopBtn) {

        this.loadedQuizNameLbl = loadedQuizNameLbl;
        this.startBtn = startBtn;
        this.stopBtn = stopBtn;
        this.pauseBtn = pauseBtn;
    }

    public void display() {

        if (!mediator.hasError()) {

            Configuration configuration = quizDataService.getConfiguration();
            loadedQuizNameLbl.setText(configuration.getName());
            startBtn.setDisable(false);
            pauseBtn.setDisable(false);
            stopBtn.setDisable(false);
        }

        else {
            System.out.println(mediator.getError());
        }
    }
}
