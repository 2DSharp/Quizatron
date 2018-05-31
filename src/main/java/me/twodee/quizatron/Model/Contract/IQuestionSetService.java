package me.twodee.quizatron.Model.Contract;

import me.twodee.quizatron.Model.Exception.NoQuestionLeftException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;

public interface IQuestionSetService {

    void loadSet(Path file) throws IOException, NoQuestionLeftException;
    //IQuestion getQuestion(int index);
    IQuestion nextQuestion() throws NoQuestionLeftException;
}
