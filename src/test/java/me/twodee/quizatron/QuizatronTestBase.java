package me.twodee.quizatron;

import com.google.inject.Guice;
import com.google.inject.Injector;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;
import me.twodee.quizatron.Component.Presentation;
import me.twodee.quizatron.Console.Panel.PanelPresenter;
import me.twodee.quizatron.Console.Panel.PanelPresenterTest;
import org.junit.After;
import org.junit.Before;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit.ApplicationTest;

import java.util.concurrent.TimeoutException;

public class QuizatronTestBase extends ApplicationTest {

    protected Injector injector;

    @Before
    public void setUpClass() throws Exception {
        ApplicationTest.launch(Main.class);

        injector = Guice.createInjector(new QuizatronModule());
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.show();
    }

    @After
    public void afterEachTest() throws TimeoutException {
        FxToolkit.hideStage();
        release(new KeyCode[]{});
        release(new MouseButton[]{});
    }
    /* Helper method to retrieve JavaFX components */
    public <T extends Node> T find(final String query) {
        return (T) lookup(query).queryAll().iterator().next();
    }
}
