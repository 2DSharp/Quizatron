package me.twodee.quizatron.Factory;

import me.twodee.quizatron.Model.Contract.RoundService;
import me.twodee.quizatron.Model.Service.QuestionSetService;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

public class RoundServiceFactory
{
    private String roundConfig;
    private Map<String, Object> roundMap = new HashMap<>();

    public RoundServiceFactory(String roundConfig)
    {
        this.roundConfig = roundConfig;
        roundMap.put("Some", QuestionSetService.class);
    }

    public RoundService create(String type)
    {
        //roundMap.get(type).getClass().getConstructor();
    }
}
