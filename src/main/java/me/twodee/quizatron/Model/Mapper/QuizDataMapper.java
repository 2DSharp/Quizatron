package me.twodee.quizatron.Model.Mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.ObjectWriter;
import me.twodee.quizatron.Model.Contract.ProjectDataMapper;
import me.twodee.quizatron.Model.Entity.QuizData;

import java.io.*;
import java.util.Map;

public class QuizDataMapper implements ProjectDataMapper<QuizData> {

    private static final ObjectReader objectReader;
    private static final ObjectWriter objectWriter;

    static {
        ObjectMapper objectMapper = new ObjectMapper();
        objectReader = objectMapper.reader().forType(QuizData.class);
        objectWriter = objectMapper.writer().forType(QuizData.class);
    }

    public QuizData load(String saveFile) throws IOException {
        FileInputStream inputStream = new FileInputStream(saveFile);
        QuizData quizData = objectReader.readValue(inputStream);
        inputStream.close();
        return quizData;
    }

    @Override
    public void save(QuizData quizData, String location) throws IOException {
        FileOutputStream outputStream = new FileOutputStream(location);
        objectWriter.writeValue(outputStream, quizData);
        outputStream.close();
    }
}
