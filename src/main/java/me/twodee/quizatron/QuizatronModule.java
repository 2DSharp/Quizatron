package me.twodee.quizatron;

import com.google.inject.AbstractModule;
import javafx.fxml.FXMLLoader;
import me.twodee.quizatron.Component.State.ObjectSerializationIOStrategy;
import me.twodee.quizatron.Component.State.SerializationIOStrategy;
import me.twodee.quizatron.Factory.FXMLLoaderProvider;
import me.twodee.quizatron.Model.Score;
import me.twodee.quizatron.Component.Presentation;
import me.twodee.quizatron.Factory.PresentationProvider;

import javax.inject.Singleton;
import java.util.HashMap;
import java.util.Map;

public class QuizatronModule extends AbstractModule {

    @Override
    protected void configure() {

        bind(Score.class);
        bind(FXMLLoader.class).toProvider(FXMLLoaderProvider.class);
        bind(Presentation.class).toProvider(PresentationProvider.class).in(Singleton.class);
        bind(SerializationIOStrategy.class).to(ObjectSerializationIOStrategy.class);
        bind(Map.class).to(HashMap.class);
    }
}
