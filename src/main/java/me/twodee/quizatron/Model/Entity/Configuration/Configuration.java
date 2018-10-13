package me.twodee.quizatron.Model.Entity.Configuration;

import com.google.common.base.MoreObjects;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Configuration implements Serializable {

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("alias")
    @Expose
    private String alias;

    @SerializedName("description")
    @Expose
    private String description;

    @SerializedName("organizer")
    @Expose
    private String organizer;

    @SerializedName("sequence")
    @Expose
    private String sequence;

    @SerializedName("appearance")
    @Expose
    private Appearance appearance;

    public String getName() {
        return name;
    }

    public String getAlias() {
        return alias;
    }

    public String getDescription() {
        return description;
    }

    public String getOrganizer() {
        return organizer;
    }

    public String getSequence() {
        return sequence;
    }

    public Appearance getAppearance() {
        return appearance;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("name", name)
                .add("alias", alias)
                .add("description", description)
                .add("organizer", organizer)
                .add("sequence", sequence)
                .add("appearance", appearance)
                .toString();
    }
}