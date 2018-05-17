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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import me.twodee.quizatron.Presentation.Presentation;
import me.twodee.quizatron.Presentation.View.MediaPresentationView;
import javax.inject.Inject;

import java.io.File;

import static java.lang.Math.abs;

/**
 * @author dedipyaman
 * @version %I%, %G%
 * @since 1.0.18.1
 */
public class Player {

    private MediaView mediaView;
    private MediaPlayer mediaPlayer;
    private FileChooser fileChooser;
    private Presentation presentation;

    @FXML private AnchorPane playerNode;
    @FXML private Button playBtn;
    @FXML private Button pauseBtn;
    @FXML private MediaView mediaBox;
    @FXML private Label currTimeLbl;
    @FXML private Label endTimeLbl;
    @FXML private JFXSlider timeSlider;
    @FXML private Label mediaInfo;
    @FXML private Label sourceFileLbl;
    private boolean sliderChanging;

    /**
     * Media player component constructor.
     * It has to be injected with a {@link FileChooser} for getting the media file, {@link MediaView}
     * @see Presentation
     * @param fileChooser
     * @param mediaView
     * @param presentation
     */
    @Inject
    public Player(FileChooser fileChooser, MediaView mediaView, Presentation presentation) {
        this.mediaView = mediaView;
        this.fileChooser = fileChooser;
        this.presentation = presentation;
        sliderChanging = false;
    }

    /**
     * Initializer to get the FXML components working.
     */
    @FXML
    public void initialize() {
        timeSlider.setValue(0);
        prepareMediaView();
    }
    /**
     * Sets the presentation view
     * @param presentation
     */
    public void setPresentation(Presentation presentation) {
        this.presentation = presentation;
    }
    /**
     * Loads the media and instantiates a new media player
     * @param source Media file source location
     */
    public void loadMedia(String source) {

        if (mediaPlayer != null && source != null) {
            mediaPlayer.dispose();
        }
        this.setMediaPlayer(new MediaPlayer(new Media(source)));
    }
    /**
     * Prepare the mediaview window with correct dimensions
     * Binds the mediaview's width and height relative to the window size and video ratio
     * @see MediaPresentationView
     * @see Bindings
     */
    public void prepareMediaView() {
        DoubleProperty width = mediaView.fitWidthProperty();
        DoubleProperty height = mediaView.fitHeightProperty();

        width.bind(Bindings.selectDouble(mediaView.sceneProperty(), "width"));
        height.bind(Bindings.selectDouble(mediaView.sceneProperty(), "height"));
    }
    /**
     * Open the file chooser and autoplay the media
     * @param event Mouse click
     * @throws Exception
     */
    @FXML private void chooseMediaFile(ActionEvent event) throws Exception{

        fileChooser.setTitle("Open Resource File");
        File file = fileChooser.showOpenDialog((Stage)playerNode.getScene().getWindow());
        String source = file.toURI().toURL().toExternalForm();

        play(source);
    }
    /**
     * Plays the file
     * Loads the media file and initializes the slider
     * Plays the given media file
     * @param source
     */
    private void play(String source) {

        this.loadMedia(source);
        timeSlider.setValue(this.mediaPlayer.getCurrentTime().toSeconds());
        setMetaData();
        initializeTimeSlider();
        initUIControlsBehavior();
        playMedia();
    }

    /**
     * Fetches and substitutes the placeholders for media metadata
     */
    private void setMetaData() {

        mediaPlayer.setOnReady(() -> {

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
        );
    }

    private void initializeTimeSlider() {

        initSliderSeekBehavior();
        initSliderProgressBehavior();
    }

    /**
     * Sets the behavior of the player UI based on the player state
     */
    private void initUIControlsBehavior() {

        mediaPlayer.setOnEndOfMedia(() -> {
            stop();
            timeSlider.setValue(1);
        });

        mediaPlayer.setOnPaused(() -> {
            setPlayIcon();
        });

        mediaPlayer.setOnPlaying(() -> {
            setPauseIcon();
        });

    }
    private void initSliderSeekBehavior() {
        timeSlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {

                if (abs(oldValue.doubleValue() - newValue.doubleValue()) > 1) {

                    mediaPlayer.seek(Duration.millis(newValue.doubleValue() *
                            mediaPlayer.getTotalDuration().toMillis() / 100.0));
                }
            }
        });
    }
    @FXML private void clickDetect(MouseEvent event) {
        sliderChanging = true;
    }

    private void initSliderProgressBehavior() {

        this.mediaPlayer.currentTimeProperty().addListener((observableValue, oldDuration, newDuration) -> {

            if (!timeSlider.isValueChanging()) {
                timeSlider.setValue((newDuration.toMillis() / this.mediaPlayer.getTotalDuration().toMillis())
                        * 100.0);
                String currentTime = String.format("%02d",(int) (newDuration.toSeconds() / 60))+ ":" +
                        String.format("%02d", (int) (newDuration.toSeconds() % 60));

                double realTimeLeft = (mediaPlayer.getTotalDuration().toSeconds() - newDuration.toSeconds());
                int timeLeft = (int) realTimeLeft;

                endTimeLbl.setText(String.format("%02d", timeLeft / 60) + ":" +
                        String.format("%02d", timeLeft  % 60));
                currTimeLbl.setText(currentTime);
            }
        });
    }
    /**
     * Setter for the class media player
     * @param mediaPlayer
     */
    private void setMediaPlayer(MediaPlayer mediaPlayer) {
        this.mediaPlayer = mediaPlayer;
    }

    @FXML
    private void play(ActionEvent event) {
        playMedia();
    }

    private void preparePresentation() {

        mediaView.setMediaPlayer(this.mediaPlayer);
        try {
            if (!(presentation.getView() instanceof MediaPresentationView)) {

                presentation.changeView("media-view");
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
        mediaPlayer.play();
    }

    @FXML private void pause(ActionEvent event) {

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
