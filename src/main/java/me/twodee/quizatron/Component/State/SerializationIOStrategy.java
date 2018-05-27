package me.twodee.quizatron.Component.State;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public interface SerializationIOStrategy {

    void persist(Map stateContainer, String location) throws IOException;
    void populate(Map stateContainer, String location) throws IOException, ClassNotFoundException;
}
