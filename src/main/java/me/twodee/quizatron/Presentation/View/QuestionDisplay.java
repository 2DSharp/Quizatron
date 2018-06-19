package me.twodee.quizatron.Presentation.View;

import javafx.animation.*;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.CubicCurveTo;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.text.Font;
import javafx.util.Duration;
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
    private QuizDataService quizDataService;
    private String logo;
    private String background;
    private ImageView imageView;
    public enum Result
    {
        CORRECT, WRONG;
    }
    private static final String USER_AGENT_STYLESHEET = QuestionDisplay.class.getResource("/Stylesheets/display-board.css").toExternalForm();
    @Inject
    public QuestionDisplay(QuizDataService quizDataService) throws MalformedURLException
    {
        this.quizDataService = quizDataService;
    }

    public void initialize() throws MalformedURLException
    {
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
        generateBg();
    }

    private void generateBg() throws MalformedURLException
    {
        leftBar.setStyle("-fx-background-color:" + getBackground(quizDataService));
        leftBar.setAlignment(Pos.BOTTOM_CENTER);
        leftBar.getChildren().add(getLogoView());
    }

    private ImageView getLogoView() throws MalformedURLException
    {
        Image logo = new Image(getLogo(quizDataService));
        ImageView logoView = new ImageView(logo);
        logoView.setFitWidth(150);
        logoView.setPreserveRatio(true);
        return logoView;
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
    public void setTitle(String title)
    {
        titleLbl.setStyle("-fx-font-size: " + computeFontSize(title));
        titleLbl.setText(title);
    }

    public void setLeftBar(Color color)
    {

    }
    private void zoomIn(Node node)
    {
        ScaleTransition st = new ScaleTransition(Duration.millis(1000), node);
        st.setFromX(0.1f);
        st.setToX(1.0f);
        st.setFromY(0.1f);
        st.setToY(1.0f);

        st.play();
    }

    public void revealAnswer(String answer, Result result)
    {
        cleanImageView();
        setTitle(answer);
        fadeIn(titleLbl);
        signal(result);
        titleContainer.getChildren().remove(imageView);
    }

    private void cleanImageView()
    {

        titleContainer.getChildren().remove(imageView);
    }
    private void signal(Result result)
    {
        final Animation animation = new Transition() {
            {
                setCycleDuration(Duration.millis(2000));
                setInterpolator(Interpolator.EASE_OUT);
            }

            @Override
            protected void interpolate(double frac) {
                Color vColor;
                if (result == Result.WRONG) {
                    vColor = new Color(1, 0, 0, 1 - frac);
                }
                else {
                    vColor = new Color(0, 1, 0, 1 - frac);
                }
                root.setBackground(new Background(new BackgroundFill(vColor, CornerRadii.EMPTY, Insets.EMPTY)));
            }
        };
        animation.play();
    }
    public void revealQuestion(String question)
    {
        cleanImageView();
        setTitle(question);
        fadeIn(titleLbl);
    }

    public void revealQuestion(String question, String qImage)
    {
        cleanImageView();
        setTitle(question);
        setQImage(qImage);

        fadeIn(titleLbl);
        fadeIn(imageView);
    }


    private void setQImage(String url)
    {
        Image image = new Image(url);
        imageView = prepareImageView(image);
        titleContainer.getChildren().add(imageView);
        fadeIn(imageView);
        titleLbl.setStyle("-fx-font-size: " + 28);
    }

    private ImageView prepareImageView(Image image)
    {
        ImageView imageView = new ImageView(image);
        imageView.setStyle("-fx-border-color: #000; -fx-border-width: 5px");
        imageView.setFitHeight(500);
        imageView.setFitWidth(950);
        imageView.setPreserveRatio(true);

        return imageView;
    }
    private void fadeIn(Node node)
    {
        FadeTransition ft = new FadeTransition(Duration.millis(3000), node);
        ft.setFromValue(0.0);
        ft.setToValue(1.0);
        ft.setCycleCount(1);
        ft.play();
    }
    private int computeFontSize(String text)
    {
        int size = 44;
        int textLength = text.length();
        return size - getDecrement(textLength, 4, 100, 200, 300, 350);
    }

    private int getDecrement(int length, int factor, int... breakPoints)
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