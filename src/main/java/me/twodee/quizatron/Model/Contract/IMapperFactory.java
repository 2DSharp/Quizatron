package me.twodee.quizatron.Model.Contract;

import com.google.inject.ImplementedBy;
import me.twodee.quizatron.Factory.CSVMapperFactory;

import java.lang.reflect.InvocationTargetException;

@ImplementedBy(CSVMapperFactory.class)
public interface IMapperFactory<T>
{
    T create(Class<T> targetClass, String config)
    throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException;
}
