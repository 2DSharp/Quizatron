package me.twodee.quizatron.Model.Contract;

import com.google.inject.ImplementedBy;
import me.twodee.quizatron.Model.Mapper.StateMapper;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
@ImplementedBy(StateMapper.class)
public interface IStateMapper {

    void populate(IState state, Path config) throws FileNotFoundException;
    void load(IState state, Path saveFile);
    void save(IState state, String saveFile) throws IOException;
}
