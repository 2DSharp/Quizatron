package me.twodee.quizatron.Model.Contract;

import me.twodee.quizatron.Model.Entity.Sequence;
import me.twodee.quizatron.Model.Exception.NonExistentRecordException;

public interface ISequenceMapper
{
    void fetch(Sequence sequence) throws NonExistentRecordException;
    int getTotalRecords();
}
