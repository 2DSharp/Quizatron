package me.twodee.quizatron.Presentation.View;

import javafx.animation.*;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
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

    private String getColor()
    {
        return quizDataService.getConfiguration().getAppearance().getTextColor();
    }

    private void generateBg() throws MalformedURLException
    {
        leftBar.setStyle("-fx-background-color:" + getLeftBg(quizDataService));
        leftBar.setAlignment(Pos.BOTTOM_CENTER);
        leftBar.getChildren().add(getLogoView());
        root.setStyle("-fx-background-color: " + getBackground());
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
    private String getLeftBg(QuizDataService quizDataService)
    {
        return quizDataService.getConfiguration().getAppearance().getTextColor();
    }

    private String getBackground()
    {
        return quizDataService.getConfiguration().getAppearance().getThemeBgColor();
    }
    public void setTitle(String title)
    {
        titleLbl.setStyle("-fx-font-size: " + computeFontSize(title) + ";" + "-fx-text-fill: " + getColor());
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
        revealQuestion(answer);
        flash(result);
    }

    public void revealAnswer(String answer, String url, Result result)
    {
        revealQuestion(answer, url);
        flash(result);
    }
    private void cleanImageView()
    {
        titleContainer.getChildren().remove(imageView);
    }
    private void flash(Result result)
    {
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0.2),
                                                      evt -> root.setStyle("-fx-background-color: " + getFlashColor(result))),
                                         new KeyFrame(Duration.seconds(1),
                                                      evt -> root.setStyle("-fx-background-color: " + getBackground())));
        timeline.play();
    }

    private String getFlashColor(Result result)
    {
        String color;
        switch (result) {
            case WRONG:
                color = "#F25056";
            break;
            case CORRECT:
                color = "#39EA49";
            break;
            default:
                color = getColor();
        }
        return color;
    }

    private void somethingElse(Result result)
    {
        final Animation animation = new Transition()
        {
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
        titleLbl.setStyle("-fx-font-size: 28; -fx-text-fill: " + getColor());
    }

    private ImageView prepareImageView(Image image)
    {
        DropShadow ds = new DropShadow(20, Color.BLACK );
        ImageView imageView = new ImageView(image);
        imageView.setEffect(ds);
        imageView.setFitHeight(500);
        imageView.setFitWidth(1000);
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