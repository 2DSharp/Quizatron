package me.twodee.quizatron.Model.Service;

import me.twodee.quizatron.Model.Contract.IQuestion;
import me.twodee.quizatron.Model.Contract.IQuestionSetService;
import me.twodee.quizatron.Model.Entity.Question;
import me.twodee.quizatron.Model.Exception.NoQuestionLeftException;
import me.twodee.quizatron.Model.Mapper.QuestionMapper;
import org.apache.commons.csv.CSVRecord;

import javax.inject.Inject;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class QuestionSetService implements IQuestionSetService {

    private Question question;
    private QuestionMapper questionMapper;
    private Iterator<CSVRecord> set = null;
    private List setList;

    @Inject
    public QuestionSetService(QuestionMapper questionMapper) {

        this.questionMapper = questionMapper;
    }
    @Override
    public void loadSet(Path file) throws IOException{
        set = questionMapper.load(file.toAbsolutePath().toString());

        CSVRecord record = set.next();
        question = loadQuestionFromRecord(record);

    }

    public List<Question> toList() {
        setList = new ArrayList<Question>();
        Iterator<CSVRecord> iterator = set;

        setList.add(question);

        while (iterator.hasNext()) {
            CSVRecord record = iterator.next();
            Question listQuestion = loadQuestionFromRecord(record);

            setList.add(listQuestion);
        }
        return setList;
    }

    public Question getQuestion() {
        return question;
    }

    public Question getQuestion(int index) {

        return (Question) setList.get(index - 1);
    }


    @Override
    public Question nextQuestion() throws NoQuestionLeftException {
        if (set.hasNext()) {
            CSVRecord record = set.next();
            question = loadQuestionFromRecord(record);
            return question;
        }
        else {
            throw new NoQuestionLeftException();
        }
    }


    public boolean isQSetLoaded() {
        return question != null;
    }


    public boolean hasNext() {
        return set.hasNext();
    }

    private Question loadQuestionFromRecord(CSVRecord record) {

        String title = record.get("Title");
        String description = record.get("Description");
        String answer = record.get("Answer");
        String image = record.get("Image");

        return new Question(title, description, answer, image);
    }
}
