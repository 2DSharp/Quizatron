package me.twodee.quizatron.Model.Entity.Configuration;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Appearance implements Serializable {

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
    @SerializedName("theme-color")
    @Expose
    private String themeColor;

    public String getLogo() {
        return logo;
    }

    public String getLogoAnimated() {
        return logoAnimated;
    }

    public String getSecondaryAnimation() {
        return secondaryAnimation;
    }

    public String getThemeMusic() {
        return themeMusic;
    }

    public String getDefaultBackground() {
        return defaultBackground;
    }

    public String getThemeColor() {
        return themeColor;
    }
}