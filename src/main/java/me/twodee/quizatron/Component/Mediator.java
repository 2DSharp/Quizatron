package me.twodee.quizatron.Component;

public class Mediator {

    private String error;
    private boolean hasError = false;

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

    public void request(Controller controller) {

        clean();
        controller.update();
    }

    public void respond(View view) {

        view.display();
    }

    private void clean() {

        error = null;
        hasError = false;
    }
}
