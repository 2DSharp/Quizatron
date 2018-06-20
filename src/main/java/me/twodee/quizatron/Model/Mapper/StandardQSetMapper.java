package me.twodee.quizatron.Model.Mapper;

import me.twodee.quizatron.Component.CSVManager;
import me.twodee.quizatron.Model.Contract.CSVReaderMapper;
import me.twodee.quizatron.Model.Entity.Configuration.Configuration;
import me.twodee.quizatron.Model.Entity.Question;
import me.twodee.quizatron.Model.Exception.NonExistentRecordException;
import org.apache.commons.csv.CSVRecord;

import javax.inject.Inject;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;


public class StandardQSetMapper implements CSVReaderMapper<Question>
{

    private Iterator<CSVRecord> iterator;
    private CSVManager csvManager;
    private String setFile;
    List<Question> questions;

    public StandardQSetMapper(CSVManager csvManager, String setFile) throws IOException
    {
        this.csvManager = csvManager;
        this.setFile = setFile;
        init();
    }

    public void fetch(Question question) throws NonExistentRecordException
    {
        try {
            Question target = questions.get(question.getIndex());
            question.setQuestion(target);
        }
        catch (IndexOutOfBoundsException e){
            throw new NonExistentRecordException();
        }
    }

    private void init() throws IOException
    {
        iterator = csvManager.load(setFile);
        questions = new ArrayList<>();

        int index = 0;

        while (iterator.hasNext()) {
            CSVRecord record = iterator.next();
            Question question = loadQuestionFromRecord(record);
            question.setIndex(index++);
            questions.add(question);
        }
    }

    private Question loadQuestionFromRecord(CSVRecord record)
    {
        String id = record.get("ID");
        String title = record.get("Title");
        String qImage = record.get("Image");
        String answer = record.get("Answer");
        String ansImage = record.get("AnsImage");
        String media = record.get("Media");
        return new Question(id, title, qImage, answer, ansImage, media);
    }

    public int getTotalRecords()
    {
        return questions.size();
    }

    public Stream<Question> getStream()
    {
        return questions.stream();
    }
}
