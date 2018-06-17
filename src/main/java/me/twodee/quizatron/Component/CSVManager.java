package me.twodee.quizatron.Component;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Iterator;

public class CSVManager
{

    private Iterator<CSVRecord> iterator;

    public Iterator<CSVRecord> load(String filePath) throws IOException {
        Reader input = new FileReader(filePath);
        Iterable<CSVRecord> recordSetIterable = CSVFormat.EXCEL.withHeader().parse(input);

        iterator = recordSetIterable.iterator();
        return iterator;
    }
}
