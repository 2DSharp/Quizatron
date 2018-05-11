package me.twodee.quizatron.Presentation.View;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import me.twodee.quizatron.Presentation.IView;

import java.awt.*;

    public class HomeView extends IView {

    @FXML
    private Label score;

    public HomeView() {
        this.score = score;
    }

    public void updateScore(String score) {
        this.score.setText(score);
    }
    public void update() {

    }
}
