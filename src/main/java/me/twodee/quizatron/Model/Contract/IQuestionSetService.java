package me.twodee.quizatron.Model.Contract;

import me.twodee.quizatron.Model.Exception.NoQuestionLeftException;
import me.twodee.quizatron.Model.Exception.NonExistentRecordException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;

public interface IQuestionSetService<T> {

    T fetch() throws NonExistentRecordException;
    T fetch(int index) throws NonExistentRecordException;
    void toStart();
    void next();
    void previous();
    boolean hasNext();
    boolean hasPrevious();
}
