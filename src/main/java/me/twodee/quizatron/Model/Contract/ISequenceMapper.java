package me.twodee.quizatron.Model.Contract;

import me.twodee.quizatron.Model.Entity.Configuration.Configuration;
import me.twodee.quizatron.Model.Entity.Sequence;
import me.twodee.quizatron.Model.Exception.NonExistentRecordException;

import java.io.IOException;
import java.util.stream.Stream;

public interface ISequenceMapper
{
    void fetch(Sequence sequence) throws NonExistentRecordException;
    int getTotalRecords();
    Stream<Sequence> getStream();
    void init(String configuration) throws IOException;
}
