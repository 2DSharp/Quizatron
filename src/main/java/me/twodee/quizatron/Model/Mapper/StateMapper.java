package me.twodee.quizatron.Model.Mapper;

import com.google.gson.Gson;
import me.twodee.quizatron.Model.Contract.IState;
import me.twodee.quizatron.Model.Contract.IStateMapper;
import me.twodee.quizatron.Model.Entity.Configuration.Configuration;
import me.twodee.quizatron.Model.Entity.State;
import me.twodee.quizatron.Model.Exception.ProjectNotSetException;

import javax.inject.Inject;
import java.io.*;
import java.net.MalformedURLException;
import java.nio.file.Path;

public class StateMapper implements IStateMapper {
    private Gson gson;

    @Inject
    StateMapper(Gson gson) {
        this.gson = gson;
    }

    public void populate(IState state, Path configFile) throws FileNotFoundException {

        Configuration configuration = gson.fromJson(new FileReader(configFile.toString()),
                                                    Configuration.class);
        state.setConfigurationFile(configFile);
        state.setConfiguration(configuration);
    }

    public State load(IState state, String saveFile) throws IOException, ClassNotFoundException {

        FileInputStream inputStream = new FileInputStream(saveFile);
        ObjectInput objectInput = new ObjectInputStream(inputStream);
        state = (State) objectInput.readObject();
        return (State) state;
    }

    public void save(IState state, String saveFile) throws IOException {
        FileOutputStream outputStream = new FileOutputStream(saveFile);
        ObjectOutput objectOutput = new ObjectOutputStream(outputStream);
        objectOutput.writeObject(state);
        objectOutput.flush();
        objectOutput.close();
    }
}
