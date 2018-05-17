package me.twodee.quizatron.Presentation;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javax.inject.Inject;

public class Presentation {

    private Stage stage;
    private Scene scene;
    private FXMLLoader loader;

    public Presentation(Stage stage, Scene scene, FXMLLoader loader) {
        this.stage = stage;
        this.scene = scene;
        this.loader = loader;
    }

    public Stage getStage() {
        return stage;
    }

    public Scene getScene() {
        return scene;
    }

    public FXMLLoader getLoader() {
        return loader;
    }

    public void show() {

        this.stage.show();
    }
    /**
    public IView getView() {

        return view;
    }
     */
    public IView getView() {

        return loader.getController();
    }

    public void changeView(String viewFile) throws Exception {

        loader = new FXMLLoader(getClass().getResource("View/" + viewFile + ".fxml" ));
        scene.setRoot(loader.load());
    }
}
