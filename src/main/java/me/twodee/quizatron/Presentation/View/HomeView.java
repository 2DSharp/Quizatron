package me.twodee.quizatron.Presentation.View;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import me.twodee.quizatron.Presentation.IView;

public class HomeView extends IView {

    @FXML
    BorderPane body;

    @FXML
    ImageView logoBox;

    public HomeView() {

    }

    public void setLogo(String file) {

        Image img = new Image(file);
        logoBox.setImage(img);
    }
    public void setBackground(String file) {

        Image img = new Image(file);
        BackgroundImage backgroundImage;
        BackgroundSize bgSize = new BackgroundSize(BackgroundSize.AUTO,
                                                   BackgroundSize.AUTO,
                                                   false,
                                                   false,
                                                   true,
                                                   true);
        backgroundImage= new BackgroundImage(img,
                                             BackgroundRepeat.NO_REPEAT,
                                             BackgroundRepeat.NO_REPEAT,
                                             BackgroundPosition.DEFAULT,
                                             bgSize);

        Background background = new Background(backgroundImage);
        body.setBackground(background);
    }
}
