package me.twodee.quizatron.Console.Panel;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.MediaView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import me.twodee.quizatron.Console.Dashboard.MediaPlayer;
import me.twodee.quizatron.Presentation.Presentation;
import me.twodee.quizatron.Presentation.PresentationFactory;
import me.twodee.quizatron.Presentation.IView;
import me.twodee.quizatron.Model.Score;
import me.twodee.quizatron.Presentation.View.HomeView;

import javax.inject.Inject;
import javafx.event.ActionEvent;
import java.io.File;

public class PanelPresenter {

    private Score score;
    private PresentationFactory presentationFactory;
    private Presentation presentation;
    private IView view;
    private FXMLLoader fxmlLoader;

    @FXML private AnchorPane rootNode;
    @FXML private AnchorPane dashboard;

    @Inject
    public PanelPresenter(Score score, PresentationFactory presentationFactory, FXMLLoader fxmlLoader) throws Exception {
        this.score = score;
        this.presentationFactory = presentationFactory;
        this.fxmlLoader = fxmlLoader;
        this.presentation = this.presentationFactory.create();
    }

    @FXML
    public void startQuiz(ActionEvent event) {

        presentation.show();
        this.view = presentation.getView();
    }

    @FXML
    public void openMedia(MouseEvent event) throws Exception {

        FXMLLoader loader = this.fxmlLoader;
        loader.setLocation(getClass().getResource("../Dashboard/media-player.fxml"));
        AnchorPane mediaPlayerPane = loader.load();
        dashboard.getScene().getStylesheets().add(getClass().getResource("/Stylesheets/media.css").toExternalForm());

        //MediaPlayer mediaPlayer = loader.getController();
        //mediaPlayer.setPresentation(presentation);

        dashboard.getChildren().add(mediaPlayerPane);
    }


    public void showScore() {

        this.score.setScore(100);
        HomeView view = (HomeView)this.view;
        view.updateScore(this.score.getScore());
    }
}
