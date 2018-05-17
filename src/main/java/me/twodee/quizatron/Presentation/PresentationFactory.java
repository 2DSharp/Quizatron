package me.twodee.quizatron.Presentation;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javax.inject.Inject;
import javax.inject.Provider;

public class PresentationFactory implements Provider<Presentation> {

    private FXMLLoader fxmlLoader;
    private Stage stage;

    @Inject
    public PresentationFactory(Stage stage, FXMLLoader fxmlLoader) {

        this.stage = stage;
        this.fxmlLoader = fxmlLoader;
    }

    public Presentation get() {

        try {

            FXMLLoader fxmlLoader = this.buildDefaultLoader();

            Parent root = this.getRoot(fxmlLoader);
            Scene scene = new Scene(root, 800, 600);

            stage.setMaximized(true);
            stage.setScene(scene);

            return new Presentation(stage, scene, fxmlLoader);
        }

        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private Parent getRoot(FXMLLoader loader) throws Exception {

        return loader.load();
    }
    private FXMLLoader buildDefaultLoader() {

        fxmlLoader.setLocation(getClass().getResource("View/home.fxml" ));
        return fxmlLoader;
    }
}
