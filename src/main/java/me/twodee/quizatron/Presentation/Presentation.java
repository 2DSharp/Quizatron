package me.twodee.quizatron.Presentation;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import me.twodee.quizatron.Presentation.View.HomeView;

public class Presentation {

    private Stage stage;
    private Scene scene;
    private IView view;
    private FXMLLoader loader;
    public Presentation(Stage stage, Scene scene, IView view, FXMLLoader loader) {
        this.stage = stage;
        this.scene = scene;
        this.view = view;
        this.loader = loader;
    }
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public Stage getStage() {
        return stage;
    }

    public void setScene(Scene scene) {
        this.scene = scene;
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
    public IView getView() {
        return view;
    }


}
