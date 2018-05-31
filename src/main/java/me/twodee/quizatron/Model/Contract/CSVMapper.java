package me.twodee.quizatron.Model.Contract;

import org.apache.commons.csv.CSVRecord;

import java.io.IOException;
import java.util.Iterator;

public interface CSVMapper {

    Iterator<CSVRecord> load(String filePath) throws IOException;
}
