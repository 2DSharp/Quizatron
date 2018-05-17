package me.twodee.quizatron.Console.Panel;

import com.jfoenix.controls.JFXToggleButton;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import me.twodee.quizatron.Console.Dashboard.MediaPlayerPresenter;
import me.twodee.quizatron.Presentation.Presentation;
import me.twodee.quizatron.Presentation.PresentationFactory;
import me.twodee.quizatron.Presentation.IView;
import me.twodee.quizatron.Model.Score;
import me.twodee.quizatron.Presentation.View.HomeView;

import javax.inject.Inject;
import javafx.event.ActionEvent;

public class PanelPresenter {

    private Score score;
    private PresentationFactory presentationFactory;
    private Presentation presentation;
    private IView view;
    private FXMLLoader fxmlLoader;

    @FXML private JFXToggleButton fullScreenToggleBtn;
    @FXML private AnchorPane rootNode;
    @FXML private AnchorPane dashboard;

    @Inject
    public PanelPresenter(Score score, Presentation presentation, FXMLLoader fxmlLoader) throws Exception {
        this.score = score;
        this.presentationFactory = presentationFactory;
        this.fxmlLoader = fxmlLoader;
        this.presentation = presentation;
    }

    @FXML
    public void startQuiz(ActionEvent event) {

        presentation.show();
        presentation.getScene().setCursor(Cursor.NONE);
        this.view = presentation.getView();
    }

    @FXML
    public void toggleFullScreen(ActionEvent event) {
        if (fullScreenToggleBtn.isSelected()) {
            presentation.getStage().setFullScreenExitHint("");
            presentation.getStage().setFullScreen(true);
        }
        else {
            presentation.getStage().setFullScreen(false);
        }
    }
    @FXML
    public void openMedia(MouseEvent event) throws Exception {

        FXMLLoader loader = this.fxmlLoader;
        loader.setLocation(getClass().getResource("../Dashboard/media-player.fxml"));
        AnchorPane mediaPlayerPane = loader.load();
        dashboard.getScene().getStylesheets().add(getClass().getResource("/Stylesheets/media.css").toExternalForm());
        dashboard.getChildren().add(mediaPlayerPane);
        }

    public void showScore() {

        this.score.setScore(100);
        HomeView view = (HomeView)this.view;
        view.updateScore(this.score.getScore());
    }
}
