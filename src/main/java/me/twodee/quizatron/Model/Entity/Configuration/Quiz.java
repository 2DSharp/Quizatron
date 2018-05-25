package me.twodee.quizatron.Model.Entity.Configuration;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Quiz implements Serializable {

    @SerializedName("sequence")
    @Expose
    private String sequence;
    @SerializedName("teams")
    @Expose
    private String teams;

    public String getSequence() {
        return sequence;
    }

    public String getTeams() {
        return teams;
    }
}