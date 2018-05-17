package me.twodee.quizatron;

import com.google.inject.*;
import javafx.fxml.FXMLLoader;
import me.twodee.quizatron.Model.Score;
import me.twodee.quizatron.Presentation.Presentation;
import me.twodee.quizatron.Presentation.PresentationFactory;

public class QuizatronModule extends AbstractModule {

    @Override
    protected void configure() {

        bind(Score.class);
        bind(Presentation.class).toProvider(PresentationFactory.class).in(Singleton.class);
    }

    @Provides
    public FXMLLoader getFXMLLoader(Injector injector) {

        FXMLLoader loader = new FXMLLoader();
        loader.setControllerFactory(injector::getInstance);
        return loader;
    }
}
