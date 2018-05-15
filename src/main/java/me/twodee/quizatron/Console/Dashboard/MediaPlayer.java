package me.twodee.quizatron.Console.Dashboard;

import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import me.twodee.quizatron.Component.Player.Player;
import me.twodee.quizatron.Presentation.IView;
import me.twodee.quizatron.Presentation.Presentation;
import me.twodee.quizatron.Presentation.PresentationFactory;

import javax.inject.Inject;
import javafx.event.ActionEvent;
import java.io.File;

public class MediaPlayer extends IView {

    private FileChooser fileChooser;
    @FXML private AnchorPane mediaNode;
    @FXML private Label loadedMedia;

    private String source;
    private javafx.scene.media.MediaPlayer mediaPlayer;
    private MediaView mediaView;
    private PresentationFactory presentationFactory;
    private Presentation presentation;

    @FXML private Parent player;

    @FXML private Player playerController;

    public void setPresentation(Presentation presentation) {

        this.presentation = presentation;
        playerController.setPresentation(presentation);
    }

    private Presentation makeNewPresentation(String viewFxml) throws Exception {
        return this.presentationFactory.create(
                presentation.getStage(),
                presentation.getScene(),
                viewFxml);
    }

}
