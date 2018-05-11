package me.twodee.quizatron.Console.Dashboard;

import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
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
    @Inject
    public MediaPlayer(PresentationFactory presentationFactory, FileChooser fileChooser)  {

        this.presentationFactory = presentationFactory;
        this.fileChooser = fileChooser;

    }

    public void setPresentation(Presentation presentation) {
        this.presentation = presentation;
    }

    private Presentation makeNewPresentation(String viewFxml) throws Exception {
        return this.presentationFactory.create(
                presentation.getStage(),
                presentation.getScene(),
                viewFxml);
    }

    @FXML
    public void play(ActionEvent event) {

        if (this.presentation.getView() instanceof me.twodee.quizatron.Presentation.View.Media) {
            // We already initialized the player
            this.mediaPlayer.play();
        }
        try {

            this.presentation = makeNewPresentation("media-view");
            MediaView mediaView = new MediaView(this.mediaPlayer);
            ((AnchorPane) presentation.getScene().getRoot()).getChildren().add(mediaView);
            presentation.getStage().setScene(presentation.getScene());
            this.mediaPlayer.play();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void pause(ActionEvent event) {
        this.mediaPlayer.pause();
    }
    @FXML
    public void stop(ActionEvent event) {
        this.mediaPlayer.stop();
    }

    @FXML
    public void mute(ActionEvent event) {
        this.mediaPlayer.setMute(true);
    }

    @FXML
    public void loadMedia(ActionEvent event) {

        fileChooser.setTitle("Open Resource File");
        File file = fileChooser.showOpenDialog((Stage)mediaNode.getScene().getWindow());

        // Create the media source.
        try {
            String source = file.toURI().toURL().toExternalForm();
            this.source = source;
            this.loadedMedia.setText(this.source);

            Media media = new Media(this.source);

            // Create the player and set to play automatically.
            this.mediaPlayer = new javafx.scene.media.MediaPlayer(media);
        }

        catch (Exception e) {
            e.printStackTrace();
        }
    }

}
