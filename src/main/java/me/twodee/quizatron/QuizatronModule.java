package me.twodee.quizatron;

import com.google.inject.*;
import javafx.fxml.FXMLLoader;
import me.twodee.quizatron.Model.Score;

public class QuizatronModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(Score.class);
    }

    @Provides
    public FXMLLoader getFXMLLoader(Injector injector) {

        FXMLLoader loader = new FXMLLoader();
        loader.setControllerFactory(injector::getInstance);
        return loader;
    }
}
