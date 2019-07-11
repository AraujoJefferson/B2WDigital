package br.com.b2w.jparaujo.starwarsgame.exception;

import br.com.b2w.jparaujo.starwarsgame.constant.ApplicationConstants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {
    private static final Logger loggerREH = LogManager.getLogger(RestExceptionHandler.class);

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        loggerREH.error(ApplicationConstants.MESSAGE_BADREQUEST);
        return new ResponseEntity<>(ApplicationConstants.MESSAGE_BADREQUEST, HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status,
                                                                  WebRequest request) {

        loggerREH.error("handleMethodArgumentNotValid");
        BindingResult bindingResult = ex.getBindingResult();

        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY.value(), HttpStatus.UNPROCESSABLE_ENTITY);
        }
        return new ResponseEntity<>( HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {PlanetDoesNotExistOnDBException.class})
    protected ResponseEntity handleDoesNotExist() {
        loggerREH.error(ApplicationConstants.MESSAGE_ENDPOINT_PLANET_NOTEXIST);
        return new ResponseEntity(ApplicationConstants.MESSAGE_ENDPOINT_PLANET_NOTEXIST,HttpStatus.NO_CONTENT);
    }

    @ExceptionHandler(value = {SearchOnSWAPIException.class})
    protected ResponseEntity handleSearchOnSWAPI() {
        loggerREH.error(ApplicationConstants.MESSAGE_ENDPOINT_PLANET_SEARCH_SWAPI);
        return new ResponseEntity(ApplicationConstants.MESSAGE_ENDPOINT_PLANET_SEARCH_SWAPI,HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = {PlanetExistOnDBException.class})
    protected ResponseEntity handleExist() {
        loggerREH.error(ApplicationConstants.MESSAGE_ENDPOINT_PLANET_EXIST);
        return new ResponseEntity(ApplicationConstants.MESSAGE_ENDPOINT_PLANET_EXIST,HttpStatus.NOT_MODIFIED);
    }

    @ExceptionHandler(value = {PlanetParameterNullEmptyException.class})
    protected ResponseEntity handlePlanetParameterNullEmpty() {
        loggerREH.error(ApplicationConstants.MESSAGE_ENDPOINT_PLANET_PARAMETER_INVALID);
        return new ResponseEntity(ApplicationConstants.MESSAGE_ENDPOINT_PLANET_PARAMETER_INVALID,HttpStatus.NOT_MODIFIED);
    }

    @ExceptionHandler(value = {PlanetNullException.class})
    protected ResponseEntity handlePlanetNull() {
        loggerREH.error(ApplicationConstants.MESSAGE_ENDPOINT_PLANET_NULL);
        return new ResponseEntity(ApplicationConstants.MESSAGE_ENDPOINT_PLANET_NULL,HttpStatus.NOT_MODIFIED);
    }

    @ExceptionHandler(value = {ExistManyPlanetsDBException.class})
    protected ResponseEntity handleExistManyPlanetsDB() {
        loggerREH.error(ApplicationConstants.MESSAGE_ENDPOINT_PLANET_MANY_EXIST);
        return new ResponseEntity(ApplicationConstants.MESSAGE_ENDPOINT_PLANET_MANY_EXIST,HttpStatus.NOT_MODIFIED);
    }
}