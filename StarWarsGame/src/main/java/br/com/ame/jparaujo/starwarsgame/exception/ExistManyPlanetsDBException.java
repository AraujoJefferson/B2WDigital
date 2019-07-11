package br.com.ame.jparaujo.starwarsgame.exception;

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
