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

import static com.sun.javafx.scene.control.skin.Utils.getResource;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{


        final Injector injector = Guice.createInjector(new QuizatronModule());
        FXMLLoader loader =  new FXMLLoader(getClass().getResource("Console/console.fxml"));
        loader.setControllerFactory(injector::getInstance);

        Parent root = loader.load();

        Scene scene = new Scene(root);
        primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/img/qtron64x64.png")));
        Font.loadFont(
                getClass().getResource("/fonts/OpenSans-Light.ttf").toExternalForm(), 24);
        Font.loadFont(
                getClass().getResource("/fonts/OpenSans-Regular.ttf").toExternalForm(),
                24);
        primaryStage.setTitle("Quizatron console");
        primaryStage.setScene(scene);
        primaryStage.setMaximized(true);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
