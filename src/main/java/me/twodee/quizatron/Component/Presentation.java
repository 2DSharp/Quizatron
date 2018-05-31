package me.twodee.quizatron.Component;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import me.twodee.quizatron.Factory.FXMLLoaderProvider;
import me.twodee.quizatron.Presentation.IView;

public class Presentation {

    private Stage stage;
    private Scene scene;
    private IView view;
    private FXMLLoaderProvider FXMLLoaderProvider;

    public Presentation(Stage stage, Scene scene, IView view, FXMLLoaderProvider FXMLLoaderProvider) {
        this.stage = stage;
        this.scene = scene;
        this.view = view;
        this.FXMLLoaderProvider = FXMLLoaderProvider;
    }

    public Stage getStage() {
        return stage;
    }

    public Scene getScene() {
        return scene;
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

        return view;
    }

    public void changeView(String viewFile) throws Exception {

        //loader = new FXMLLoader(getClass().getResource("View/" + viewFile + ".fxml" ));
        FXMLLoader loader = FXMLLoaderProvider.get();
        loader.setLocation(getClass().getResource("../Presentation/View/" + viewFile + ".fxml" ));
        scene.setRoot(loader.load());
        view = loader.getController();
    }
}
