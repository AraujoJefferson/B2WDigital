package br.com.ame.jparaujo.starwarsgame.exception;

public class PlanetDoesNotExistOnDBException extends RuntimeException {
    public PlanetDoesNotExistOnDBException() {
        super();
    }

    public PlanetDoesNotExistOnDBException(String message) {
        super(message);
    }

    public PlanetDoesNotExistOnDBException(String message, Throwable cause) {
        super(message, cause);
    }

    public PlanetDoesNotExistOnDBException(Throwable cause) {
        super(cause);
    }
}
