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
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{


        final Injector injector = Guice.createInjector(new QuizatronModule());
        FXMLLoader loader =  new FXMLLoader(getClass().getResource("Console/console.fxml"));
        loader.setControllerFactory(injector::getInstance);

        Parent root = loader.load();

        Scene scene = new Scene(root);
        primaryStage.setTitle("Quizatron console");
        primaryStage.setScene(scene);
        primaryStage.show();













        /*
        Score score = new Score();

        Map<Object, Object> customProperties = new HashMap<>();

        customProperties.put("score", score);
        System.out.println(dashboardView.getView().toString());
        primaryStage.setTitle("Quizatron console");
        primaryStage.setScene(scene);
        primaryStage.show();

        /*
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("me/twodee/quizatron/Console/Dashboard/console.fxml"));

        primaryStage.setTitle("Quizatron Console");


        primaryStage.setScene(scene);

        primaryStage.show();

        /*
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        File file = fileChooser.showOpenDialog(primaryStage);

        // Create the media source.
        String source = file.toURI().toURL().toExternalForm();
        MediaPresentationView media = new MediaPresentationView(source);

        // Create the player and set to play automatically.
        MediaPlayerPresenter mediaPlayer = new MediaPlayerPresenter(media);
        mediaPlayer.setAutoPlay(true);

        // Create the view and add it to the Scene.
        MediaPresentationView mediaView = new MediaPresentationView(mediaPlayer);

        DoubleProperty mvw = mediaView.fitWidthProperty();
        DoubleProperty mvh = mediaView.fitHeightProperty();
        mvw.bind(Bindings.selectDouble(mediaView.sceneProperty(), "width"));
        mvh.bind(Bindings.selectDouble(mediaView.sceneProperty(), "height"));


        ((Group) outputScene.getRoot()).getChildren().add(mediaView);

        outputStage.setScene(outputScene);
        */

    }


    public static void main(String[] args) {
        launch(args);
    }
}
