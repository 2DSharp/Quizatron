package me.twodee.quizatron.Console.Dashboard;

import javafx.fxml.FXML;
import javafx.scene.Parent;

import me.twodee.quizatron.Component.Player.Player;
import me.twodee.quizatron.Presentation.Presentation;

public class MediaPlayerPresenter {


    private Presentation presentation;

    @FXML private Parent player;
    @FXML private Player playerController;

    public void setPresentation(Presentation presentation) {

        this.presentation = presentation;
        playerController.setPresentation(presentation);
    }
}
