package me.twodee.quizatron.Model.Mapper;

import me.twodee.quizatron.Model.Contract.ProjectDataMapper;
import me.twodee.quizatron.Model.Entity.QuizData;

import java.io.*;

public class QuizDataMapper implements ProjectDataMapper<QuizData> {

    /**
     * Load the quiz data from a saved file (deserialize)
     * @param saveFile
     * @return QuizData
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public QuizData load(String saveFile) throws IOException, ClassNotFoundException {

        FileInputStream inputStream = new FileInputStream(saveFile);
        ObjectInput objectInput = new ObjectInputStream(inputStream);
        return (QuizData) objectInput.readObject();
    }
    /**
     * Serialize the data
     * TODO: Copied from somewhere, clean it up
     * @param quizData
     * @param location
     * @throws IOException
     */
    @Override
    public void save(QuizData quizData, String location) throws IOException {

        FileOutputStream outputStream = new FileOutputStream(location);
        ObjectOutput objectOutput = new ObjectOutputStream(outputStream);
        objectOutput.writeObject(quizData);
        objectOutput.flush();
        objectOutput.close();
    }
}
