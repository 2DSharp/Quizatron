package me.twodee.quizatron.Component;

public class Mediator {

    private String error;
    boolean hasError = false;

    public void setError(String error) {

        this.error = error;
        hasError = true;
    }

    public Object getError() {
        return error;
    }

    public boolean hasError() {
        return hasError;
    }

    public void updateModel(Controller controller) {

        clean();
        controller.update();
    }

    public void display(View view) {

        view.display();
    }

    private void clean() {

        error = null;
        hasError = false;
    }
}
