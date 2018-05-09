package me.twodee.quizatron.Model;

import javax.inject.Inject;

public class Score {

    private Person person;
    private int score;

    @Inject
    public Score(Person person) {
        this.score = 10;
        this.person = person;
    }
    public String getScore() {
        return this.person.getName()+ " " + this.score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
