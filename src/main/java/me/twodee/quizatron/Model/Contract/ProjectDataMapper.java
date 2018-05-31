package me.twodee.quizatron.Model.Contract;

import java.io.FileNotFoundException;
import java.io.IOException;

public interface ProjectDataMapper<T> {

    T load(String location) throws IOException, ClassNotFoundException;

    void save(T entity, String location) throws IOException;
}
