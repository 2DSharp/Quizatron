package me.twodee.quizatron.Factory;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import me.twodee.quizatron.Component.Presentation;

import javax.inject.Inject;
import javax.inject.Provider;

public class PresentationProvider implements Provider<Presentation> {

    private Stage stage;
    private FXMLLoaderProvider FXMLLoaderProvider;

    @Inject
    public PresentationProvider(Stage stage, FXMLLoaderProvider FXMLLoaderProvider) {

        this.stage = stage;
        this.FXMLLoaderProvider = FXMLLoaderProvider;
    }

    public Presentation get() {

        try {
            FXMLLoader fxmlLoader = FXMLLoaderProvider.get();
            fxmlLoader.setLocation(getClass().getResource("../Presentation/View/home.fxml" ));

            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root, 800, 600);

            stage.setMaximized(true);
            stage.setScene(scene);

            return new Presentation(stage, scene, fxmlLoader.getController(), FXMLLoaderProvider);
        }

        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
