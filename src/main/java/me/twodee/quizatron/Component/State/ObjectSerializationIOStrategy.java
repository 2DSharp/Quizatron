package me.twodee.quizatron.Component.State;

import java.io.*;
import java.util.Map;

public class ObjectSerializationIOStrategy implements SerializationIOStrategy {
    @Override
    public void persist(Map stateMap, String location) throws IOException {

        FileOutputStream outputStream = new FileOutputStream(location);
        ObjectOutput objectOutput = new ObjectOutputStream(outputStream);
        objectOutput.writeObject(stateMap);
        objectOutput.flush();
        objectOutput.close();
    }

    @Override
    public void populate(Map stateContainer, String location) throws IOException, ClassNotFoundException {
        FileInputStream inputStream = new FileInputStream(location);
        ObjectInput objectInput = new ObjectInputStream(inputStream);
        Map loadedMap = (Map) objectInput.readObject();
        stateContainer.putAll(loadedMap);
    }
}
