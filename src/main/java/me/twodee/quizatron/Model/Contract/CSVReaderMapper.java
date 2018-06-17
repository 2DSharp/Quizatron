package me.twodee.quizatron.Model.Contract;

import me.twodee.quizatron.Model.Exception.NonExistentRecordException;

public interface CSVReaderMapper<T>
{
    void fetch(T target) throws NonExistentRecordException;
}
