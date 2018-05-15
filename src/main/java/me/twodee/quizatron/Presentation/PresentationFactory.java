package me.twodee.quizatron.Presentation;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javax.inject.Inject;

public class PresentationFactory {

    private FXMLLoader fxmlLoader;

    @Inject
    public PresentationFactory(FXMLLoader fxmlLoader) {
        this.fxmlLoader = fxmlLoader;
    }
    public Presentation create() throws Exception {

        FXMLLoader fxmlLoader = this.loaderBuilder("home");

        Parent root = this.getRoot(fxmlLoader);
        IView view = this.getView(fxmlLoader);

        Scene scene = new Scene(root, 800, 600);

        Stage stage = new Stage();
        stage.setMaximized(true);
        stage.setScene(scene);

        Presentation presentation = new Presentation(stage, scene, view, fxmlLoader);
        return presentation;
    }

    public Presentation create(Stage stage, Scene scene, String viewFile) throws Exception{

        FXMLLoader fxmlLoader = this.loaderBuilder(viewFile);

        Parent root = this.getRoot(fxmlLoader);
        IView view = this.getView(fxmlLoader);
        scene.setRoot(root);

        return new Presentation(stage, scene, view, fxmlLoader);
    }

    private FXMLLoader loaderBuilder(String viewFile) throws Exception {

        fxmlLoader.setLocation(getClass().getResource("View/" + viewFile + ".fxml" ));
        return fxmlLoader;
    }

    private IView getView(FXMLLoader loader) throws Exception {

        return loader.getController();
    }
    private Parent getRoot(FXMLLoader loader) throws Exception {

        return loader.load();
    }
}
