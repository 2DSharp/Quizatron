package me.twodee.quizatron.Model.Contract;

import com.google.inject.ImplementedBy;
import me.twodee.quizatron.Model.Entity.State;
import me.twodee.quizatron.Model.Mapper.StateMapper;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
@ImplementedBy(StateMapper.class)
public interface IStateMapper {

    void populate(IState state, Path config) throws FileNotFoundException;
    State load(IState state, String saveFile) throws IOException, ClassNotFoundException;
    void save(IState state, String saveFile) throws IOException;
}
