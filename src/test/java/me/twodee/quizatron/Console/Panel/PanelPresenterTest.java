package me.twodee.quizatron.Console.Panel;

import javafx.application.Platform;
import me.twodee.quizatron.Component.Presentation;
import me.twodee.quizatron.QuizatronTestBase;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.testfx.api.FxRobotException;
import org.testfx.matcher.base.NodeMatchers;
import org.testfx.util.WaitForAsyncUtils;

import javax.inject.Inject;

import static org.testfx.api.FxAssert.verifyThat;

public class PanelPresenterTest extends QuizatronTestBase {
    final String MEDIA_PLAYER_TAB = "#mediaPlayerTab";
    final String DASHBOARD = "#dashboard";
    final String START_QUIZ_BUTTON = "#startBtn";

    @Test (expected = FxRobotException.class)
    public void clickOnNonExistentElem() {
        clickOn("#fakeElement");
    }

    @Test
    public void testOpenMediaPlayerTab() {
        clickOn(MEDIA_PLAYER_TAB);
        verifyThat(DASHBOARD, NodeMatchers.hasChild("#mediaNode"));

    }
    @Test
    public void testStartQuiz() {
        clickOn(START_QUIZ_BUTTON);
        verifyThat("#homeView", NodeMatchers.isVisible());
    }

}
