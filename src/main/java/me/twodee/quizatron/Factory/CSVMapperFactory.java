package me.twodee.quizatron.Factory;

import me.twodee.quizatron.Component.CSVManager;
import me.twodee.quizatron.Model.Contract.IMapper;
import me.twodee.quizatron.Model.Contract.IMapperFactory;

import javax.inject.Inject;
import java.lang.reflect.InvocationTargetException;

public class CSVMapperFactory implements IMapperFactory<IMapper>
{
    private CSVManager csvManager;

    @Inject
    public CSVMapperFactory(CSVManager csvManager)
    {
        this.csvManager = csvManager;
    }

    public IMapper create(Class<IMapper> dataMapperClass, String file)
    throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException
    {
        return dataMapperClass.getDeclaredConstructor(CSVManager.class, String.class)
                              .newInstance(csvManager, file);
    }

}
