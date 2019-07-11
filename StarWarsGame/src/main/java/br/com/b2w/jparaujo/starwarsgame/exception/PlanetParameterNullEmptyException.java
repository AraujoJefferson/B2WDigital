package br.com.b2w.jparaujo.starwarsgame.exception;

public class PlanetParameterNullEmptyException extends RuntimeException {
    public PlanetParameterNullEmptyException() {
        super();
    }

    public PlanetParameterNullEmptyException(String message) {
        super(message);
    }

    public PlanetParameterNullEmptyException(String message, Throwable cause) {
        super(message, cause);
    }

    public PlanetParameterNullEmptyException(Throwable cause) {
        super(cause);
    }
}
