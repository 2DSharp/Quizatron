package me.twodee.quizatron.Presentation.View;

import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.util.Duration;
import me.twodee.quizatron.Model.Entity.Question;
import me.twodee.quizatron.Model.Service.QuizDataService;
import me.twodee.quizatron.Presentation.IView;

import javax.inject.Inject;
import java.net.MalformedURLException;

public class QuestionDisplay extends IView
{
    @FXML
    private Label titleLbl;
    @FXML
    private VBox leftBar;

    @FXML
    BorderPane root;
    @FXML
    private  VBox titleContainer;

    private String logo;
    private String background;

    private static final String USER_AGENT_STYLESHEET = QuestionDisplay.class.getResource("/Stylesheets/display-board.css").toExternalForm();
    @Inject
    public QuestionDisplay(QuizDataService quizDataService) throws MalformedURLException
    {

        this.background = getBackground(quizDataService);
        this.logo = getLogo(quizDataService);
    }

    private String getLogo(QuizDataService quizDataService) throws MalformedURLException
    {
        return quizDataService.getInitialDirectory().toUri().toURL().toExternalForm() +
                quizDataService.getConfiguration().getAppearance().getLogo();
    }
    private String getBackground(QuizDataService quizDataService)
    {
        return quizDataService.getConfiguration().getAppearance().getThemeColor();
    }
    public void initialize() {
        root.getStylesheets().add(USER_AGENT_STYLESHEET);
        /*
        Image img = new Image(background);
        BackgroundImage bgImg = new BackgroundImage(img,
                                                    BackgroundRepeat.NO_REPEAT,
                                                    BackgroundRepeat.NO_REPEAT,
                                                    BackgroundPosition.DEFAULT,
                                                    BackgroundSize.DEFAULT);
        leftBar.setBackground(new Background(bgImg));
        */
        leftBar.setStyle("-fx-background-color:" + background );
        leftBar.setAlignment(Pos.BOTTOM_CENTER);
        Image logo = new Image(this.logo);
        ImageView logoView = new ImageView(logo);
        logoView.setFitWidth(150);
        logoView.setPreserveRatio(true);
        leftBar.getChildren().add(logoView);
        System.out.println(background);
    }

    public void revealQuestion(String question)
    {
        setTitle(question);
        fadeIn(titleLbl);
    }
    private void fadeIn(Node node) {
        FadeTransition ft = new FadeTransition(Duration.millis(3000), node);
        ft.setFromValue(0.0);
        ft.setToValue(1.0);
        ft.setCycleCount(1);
        ft.play();
    }
    public void setTitle(String title)
    {
        System.out.println(computeFontSize(title));
        titleLbl.setFont(Font.font(computeFontSize(title)));
        titleLbl.setText(title);
    }

    public void setLeftBar(Color color)
    {

    }
    private int computeFontSize(String text) 
    {
        int size = 44;
        int textLength = text.length();
        return size - foo(textLength, 4, 100, 200, 300, 350);
    }

    private int foo(int length, int factor, int... breakPoints)
    {
        int total = 0;

        for (int item : breakPoints) {
            if (length > item) {
                total += factor;
            }
        }
        return total;
    }
}