package me.twodee.quizatron.Model.Entity;

public class Group
{
    private int index;
    private String file;
    private String image;
    private String answer;
    private String blockFile;

    public Group()
    {

    }
    public Group(String fileName, String image, String answer, String blockFile)
    {
        this.file = fileName;
        this.image = image;
        this.answer = answer;
        this.blockFile = blockFile;
    }

    public void setGroup(Group group)
    {
        this.index = group.index;
        this.image = group.image;
        this.file = group.file;
        this.answer = group.answer;
        this.blockFile = group.blockFile;
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

    public String getImage()
    {
        return image;
    }

    public void setImage(String image)
    {
        this.image = image;
    }

    public String getAnswer()
    {
        return answer;
    }

    public String getBlockFile()
    {
        return blockFile;
    }
}
