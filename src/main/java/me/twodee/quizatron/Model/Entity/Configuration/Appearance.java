package me.twodee.quizatron.Model.Entity.Configuration;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Appearance implements Serializable
{

    @SerializedName("logo")
    @Expose
    private String logo;
    @SerializedName("logo-animated")
    @Expose
    private String logoAnimated;
    @SerializedName("secondary-animation")
    @Expose
    private String secondaryAnimation;
    @SerializedName("theme-music")
    @Expose
    private String themeMusic;
    @SerializedName("default-background")
    @Expose
    private String defaultBackground;
    @SerializedName("theme-bg-color")
    @Expose
    private String themeBgColor;
    @SerializedName("text-color")
    @Expose
    private String textColor;
    @SerializedName("theme-color")
    @Expose
    private String themeColor;
    @SerializedName("theme-left-bg")
    @Expose
    private String themeLeftBg;


    public String getLogo()
    {
        return logo;
    }

    public String getLogoAnimated()
    {
        return logoAnimated;
    }

    public String getSecondaryAnimation()
    {
        return secondaryAnimation;
    }

    public String getThemeMusic()
    {
        return themeMusic;
    }

    public String getDefaultBackground()
    {
        return defaultBackground;
    }

    public String getThemeBgColor()
    {
        return themeBgColor;
    }

    public String getTextColor()
    {
        return textColor;
    }

    public String getThemeColor()
    {
        return themeColor;
    }

    public String getThemeLeftBg()
    {
        return themeLeftBg;
    }
}