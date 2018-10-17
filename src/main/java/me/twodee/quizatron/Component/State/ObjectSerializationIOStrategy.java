package me.twodee.quizatron.Component.State;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.ObjectWriter;

import java.io.*;
import java.util.Map;

public class ObjectSerializationIOStrategy implements SerializationIOStrategy {

    private static final ObjectReader objectReader;
    private static final ObjectWriter objectWriter;

    static {
        ObjectMapper objectMapper = new ObjectMapper();
        objectReader = objectMapper.reader().forType(Map.class);
        objectWriter = objectMapper.writer().forType(Map.class);
    }

    @Override
    public void persist(Map stateMap, String location) throws IOException {
        FileOutputStream outputStream = new FileOutputStream(location);
        objectWriter.writeValue(outputStream, stateMap);
        outputStream.close();
    }

    @Override
    public void populate(Map stateContainer, String location) throws IOException {
        FileInputStream inputStream = new FileInputStream(location);
        stateContainer.putAll(objectReader.readValue(inputStream));
        inputStream.close();
    }
}
