package controller.exception;

import javax.servlet.ServletException;

public class AnnullaOrdineSmartException extends ServletException {

    public AnnullaOrdineSmartException(String message) {
        super(message);
    }

    public AnnullaOrdineSmartException(String message, Throwable cause) {
        super(message, cause);
    }
}
