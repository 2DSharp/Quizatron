package me.twodee.quizatron.Component.Player;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.inject.Inject;

import java.io.File;

public class Player {

    private MediaView mediaView;
    private MediaPlayer mediaPlayer;
    private Pane parentContainer;
    private FileChooser fileChooser;

    private boolean playerState;

    @FXML private AnchorPane playerNode;
    @FXML private Button playBtn;
    @FXML private Button pauseBtn;
    @FXML private MediaView mediaBox;

    @Inject
    public Player(FileChooser fileChooser) {
        this.mediaView = mediaBox;
        this.fileChooser = fileChooser;
    }

    public MediaView getMediaView() {
        return mediaView;
    }

    public void loadMedia(String source) {

        this.setMediaPlayer(new MediaPlayer(new Media(source)));
    }

    public void chooseMediaFile() {

        fileChooser.setTitle("Open Resource File");
        File file = fileChooser.showOpenDialog((Stage)playerNode.getScene().getWindow());
        try {
            String source = file.toURI().toURL().toExternalForm();
            this.loadMedia(source);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void getSource() {

        this.mediaPlayer.getMedia().getSource();
    }

    public void setMediaPlayer(MediaPlayer mediaPlayer) {
        this.mediaPlayer = mediaPlayer;
    }

    @FXML
    public void play(ActionEvent event) {

        if (mediaBox.getMediaPlayer() != this.mediaPlayer) {
        //mediaView.setMediaPlayer();
            mediaBox.setMediaPlayer(this.mediaPlayer);
        }
        this.setPauseIcon();
        mediaPlayer.play();
    }

    @FXML public void pause(ActionEvent event) {

        this.setPlayIcon();
        mediaPlayer.pause();
    }

    private void setPlayIcon() {
        FontAwesomeIconView playIcon = new FontAwesomeIconView(FontAwesomeIcon.PLAY);
        playIcon.setGlyphSize(16);
        playIcon.setStyleClass("trackBtn");
        playBtn.setGraphic(playIcon);
        playBtn.setOnAction(this::play);
    }

    private void setPauseIcon() {
        FontAwesomeIconView pauseIcon = new FontAwesomeIconView(FontAwesomeIcon.PAUSE);
        pauseIcon.setStyleClass("trackBtn");
        pauseIcon.setGlyphSize(16);
        playBtn.setGraphic(pauseIcon);
        playBtn.setOnAction(this::pause);
    }
    public void openPlaylist() {

    }
    public void back() {

    }
    public void next() {

    }
    public void stop() {
        mediaPlayer.stop();
        setPlayIcon();
    }
}
