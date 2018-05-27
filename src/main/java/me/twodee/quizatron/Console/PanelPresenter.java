package me.twodee.quizatron.Console;

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
import me.twodee.quizatron.Component.Presentation.Presentation;
import me.twodee.quizatron.Component.State.State;
import me.twodee.quizatron.Model.Entity.Configuration.Configuration;
import me.twodee.quizatron.Model.Service.ConfigurationManager;
import me.twodee.quizatron.Model.Service.StateService;
import me.twodee.quizatron.Presentation.IView;
import me.twodee.quizatron.Model.Score;
import me.twodee.quizatron.Presentation.View.HomeView;

import javax.inject.Inject;
import javafx.event.ActionEvent;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.prefs.Preferences;

public class PanelPresenter {

    private enum LoaderType {
        SAVE_FILE, CONFIG_FILE
    }
    private Configuration configuration;

    private Score score;
    private Presentation presentation;
    private IView view;
    private FXMLLoader fxmlLoader;
    private StateService stateService;
    private FileChooser fileChooser;
    private Preferences prefs;
    private ConfigurationManager configurationManager;
    private State state;

    @FXML private JFXToggleButton fullScreenToggleBtn;
    @FXML private AnchorPane rootNode;
    @FXML private AnchorPane dashboard;
    @FXML private JFXTextField configFileLbl;
    @FXML private Label loadedQuizNameLbl;
    @FXML private JFXButton startBtn;


    @Inject
    public PanelPresenter(Presentation presentation,
                          FXMLLoader fxmlLoader,
                          ConfigurationManager configurationManager,
                          State state) throws Exception {

        this.configurationManager = configurationManager;
        this.fxmlLoader = fxmlLoader;
        this.presentation = presentation;
        this.state = state;

    }
    @FXML
    private void loadSavedState(MouseEvent event) {
        try {
            Path file = getFile("Open quiz saved file");
            state.load(file);
            loadFeedback();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadConfigToState(Path file) throws FileNotFoundException {

        Configuration configuration = configurationManager.loadConfiguration(file);
        state.set("configuration", configuration);
        state.set("homedir", file.getParent().toAbsolutePath().toString());

    }
    @FXML
    private void importConfigFile(ActionEvent event) {
        try {
            String location = configFileLbl.getText();
            Path file = Paths.get(location);
            loadConfigToState(file);
            loadFeedback();
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void openConfigChooser(ActionEvent event)  {
        try {
            Path file = getFile("Open quiz configuration file");
            //loadFeedback(LoaderType.CONFIG_FILE, file);
            loadConfigToState(file);

            String source = file.toAbsolutePath().toString();
            configFileLbl.setText(source);

            loadFeedback();
        }
        catch (NullPointerException e) {
            System.out.println("No file chosen");
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadFeedback()  {

        Configuration configuration = state.get("configuration");

        String homeDir = state.get("homedir");


        loadedQuizNameLbl.setText(configuration.getName());

        startBtn.setDisable(false);
    }

    private Path getFile(String title) {
        FileChooser fileChooser = new FileChooser();

        if (state.has("homedir")) {
            String homeDir = state.get("homedir");
            Path homePath = Paths.get(homeDir);
            fileChooser.setInitialDirectory(homePath.toFile());
        }

        fileChooser.setTitle(title);
        Path file = fileChooser.showOpenDialog(rootNode.getScene().getWindow()).toPath();
        return file;
    }

    @FXML
    private void saveState(MouseEvent event) {
        try {

            String homeDir = state.get("homedir");
            String fileStr = homeDir + "/save/SaveData.2D";
            Path filePath = Paths.get(fileStr);
            state.save(filePath);
        }

        catch (IOException e) {
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
