package me.twodee.quizatron.Model.Entity;

public class Sequence
{
    private int id;
    private String name;
    private String type;
    private String filePath;
    private String design;
    private String intro;
    private String roundType;
    private String secImage;
    private int index;

    public Sequence()
    {
    }

    public void copySequence(Sequence sequence)
    {
        this.id = sequence.id;
        this.name = sequence.name;
        this.type = sequence.type;
        this.roundType = sequence.roundType;
        this.filePath = sequence.filePath;
        this.design = sequence.design;
        this.intro = sequence.intro;
        this.secImage = sequence.secImage;
    }
    public Sequence(int id, String name, String type, String roundType, String filePath, String design, String intro, String secImage)
    {
        this.id = id;
        this.name = name;
        this.type = type;
        this.roundType = roundType;
        this.filePath = filePath;
        this.design = design;
        this.intro = intro;
        this.secImage = secImage;
    }

    public String getName()
    {
        return name;
    }

    public int getID()
    {
        return id;
    }

    public void setID(int id)
    {
        this.id = id;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public int getIndex()
    {
        return index;
    }

    public void setIndex(int index)
    {
        this.index = index;
    }

    public String getType()
    {
        return type;
    }

    public String getFilePath()
    {
        return filePath;
    }

    public String getIntro()
    {
        return intro;
    }

    public String getRoundType()
    {
        return roundType;
    }

    public String getSecImage()
    {
        return secImage;
    }
}
