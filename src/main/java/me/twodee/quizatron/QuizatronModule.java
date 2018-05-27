package me.twodee.quizatron;

import com.google.inject.AbstractModule;
import com.google.inject.Injector;
import com.google.inject.Provides;
import com.google.inject.name.Names;
import javafx.fxml.FXMLLoader;
import me.twodee.quizatron.Component.State.ObjectSerializationIOStrategy;
import me.twodee.quizatron.Component.State.SerializationIOStrategy;
import me.twodee.quizatron.Component.State.State;
import me.twodee.quizatron.Model.Score;
import me.twodee.quizatron.Component.Presentation.Presentation;
import me.twodee.quizatron.Factory.PresentationProvider;

import javax.inject.Singleton;
import java.util.HashMap;
import java.util.Map;

public class QuizatronModule extends AbstractModule {

    @Override
    protected void configure() {

        bind(Score.class);
        bind(Presentation.class).toProvider(PresentationProvider.class).in(Singleton.class);
        bind(SerializationIOStrategy.class).to(ObjectSerializationIOStrategy.class);
        bind(Map.class).to(HashMap.class);
    }

    @Provides
    public FXMLLoader getFXMLLoader(Injector injector) {

        FXMLLoader loader = new FXMLLoader();
        loader.setControllerFactory(injector::getInstance);
        return loader;
    }
}
