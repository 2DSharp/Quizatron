package me.twodee.quizatron.Component.Player;

import com.jfoenix.controls.JFXSlider;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import me.twodee.quizatron.Presentation.Presentation;
import me.twodee.quizatron.Presentation.PresentationFactory;
import me.twodee.quizatron.Presentation.View.MediaPresentationView;

import javax.inject.Inject;

import java.io.File;

import static java.lang.Math.abs;

public class Player {

    private MediaView mediaView;
    private MediaPlayer mediaPlayer;
    private Pane parentContainer;
    private FileChooser fileChooser;
    private double currentPos;
    private boolean playerState;
    private Presentation presentation;
    private  PresentationFactory presentationFactory;

    @FXML private AnchorPane playerNode;
    @FXML private Button playBtn;
    @FXML private Button pauseBtn;
    @FXML private MediaView mediaBox;
    @FXML private Label currTimeLbl;
    @FXML private Label endTimeLbl;
    @FXML private JFXSlider timeSlider;
    @FXML private Label mediaInfo;
    @FXML private Label sourceFileLbl;

    @Inject
    public Player(FileChooser fileChooser, MediaView mediaView, PresentationFactory presentationFactory) {
        this.mediaView = mediaView;
        this.fileChooser = fileChooser;
        this.presentationFactory = presentationFactory;
    }
    @FXML
    public void initialize() {

        timeSlider.setValue(0);
        prepareMediaView();
    }
    public MediaView getMediaView() {

        return mediaView;
    }

    public void setPresentation(Presentation presentation) {
        this.presentation = presentation;
    }

    public void loadMedia(String source) {

        if (mediaPlayer != null && source != null) {
            mediaPlayer.dispose();
        }
        this.setMediaPlayer(new MediaPlayer(new Media(source)));
    }

    public void prepareMediaView() {
        DoubleProperty width = mediaView.fitWidthProperty();
        DoubleProperty height = mediaView.fitHeightProperty();

        width.bind(Bindings.selectDouble(mediaView.sceneProperty(), "width"));
        height.bind(Bindings.selectDouble(mediaView.sceneProperty(), "height"));
    }
    public void chooseMediaFile() {

        fileChooser.setTitle("Open Resource File");
        File file = fileChooser.showOpenDialog((Stage)playerNode.getScene().getWindow());
        try {
            String source = file.toURI().toURL().toExternalForm();
            play(source);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void play(String source) {

        this.loadMedia(source);
        timeSlider.setValue(this.mediaPlayer.getCurrentTime().toSeconds());
        initializePlayer();
        initializeSlider();
        playMedia();
    }
    private void initializePlayer() {

        mediaPlayer.setOnReady(new Runnable() {

            @Override
            public void run() {

                String artistName = (String) mediaPlayer.getMedia().getMetadata().get("artist");
                String title = (String)mediaPlayer.getMedia().getMetadata().get("title");
                String album = (String)mediaPlayer.getMedia().getMetadata().get("album");
                String mediaSource = mediaPlayer.getMedia().getSource();

                mediaSource = mediaSource.replace("%20", " ");
                mediaInfo.setText(title+ " - " + artistName + " - " + album);
                sourceFileLbl.setText(mediaSource.substring( mediaSource.lastIndexOf('/')+1,
                        mediaSource.length()));

                int duration = (int) mediaPlayer.getTotalDuration().toSeconds();
                endTimeLbl.setText(String.format("%02d", duration / 60) + ":" +
                        String.format("%02d", duration  % 60));
                }
        });
    }

    private void initializeSlider() {

        this.mediaPlayer.currentTimeProperty().addListener((observableValue, oldDuration, newDuration) -> {

            if (!timeSlider.isValueChanging()) {

                timeSlider.setValue((newDuration.toMillis() / this.mediaPlayer.getTotalDuration().toMillis())
                        * 100.0);
                String currentTime = String.format("%02d",(int) (newDuration.toSeconds() / 60))+ ":" +
                        String.format("%02d", (int) (newDuration.toSeconds() % 60));

                double realTimeLeft = (this.mediaPlayer.getTotalDuration().toSeconds() - newDuration.toSeconds());
                int timeLeft = (int) realTimeLeft;

                endTimeLbl.setText(String.format("%02d", timeLeft / 60) + ":" +
                        String.format("%02d", timeLeft  % 60));
                currTimeLbl.setText(currentTime);

                if (newDuration.toMillis() == this.mediaPlayer.getTotalDuration().toMillis()) {
                    this.stop();
                    timeSlider.setValue(1);
                }
            }
        });
    }

    public void getSource() {

        this.mediaPlayer.getMedia().getSource();
    }

    public void setMediaPlayer(MediaPlayer mediaPlayer) {
        this.mediaPlayer = mediaPlayer;
    }

    @FXML
    public void play(ActionEvent event) {
        playMedia();
    }

    private void preparePresentation() {

        mediaView.setMediaPlayer(this.mediaPlayer);
        try {
            if (!(presentation.getView() instanceof MediaPresentationView)) {

                presentation = presentationFactory.create(presentation.getStage(),
                        presentation.getScene(),
                        "media-view");
            }
            MediaPresentationView mediaViewController = (MediaPresentationView) presentation.getView();
            mediaViewController.embedMediaView(mediaView);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void playMedia() {
        if (mediaView.getMediaPlayer() != this.mediaPlayer) {
            preparePresentation();
        }
        this.setPauseIcon();
        mediaPlayer.play();
    }

    @FXML private void pause(ActionEvent event) {

        this.setPlayIcon();
        mediaPlayer.pause();
    }

    private void setPlayIcon() {
        FontAwesomeIconView playIcon = new FontAwesomeIconView(FontAwesomeIcon.PLAY);
        playIcon.setGlyphSize(16);
        playIcon.setStyleClass("trackBtnIcon");
        playBtn.setGraphic(playIcon);
        playBtn.setOnAction(this::play);
    }

    private void setPauseIcon() {

        FontAwesomeIconView pauseIcon = new FontAwesomeIconView(FontAwesomeIcon.PAUSE);
        pauseIcon.setStyleClass("trackBtnIcon");
        pauseIcon.setGlyphSize(16);
        playBtn.setGraphic(pauseIcon);
        playBtn.setOnAction(this::pause);
    }

    @FXML
    public void seek(MouseEvent event) {
        System.out.println("Seeked?");
        System.out.println(timeSlider.getValue());
        timeSlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                //System.out.println(newValue);
                if (abs(oldValue.doubleValue() - newValue.doubleValue()) > 1) {
                    //System.out.println(newValue+ " " + timeSlider.getValue());
                    mediaPlayer.seek(Duration.millis(timeSlider.getValue() *
                            mediaPlayer.getTotalDuration().toMillis() / 100.0));
                }
            }
        });
    }

    @FXML public void dragSeek(DragEvent event) {
        System.out.println("dragged");
        mediaPlayer.seek(Duration.millis(timeSlider.getValue() *
                mediaPlayer.getTotalDuration().toMillis() / 100.0));
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
