package me.twodee.quizatron.Component.State;

import javax.inject.Inject;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Path;
import java.util.Map;

public class State implements StateContextInterface, Serializable {

    private final Map<String, Object> stateMap;
    private SerializationIOStrategy serializationIOStrategy;

    @Inject
    public State(Map stateMap, SerializationIOStrategy serializationIOStrategy) {

        this.stateMap = stateMap;
    }

    public <T> T get(String key) {

        return (T) stateMap.get(key);
    }

    public void set(String key, Object value) {

        stateMap.put(key, value);
    }

    @Override
    public boolean has(String key) {

        return stateMap.containsKey(key);
    }

    @Override
    public void remove(String key) {

        stateMap.remove(key);
    }

    @Override
    public void destroy() {

        stateMap.clear();
    }

    @Override
    public  void replace(String key, Object value) {

        stateMap.replace(key, value);
    }


    public void save(Path location) throws IOException {
        // Why not let the strategy be included as an argument?
        // The state doesn't necessarily need persistence
        // If it does, provide the serializer
        String file = location.toAbsolutePath().toString();
        serializationIOStrategy.persist(stateMap, file);
    }


    public void load(Path location) throws IOException, ClassNotFoundException {

        String file = location.toAbsolutePath().toString();
        serializationIOStrategy.populate(stateMap, file);
    }
}