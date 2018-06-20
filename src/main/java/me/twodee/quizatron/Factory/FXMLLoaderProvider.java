package me.twodee.quizatron.Factory;

import com.google.inject.Injector;
import javafx.fxml.FXMLLoader;

import javax.inject.Inject;
import javax.inject.Provider;

public class FXMLLoaderProvider implements Provider<FXMLLoader> {

    private Injector injector;
    private FXMLLoader loader;

    @Inject
    public FXMLLoaderProvider(Injector injector)  {

        this.injector = injector;
    }

    public FXMLLoader get() {

        FXMLLoader loader = new FXMLLoader();
        loader.setControllerFactory(injector::getInstance);
        return loader;
    }

    public FXMLLoader build(String viewFile) {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(this.getClass().getResource("../Presentation/View/" + viewFile + ".fxml" ));
        loader.setControllerFactory(injector::getInstance);
        return loader;
    }
}
