package me.twodee.quizatron.Component.State;

import java.io.IOException;
import java.nio.file.Path;

public interface StateContextInterface {

    void set(String key, Object value);
    <T> T get(String key);

    boolean has(String key);

    void replace(String key, Object value);
    void remove(String key);

    void destroy();

    void save(SerializationIOStrategy serializationIOStrategy, Path location) throws IOException;
    void load(SerializationIOStrategy serializationIOStrategy, Path location) throws IOException, ClassNotFoundException;
}
