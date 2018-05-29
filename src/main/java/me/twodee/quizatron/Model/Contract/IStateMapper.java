package me.twodee.quizatron.Model.Contract;

import com.google.inject.ImplementedBy;
import me.twodee.quizatron.Model.Entity.QuizData;
import me.twodee.quizatron.Model.Mapper.QuizDataMapper;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
@ImplementedBy(QuizDataMapper.class)
public interface IStateMapper {

    void populate(IState state, Path config) throws FileNotFoundException;
    QuizData load(IState state, String saveFile) throws IOException, ClassNotFoundException;
    void save(IState state, String saveFile) throws IOException;
}
