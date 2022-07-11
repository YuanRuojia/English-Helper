package org.knowyourdenfender.enhelper.exception;

import org.knowyourdenfender.enhelper.pojo.ServerResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


/**
 * General exception handler for RestController(s).
 * Proceeds uncaught exception.
 *
 * @author Miris
 */
@RestControllerAdvice
public class GeneralControllerExceptionHandler {

    /**
     * General exception handler for RestController(s). Returns a 500 code for any exception.
     *
     * @param e Any exception not being caught.
     * @return A ServerResult object with specified code.
     * @author Miris
     */
    @ExceptionHandler
    public ServerResult GeneralHandler(Throwable e) {
        e.printStackTrace();

        return new ServerResult(500, "Server Error", e.getMessage());
    }
}
