package me.twodee.quizatron;

import com.google.inject.AbstractModule;
import com.google.inject.Stage;
import me.twodee.quizatron.Model.Person;
import me.twodee.quizatron.Model.Score;

public class QuizatronModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(Score.class);
        bind(Person.class);
    }
}
