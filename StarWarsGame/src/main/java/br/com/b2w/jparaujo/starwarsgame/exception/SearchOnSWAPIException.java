package br.com.b2w.jparaujo.starwarsgame.exception;

public class SearchOnSWAPIException extends RuntimeException {
    public SearchOnSWAPIException() {
        super();
    }

    public SearchOnSWAPIException(String message) {
        super(message);
    }

    public SearchOnSWAPIException(String message, Throwable cause) {
        super(message, cause);
    }

    public SearchOnSWAPIException(Throwable cause) {
        super(cause);
    }
}
