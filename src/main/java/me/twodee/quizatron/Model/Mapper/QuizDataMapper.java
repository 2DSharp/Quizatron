package me.twodee.quizatron.Model.Mapper;

import javax.inject.Inject;
import me.twodee.quizatron.Model.Contract.ProjectDataMapper;
import me.twodee.quizatron.Model.Entity.QuizData;

import java.io.*;

import com.google.gson.Gson;

public class QuizDataMapper implements ProjectDataMapper<QuizData> {

    private final Gson gson;

    @Inject
    public QuizDataMapper(Gson gson) {
        this.gson = gson.newBuilder()
                .setPrettyPrinting()
                .create();
    }

    public QuizData load(String location) throws IOException, ClassNotFoundException {

        try(BufferedReader bufferedReader = new BufferedReader(new FileReader(location))) {
            return gson.fromJson(bufferedReader, QuizData.class);
        }
    }

    @Override
    public void save(QuizData quizData, String location) throws IOException {

        try (Writer writer = new FileWriter(location)) {
            gson.toJson(quizData, writer);
        }
    }
}
