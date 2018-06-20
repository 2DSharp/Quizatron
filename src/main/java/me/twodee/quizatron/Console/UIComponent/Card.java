package me.twodee.quizatron.Console.UIComponent;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import me.twodee.quizatron.Component.UIComponent;

import java.io.IOException;

public class Card extends UIComponent
{
    private static final String USER_AGENT_STYLESHEET = Card.class
            .getResource("/Stylesheets/card.css")
            .toExternalForm();
    private String name;
    private String fileName;
    private String fileType;

    @FXML
    Label nameBox;
    @FXML
    Label fileNameBox;
    @FXML
    FontAwesomeIconView icon;

    public Card(String name, String fileName, String fileType) throws IOException
    {
        this.name = name;
        this.fileName = fileName;
        this.fileType = fileType;

        FXMLLoader fxmlLoader = initFXML("card.fxml");
        fxmlLoader.load();
    }

    public void initialize()
    {
        this.getStylesheets().add(USER_AGENT_STYLESHEET);
        nameBox.setText(name);
        fileNameBox.setText(fileName);

        FontAwesomeIcon target;
        switch (fileType.toLowerCase()) {
            case "video" :
                target = FontAwesomeIcon.PLAY_CIRCLE_ALT;
                break;
            case "round" :
                target = FontAwesomeIcon.BOLT;
                break;
            default: target = FontAwesomeIcon.CODEPEN;
        }
        icon.setIcon(target);
    }
}
