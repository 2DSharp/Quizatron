package me.twodee.quizatron.Console.Dashboard;

import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import me.twodee.quizatron.Presentation.Presentation;
import me.twodee.quizatron.Presentation.PresentationFactory;
import me.twodee.quizatron.Presentation.IView;
import me.twodee.quizatron.Model.Score;
import me.twodee.quizatron.Presentation.View.HomeView;

import javax.inject.Inject;
import javafx.event.ActionEvent;
import java.io.File;

public class DashboardPresenter {

    private Score score;
    private PresentationFactory presentationFactory;
    private Presentation presentation;
    private IView view;
    private MediaPlayer mediaPlayer;

    @FXML private AnchorPane rootNode;

    @Inject
    public DashboardPresenter(Score score, PresentationFactory presentationFactory) throws Exception {
        this.score = score;
        this.presentationFactory = presentationFactory;
        this.presentation = this.presentationFactory.create();
    }

    @FXML
    public void startQuiz(MouseEvent event) {

        presentation.show();
        this.view = presentation.getView();
    }

    public String getSourceFile() {

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        File file = fileChooser.showOpenDialog((Stage)rootNode.getScene().getWindow());

        // Create the media source.
        try {
            String source = file.toURI().toURL().toExternalForm();
            return source;
        }

        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    @FXML
    public void openMedia(MouseEvent event) {

        String mediaSource = getSourceFile();
        Media media = new Media(mediaSource);

        // Create the player and set to play automatically.
        this.mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setAutoPlay(true);

        // Create the view and add it to the Scene.
        MediaView mediaView = new MediaView(mediaPlayer);

        DoubleProperty mvw = mediaView.fitWidthProperty();
        DoubleProperty mvh = mediaView.fitHeightProperty();
        mvw.bind(Bindings.selectDouble(mediaView.sceneProperty(), "width"));
        mvh.bind(Bindings.selectDouble(mediaView.sceneProperty(), "height"));


        try {
            this.presentation = makeNewPresentation("secondview");
            ((AnchorPane) presentation.getScene().getRoot()).getChildren().add(mediaView);

            presentation.getStage().setScene(presentation.getScene());
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML private void playMedia(ActionEvent event) {
        this.mediaPlayer.play();
    }

    @FXML private void pauseMedia(ActionEvent event) {
        this.mediaPlayer.pause();
    }

    private Presentation makeNewPresentation(String viewFxml) throws Exception {
        return this.presentationFactory.create(
                presentation.getStage(),
                presentation.getScene(),
                "secondview");

    }
    public void showScore() {

        this.score.setScore(100);
        HomeView view = (HomeView)this.view;
        view.updateScore(this.score.getScore());
    }
}
