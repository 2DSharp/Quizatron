package me.twodee.quizatron.Model.Contract;

import me.twodee.quizatron.Model.Exception.NoQuestionLeftException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;

public interface IQuestionSetService {

    //IQuestion fetchQuestion(int index);
    IQuestion nextQuestion() throws NoQuestionLeftException, MalformedURLException;
}
