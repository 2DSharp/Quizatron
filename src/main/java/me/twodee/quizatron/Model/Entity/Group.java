package me.twodee.quizatron.Model.Entity;

public class Group
{
    private int index;
    private String file;

    public Group()
    {

    }
    public Group(String fileName)
    {
        this.file = fileName;
    }

    public void setGroup(Group group)
    {
        this.index = group.index;
        this.file = group.file;
    }
    public int getIndex()
    {
        return index;
    }

    public void setIndex(int index)
    {
        this.index = index;
    }

    public String getFile()
    {
        return file;
    }

    public void setFile(String file)
    {
        this.file = file;
    }
}
