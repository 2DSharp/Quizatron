package me.twodee.quizatron.Console.Panel;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXToggleButton;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import me.twodee.quizatron.Component.Presentation;
import me.twodee.quizatron.Model.Entity.Configuration.Configuration;
import me.twodee.quizatron.Model.Service.StateService;
import me.twodee.quizatron.Presentation.IView;
import me.twodee.quizatron.Model.Score;
import me.twodee.quizatron.Presentation.View.HomeView;

import javax.inject.Inject;
import javafx.event.ActionEvent;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;

public class PanelPresenter {

    private Score score;
    private Presentation presentation;
    private IView view;
    private FXMLLoader fxmlLoader;
    private StateService stateService;
    @FXML private JFXToggleButton fullScreenToggleBtn;
    @FXML private AnchorPane rootNode;
    @FXML private AnchorPane dashboard;
    @FXML private JFXTextField configFileLbl;
    @FXML private Label loadedQuizNameLbl;
    @FXML private JFXButton startBtn;

    @Inject
    public PanelPresenter(Score score,
                          Presentation presentation,
                          FXMLLoader fxmlLoader,
                          StateService stateService) throws Exception {
        this.score = score;
        this.fxmlLoader = fxmlLoader;
        this.presentation = presentation;
        this.stateService = stateService;

    }

    @FXML
    private void saveState(MouseEvent event) {
        try {
            String location = stateService.saveState();
            System.out.println("State saved to: " + location);
        }

        catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void openConfigChooser(ActionEvent event)  {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open quiz configuration file");

        if (stateService.stateLoaded()) {
            fileChooser.setInitialDirectory(stateService.getInitialDirectory().toFile());
        }
        try {
            Path file = fileChooser.showOpenDialog(rootNode.getScene().getWindow()).toPath();

            String source = file.toUri().toURL().toExternalForm();
            configFileLbl.setText(source);

            stateService.setConfigurationPath(file);
            stateService.loadState();

            Configuration configuration = stateService.getConfiguration();
            loadedQuizNameLbl.setText(configuration.getName());
            startBtn.setDisable(false);
        }
        catch (NullPointerException e) {
            System.out.println("No file chosen");
        }
        catch (MalformedURLException e) {
            System.out.println("The choosen file URL is not valid");
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void startQuiz(ActionEvent event) {

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
