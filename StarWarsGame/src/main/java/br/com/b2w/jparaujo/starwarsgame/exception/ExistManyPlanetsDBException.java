package br.com.b2w.jparaujo.starwarsgame.exception;

public class ExistManyPlanetsDBException extends RuntimeException {
    public ExistManyPlanetsDBException() {
        super();
    }

    public ExistManyPlanetsDBException(String message) {
        super(message);
    }

    public ExistManyPlanetsDBException(String message, Throwable cause) {
        super(message, cause);
    }

    public ExistManyPlanetsDBException(Throwable cause) {
        super(cause);
    }
}
