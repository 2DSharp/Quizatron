package me.twodee.quizatron.Console.UIComponent;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.BorderPane;

public abstract class UIComponent extends BorderPane
{
    protected FXMLLoader initFXML(String fxml)
    {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource(fxml));
        fxmlLoader.setController(this);
        fxmlLoader.setRoot(this);
        return fxmlLoader;
    }

}
