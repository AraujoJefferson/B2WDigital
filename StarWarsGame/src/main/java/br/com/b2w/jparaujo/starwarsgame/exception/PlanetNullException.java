package br.com.b2w.jparaujo.starwarsgame.exception;

public class PlanetNullException extends RuntimeException {
    public PlanetNullException() {
        super();
    }

    public PlanetNullException(String message) {
        super(message);
    }

    public PlanetNullException(String message, Throwable cause) {
        super(message, cause);
    }

    public PlanetNullException(Throwable cause) {
        super(cause);
    }
}
