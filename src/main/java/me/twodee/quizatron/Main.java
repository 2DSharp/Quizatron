package me.twodee.quizatron;
/**
 * Quizatron the quiz control panel
 * @author Dedipyaman Das <2d@twodee.me>
 */

import com.google.inject.Guice;
import com.google.inject.Injector;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.logging.Logger;

public class Main extends Application {

    private static final Logger log = Logger.getLogger(Main.class.getName());

    @Override
    public void start(Stage primaryStage) throws Exception{


        final Injector injector = Guice.createInjector(new QuizatronModule());
        FXMLLoader loader =  new FXMLLoader(getClass().getResource("Console/console.fxml"));
        loader.setControllerFactory(injector::getInstance);

        Parent root = loader.load();

        Scene scene = new Scene(root);
        primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/img/qtron64x64.png")));
        loadFont("/fonts/OpenSans-Light.ttf");
        loadFont("/fonts/OpenSans-Regular.ttf");

        primaryStage.setTitle("Quizatron console");
        primaryStage.setScene(scene);
        primaryStage.setMaximized(true);
        primaryStage.show();
    }

    private void loadFont(String path) {
        try {
            Font.loadFont(getClass().getResource(path).toExternalForm(), 24);
        } catch (Exception e) {
            log.warning("Cannot load: " + path);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
