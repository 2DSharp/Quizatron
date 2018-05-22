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
    private LoaderFactory loaderFactory;

    @Inject
    public PresentationProvider(Stage stage, LoaderFactory loaderFactory) {

        this.stage = stage;
        this.loaderFactory = loaderFactory;
    }

    public Presentation get() {
        try {
            //FXMLLoader fxmlLoader = this.buildDefaultLoader();
            FXMLLoader fxmlLoader = loaderFactory.build("home");

            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root, 800, 600);

            stage.setMaximized(true);
            stage.setScene(scene);

            return new Presentation(stage, scene, fxmlLoader.getController(), loaderFactory);
        }

        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
