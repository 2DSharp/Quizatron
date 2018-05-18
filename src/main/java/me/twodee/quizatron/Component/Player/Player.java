package me.twodee.quizatron.Component.Player;

import com.jfoenix.controls.JFXSlider;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableMap;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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
 * Media Player component for Quizatron
 * @author Dedipyaman Das <2d@twodee.me>
 * @version 2018
 * @since 1.0.18.1
 */
public class Player {

    private static final String PLAY = "play";
    private static final String PAUSE = "pause";
    private MediaView mediaView;
    private MediaPlayer mediaPlayer;
    private FileChooser fileChooser;
    private Presentation presentation;

    @FXML private AnchorPane playerNode;
    @FXML private Button playBtn;
    @FXML private Label currTimeLbl;
    @FXML private Label endTimeLbl;
    @FXML private JFXSlider timeSlider;
    @FXML private Label mediaInfo;
    @FXML private Label sourceFileLbl;
    /**
     * Media player component constructor.
     * Guice or any other injector needs to inject a {@link FileChooser} for getting the media file,
     * {@link MediaView} for visual output
     * and {@link Presentation} to have it presented in a separate view
     * @param fileChooser FileChooser - single file
     * @param mediaView MediaView - Parent container for visual output
     * @param presentation Presentation state for separate stage
     */
    @Inject
    public Player(FileChooser fileChooser, MediaView mediaView, Presentation presentation) {
        this.mediaView = mediaView;
        this.fileChooser = fileChooser;
        this.presentation = presentation;
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
     * Loads the media and instantiates a new media player
     * @param source String- Media file source location
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
     * @param event ActionEvent
     * @throws Exception NullPointerException thrown on failure to open file
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
     * @param source String - source file to be played
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

            ObservableMap<String, Object> metaData = mediaPlayer.getMedia().getMetadata();

            String artistName = (String) metaData.get("artist");
            String title = (String) metaData.get("title");
            String album = (String) metaData.get("album");
            String mediaSource = mediaPlayer.getMedia().getSource();

            mediaSource = mediaSource.replace("%20", " ");
            mediaInfo.setText(title+ " - " + artistName + " - " + album);
            sourceFileLbl.setText(mediaSource.substring( mediaSource.lastIndexOf('/')+1,
                    mediaSource.length()));

            int duration = (int) mediaPlayer.getTotalDuration().toSeconds();
            endTimeLbl.setText(String.format("%02d", duration / 60) + ":" +
                    String.format("%02d", duration  % 60));
            });
    }
    /**
     * Initialize the slider behavior
     */
    private void initializeTimeSlider() {

        initSliderSeekBehavior();
        initSliderProgressBehavior();
    }
    /**
     * Sets the behavior of the player UI based on the player state
     */
    private void initUIControlsBehavior() {
        // Compromising on the readability a bit, maybe switch to a more verbose approach?
        mediaPlayer.setOnEndOfMedia(() -> {
            stop();
            timeSlider.setValue(1);
        });

        mediaPlayer.setOnPaused(() -> {
            setPlayPauseIcon(PLAY);
        });

        mediaPlayer.setOnPlaying(() -> {
            setPlayPauseIcon(PAUSE);
        });
    }
    /**
     * The slider behavior on dragging/clicking on the JFXSlider to seek
     * @see JFXSlider
     */
    private void initSliderSeekBehavior() {
        timeSlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                // Is the change significant enough?
                // Drag was buggy, have to run some tests
                if (abs(oldValue.doubleValue() - newValue.doubleValue()) > 0.1) {
                    mediaPlayer.seek(Duration.millis(newValue.doubleValue() *
                            mediaPlayer.getTotalDuration().toMillis() / 100.0));
                }
            }
        });
    }
    /**
     * Behavior of the slider as it progresses
     * Setting the time elapsed and time left on the appropriate indicators
     */
    private void initSliderProgressBehavior() {

        this.mediaPlayer.currentTimeProperty().addListener((observableValue, oldDuration, newDuration) -> {
            // Making sure it doesn't interfere with the manual seeking
            if (!timeSlider.isValueChanging()) {

                timeSlider.setValue((newDuration.toMillis() / this.mediaPlayer.getTotalDuration().toMillis())
                        * 100.0);
                String currentTime = String.format("%02d",(int) (newDuration.toSeconds() / 60))+ ":" +
                        String.format("%02d", (int) (newDuration.toSeconds() % 60));

                double realTimeLeft = (mediaPlayer.getTotalDuration().toSeconds() - newDuration.toSeconds());
                int timeLeft = (int) realTimeLeft;
                // Time elapsed/left indicators update
                endTimeLbl.setText(String.format("%02d", timeLeft / 60) + ":" +
                        String.format("%02d", timeLeft  % 60));
                currTimeLbl.setText(currentTime);
            }
        });
    }
    /**
     * Setter for the class media player
     * @param mediaPlayer MediaPlayer {@link MediaPlayer}
     */
    private void setMediaPlayer(MediaPlayer mediaPlayer) {
        this.mediaPlayer = mediaPlayer;
    }
    /**
     * User action to play the media
     * @param event ActionEvent
     */
    @FXML
    private void play(ActionEvent event) {
        playMedia();
    }
    /**
     * Prepare the external presentation view
     * Get the view controller and embed visual output
     * {@link Presentation}
     */
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
    /**
     * Check if the media player was already there, prepare the presentation and play the video
     */
    private void playMedia() {

        if (mediaView.getMediaPlayer() != this.mediaPlayer) {
            preparePresentation();
        }
        mediaPlayer.play();
    }
    /**
     * User action event to pause the video
     * @param event
     */
    @FXML private void pause(ActionEvent event) {

        mediaPlayer.pause();
    }
    /**
     * Change the pause button to a play button and have the appropriate action based on it
     * {@link FontAwesomeIconView}
     */
    private void setPlayPauseIcon(String status) {

        FontAwesomeIconView icon;
        if (status == PLAY) {
            icon= new FontAwesomeIconView(FontAwesomeIcon.PLAY);
            playBtn.setOnAction(this::play);
        }
        else {
            icon = new FontAwesomeIconView(FontAwesomeIcon.PAUSE);
            playBtn.setOnAction(this::pause);
        }
        icon.setGlyphSize(16);
        icon.setStyleClass("trackBtnIcon");
        playBtn.setGraphic(icon);
    }
    /**
     * User action event to stop the media
     */
    public void stop() {
        mediaPlayer.stop();
        setPlayPauseIcon(PLAY);
    }

    public void openPlaylist() {

    }
    public void back() {

    }
    public void next() {

    }


}
