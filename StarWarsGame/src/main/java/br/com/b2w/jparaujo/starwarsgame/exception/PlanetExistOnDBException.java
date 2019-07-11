package br.com.b2w.jparaujo.starwarsgame.exception;

public class PlanetExistOnDBException extends RuntimeException {
    public PlanetExistOnDBException() {
        super();
    }

    public PlanetExistOnDBException(String message) {
        super(message);
    }

    public PlanetExistOnDBException(String message, Throwable cause) {
        super(message, cause);
    }

    public PlanetExistOnDBException(Throwable cause) {
        super(cause);
    }
}
