package me.twodee.quizatron.Factory;

import me.twodee.quizatron.Model.Contract.RoundService;
import me.twodee.quizatron.Model.Service.RoundService.StandardQSet;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public class RoundServiceFactory
{
    private String roundConfig;
    private Map<String, Class> roundMap = new HashMap<>();

    public RoundServiceFactory(String roundConfig)
    {
        this.roundConfig = roundConfig;
        roundMap.put("Some", StandardQSet.class);
    }

    public RoundService create(String type)
    throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException
    {
        Class serviceClass = roundMap.get(type);
        Constructor ctor = serviceClass.getConstructor();
        RoundService instance = (RoundService) ctor.newInstance();
        return instance;
    }
}
