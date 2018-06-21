package me.twodee.quizatron.Presentation.View;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import me.twodee.quizatron.Model.Entity.Configuration.Appearance;
import me.twodee.quizatron.Model.Service.QuizDataService;
import me.twodee.quizatron.Presentation.IView;

import javax.inject.Inject;
import java.net.MalformedURLException;

public class Default extends IView {

    @FXML
    BorderPane body;

    @FXML
    ImageView logoBox;
    private QuizDataService quizDataService;

    @Inject
    public Default(QuizDataService quizDataService) {
        this.quizDataService = quizDataService;
    }
    public void initialize() throws MalformedURLException
    {
        decorate();
    }

    private void decorate() throws MalformedURLException
    {
        Appearance appearance = quizDataService.getConfiguration().getAppearance();
        String bgImage = quizDataService.constructURL(appearance.getDefaultBackground());
        String logo = quizDataService.constructURL(appearance.getLogo());

        setLogo(logo);
        setBackground(bgImage);
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
