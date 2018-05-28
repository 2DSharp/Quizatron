package me.twodee.quizatron.Console.Controller;

import me.twodee.quizatron.Component.Controller;

import java.util.HashMap;
import java.util.Map;

public class StateSaverController implements Controller {

    private Map input;

    @Override
    public void update() {

    }

    @Override
    public void setInput(Map input) {

        this.input = input;
    }
}
