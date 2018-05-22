package me.twodee.quizatron.Factory;

import com.google.inject.Injector;
import javafx.fxml.FXMLLoader;

import javax.inject.Inject;

public class LoaderFactory {

    private FXMLLoader loader;
    private Injector injector;

    @Inject
    public LoaderFactory(Injector injector) {
        this.injector = injector;
    }

    public FXMLLoader build(String viewFile) {
        loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../Presentation/View/" + viewFile + ".fxml" ));
        loader.setControllerFactory(injector::getInstance);
        return loader;
    }
}
