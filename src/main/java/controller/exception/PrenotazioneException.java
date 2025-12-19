package controller.exception;

public class PrenotazioneException extends Exception {

    public PrenotazioneException(String message) {
        super(message);
    }

    public PrenotazioneException(String message, Throwable cause) {
        super(message, cause);
    }
}
