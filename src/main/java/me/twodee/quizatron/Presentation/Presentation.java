package me.twodee.quizatron.Presentation;

import com.google.inject.Guice;
import com.google.inject.Injector;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import me.twodee.quizatron.Presentation.View.HomeView;
import me.twodee.quizatron.Presentation.View.MediaPresentationView;

import javax.inject.Inject;

public class Presentation {

    private Stage stage;
    private Scene scene;
    private FXMLLoader loader;
    private IView view;
    private LoaderFactory loaderFactory;

    public Presentation(Stage stage, Scene scene, IView view, LoaderFactory loaderFactory) {
        this.stage = stage;
        this.scene = scene;
        this.view = view;
        this.loaderFactory = loaderFactory;
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

        return view;
    }

    public void changeView(String viewFile) throws Exception {

        //loader = new FXMLLoader(getClass().getResource("View/" + viewFile + ".fxml" ));
        loader = loaderFactory.build(viewFile);
        scene.setRoot(loader.load());
        view = loader.getController();
    }
}
