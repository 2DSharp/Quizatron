package me.twodee.quizatron.Component.Player;

import com.jfoenix.controls.JFXSlider;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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

import javax.inject.Inject;

import java.io.File;
import java.net.URL;
import java.text.SimpleDateFormat;

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
    @FXML private Label sourceFile;

    @Inject
    public Player(FileChooser fileChooser, MediaView mediaView, PresentationFactory presentationFactory) {
        this.mediaView = mediaView;
        this.fileChooser = fileChooser;
        this.presentationFactory = presentationFactory;
    }
    @FXML
    public void initialize() {

        timeSlider.setValue(0);
    }
    public MediaView getMediaView() {

        return mediaView;
    }

    public void setPresentation(Presentation presentation) {
        this.presentation = presentation;
    }

    public void loadMedia(String source) {

        this.setMediaPlayer(new MediaPlayer(new Media(source)));
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
            this.loadMedia(source);

            timeSlider.setValue(this.mediaPlayer.getCurrentTime().toSeconds());

            initializePlayer();
            initializeSlider();
            playMedia();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
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
                sourceFile.setText(mediaSource.substring( mediaSource.lastIndexOf('/')+1,
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

    public void playMedia() {
        if (mediaView.getMediaPlayer() != this.mediaPlayer) {
            //mediaView.setMediaPlayer();
            mediaView.setMediaPlayer(this.mediaPlayer);
            try {
                presentation = presentationFactory.create(presentation.getStage(), presentation.getScene(),
                        "media-view");
                presentation.show();
                me.twodee.quizatron.Presentation.View.Media mediaController = presentation.getLoader().getController();
                mediaController.embedMediaView(mediaView);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
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

        this.mediaPlayer.seek(Duration.millis(timeSlider.getValue() *
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
    /*
    public void bindToTime(Label timer, Text dater) {
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(0),
                        new EventHandler<ActionEvent>() {
                            @Override public void handle(ActionEvent actionEvent) {
                                Calendar time = Calendar.getInstance();
                                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
                                timer.setText(simpleDateFormat.format(time.getTime()));
                                SimpleDateFormat date = new SimpleDateFormat("EEEE, dd MMMM");
                                dater.setText(date.format(time.getTime()));
                            }
                        }
                ),
                new KeyFrame(Duration.seconds(1))
        );
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }
    */
}
