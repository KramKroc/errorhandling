package com.example.kramkroc.errorhandling;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/")
public class ErrorCausingController {

    private static final Logger logger = LoggerFactory.getLogger(ErrorCausingController.class);

    @RequestMapping(method = RequestMethod.GET, value = "/")
    public String index() {
        return "Greetings from Spring Boot!";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/errorCausing")
    public String errorCausing() {
        throw new RuntimeException("Messed that up!");
    }

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<MappedError> handleAllExceptions(final Exception ex,
                                                           final HttpServletRequest request) {
        logger.warn("Exception {}/{} request={}", ex, ex.getMessage(), request);

        final MappedError error = new MappedError(ex.getMessage(),
                                                  HttpStatus.INTERNAL_SERVER_ERROR.value());
        return new ResponseEntity<>(error, error.httpStatusCode());

    }

}
