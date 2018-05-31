package me.twodee.quizatron.Console;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXToggleButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import me.twodee.quizatron.Component.Mediator;
import me.twodee.quizatron.Component.Presentation;
import me.twodee.quizatron.Console.View.ConfigLoaderView;
import me.twodee.quizatron.Model.Entity.Configuration.Appearance;
import me.twodee.quizatron.Model.Service.QuizDataService;
import me.twodee.quizatron.Presentation.IView;
import me.twodee.quizatron.Presentation.View.HomeView;

import javax.inject.Inject;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class PanelPresenter {

    private Presentation presentation;
    private IView view;
    private FXMLLoader fxmlLoader;
    private Mediator mediator;
    private QuizDataService quizDataService;

    @FXML
    private JFXToggleButton fullScreenToggleBtn;
    @FXML
    private AnchorPane rootNode;
    @FXML
    private AnchorPane dashboard;
    @FXML
    private JFXTextField configFileLbl;
    @FXML
    private Label loadedQuizNameLbl;
    @FXML
    private JFXButton startBtn;


    @Inject
    public PanelPresenter(Presentation presentation,
                          FXMLLoader fxmlLoader,
                          Mediator mediator,
                          QuizDataService quizDataService) throws Exception {

        this.fxmlLoader = fxmlLoader;
        this.presentation = presentation;
        this.mediator = mediator;
        this.quizDataService = quizDataService;
    }

    @FXML
    public void openMediaTabAction(MouseEvent event) throws Exception {

        FXMLLoader loader = this.fxmlLoader;
        loader.setLocation(getClass().getResource("Dashboard/media-player.fxml"));
        AnchorPane mediaPlayerPane = loader.load();
        dashboard.getScene().getStylesheets().add(getClass().getResource("/Stylesheets/media.css").toExternalForm());
        dashboard.getChildren().add(mediaPlayerPane);
    }


    @FXML
    private void loadSavedState(MouseEvent event) {

        Path file = getFile("Open quiz saved file");
        mediator.request(() -> loadStateFromFile(file));
        loadFeedBack();
    }

    private Path getFile(String title) {

        FileChooser fileChooser = new FileChooser();

        if (quizDataService.quizDataLoaded()) {

            Path homePath = quizDataService.getInitialDirectory();
            fileChooser.setInitialDirectory(homePath.toFile());
        }

        fileChooser.setTitle(title);
        Path file = fileChooser.showOpenDialog(rootNode.getScene().getWindow()).toPath();
        return file;
    }

    private void loadStateFromFile(Path file) {

        try {
            quizDataService.loadSavedData(file);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void loadFeedBack() {

        ConfigLoaderView configLoaderView = new ConfigLoaderView(mediator, quizDataService);
        configLoaderView.setOutput(loadedQuizNameLbl, startBtn);
        mediator.respond(configLoaderView);
    }

    @FXML
    private void saveStateAction(MouseEvent event) {

        mediator.request(this::saveStateToFile);
    }

    @FXML
    private void loadConfigTxtAction(ActionEvent event) {

        String location = configFileLbl.getText();
        Path file = Paths.get(location);
        loadFromConfigFile(file);
        loadFeedBack();
    }

    private void loadFromConfigFile(Path file) {

        mediator.request(() -> loadConfig(file));
    }

    private void loadConfig(Path file) {

        try {

            quizDataService.loadConfig(file);
        }
        catch (FileNotFoundException e) {

            mediator.setError("The file you entered couldn't be found");
        }
    }

    @FXML
    private void loadConfigBtnAction(ActionEvent event) {

        try {

            Path file = getFile("Open quiz configuration file");
            loadFromConfigFile(file);
            loadFeedBack();

            String source = file.toAbsolutePath().toString();
            configFileLbl.setText(source);
        }
        catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void startQuizAction(ActionEvent event) {

        try {
            presentation.show();
            presentation.getScene().setCursor(Cursor.NONE);
            HomeView view = (HomeView) presentation.getView();
            Appearance appearance = quizDataService.getConfiguration().getAppearance();
            String bgImage = makeAbsURL(appearance.getDefaultBackground());
            String logo = makeAbsURL(appearance.getLogo());

            view.setLogo(logo);
            view.setBackground(bgImage);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String makeAbsURL(String url) throws MalformedURLException {

        String file = quizDataService.getInitialDirectory() + "/" + url;
        Path filePath = Paths.get(file);
        return filePath.toUri().toURL().toString();
    }

    @FXML
    public void toggleFullScreen(ActionEvent event) {

        if (fullScreenToggleBtn.isSelected()) {

            presentation.getStage().setFullScreenExitHint("");
            presentation.getStage().setFullScreen(true);
        } else {
            presentation.getStage().setFullScreen(false);
        }
    }

    private void saveStateToFile() {

        try {
            quizDataService.saveData();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
