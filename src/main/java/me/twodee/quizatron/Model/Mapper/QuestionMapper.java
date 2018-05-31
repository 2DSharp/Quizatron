package me.twodee.quizatron.Model.Mapper;

import me.twodee.quizatron.Model.Contract.CSVMapper;
import me.twodee.quizatron.Model.Entity.Question;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class QuestionMapper implements CSVMapper {

    Iterator<CSVRecord> iterator;

    public Iterator<CSVRecord> load(String filePath) throws IOException {
        Reader input = new FileReader(filePath);
        Iterable<CSVRecord> recordSetIterable = CSVFormat.EXCEL.withHeader().parse(input);

        iterator = recordSetIterable.iterator();
        return iterator;
    }

}
