package me.twodee.quizatron.Presentation;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import me.twodee.quizatron.Factory.FXMLLoaderProvider;
import me.twodee.quizatron.Presentation.IView;

import java.io.IOException;

public class Presentation
{
    private Stage stage;
    private Scene scene;
    private IView view;
    private FXMLLoaderProvider FXMLLoaderProvider;

    public Presentation(Stage stage, Scene scene, IView view, FXMLLoaderProvider FXMLLoaderProvider)
    {
        this.stage = stage;
        this.scene = scene;
        this.view = view;
        this.FXMLLoaderProvider = FXMLLoaderProvider;
    }

    public Stage getStage()
    {
        return stage;
    }

    public Scene getScene()
    {
        return scene;
    }

    public void show()
    {
        this.stage.show();
    }

    public <T> T getView()
    {
        return (T) view;
    }

    public void changeView(String viewFile) throws IOException
    {
        FXMLLoader loader = FXMLLoaderProvider.get();
        loader.setLocation(getClass().getResource("View/" + viewFile + ".fxml"));
        scene.setRoot(loader.load());
        view = loader.getController();
    }
}
