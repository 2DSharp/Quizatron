package me.twodee.quizatron.Presentation;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javax.inject.Inject;
import javax.inject.Provider;

public class PresentationProvider implements Provider<Presentation> {

    private FXMLLoader fxmlLoader;
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

    private Parent getRoot(FXMLLoader loader) throws Exception {

        return loader.load();
    }
    private FXMLLoader buildDefaultLoader() {

        fxmlLoader.setLocation(getClass().getResource("View/home.fxml" ));
        return fxmlLoader;
    }
}
