package me.twodee.quizatron.Component;

import javax.inject.Inject;

public abstract class CSVDataMapper
{
    protected CSVManager csvManager;
    protected String configuration;

    @Inject
    public CSVDataMapper(CSVManager csvManager, String config)
    {
        this.csvManager = csvManager;
        this.configuration = config;
    }

}
