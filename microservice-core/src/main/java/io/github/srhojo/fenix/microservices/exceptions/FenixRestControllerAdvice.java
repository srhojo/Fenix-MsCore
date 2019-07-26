package io.github.srhojo.fenix.microservices.exceptions;

import static io.github.srhojo.fenix.microservices.exceptions.ExceptionConstants.ERRORS_GENERIC_CODE;
import static io.github.srhojo.fenix.microservices.exceptions.ExceptionConstants.ERRORS_GENERIC_MESSAGE;
import static io.github.srhojo.fenix.microservices.exceptions.ExceptionConstants.ERRORS_VALIDATION_CODE;
import static io.github.srhojo.fenix.microservices.exceptions.ExceptionConstants.ERRORS_VALIDATION_MESSAGE;

import javax.validation.ConstraintViolationException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * 
 * @author: srhojo
 * @see <a href="https://github.com/srhojo">GitHub</a>
 *
 */
@RestControllerAdvice
public class FenixRestControllerAdvice extends ResponseEntityExceptionHandler {

    /**
     * Method to response handled exceptions
     *
     * @param fenixException the handled warehouse exception
     * @return ResponseEntity<WarehouseExceptionResponse>
     */
    @ExceptionHandler(FenixException.class)
    public ResponseEntity<FenixExceptionResponse> handleWarehouseException(final FenixException fenixException) {
        final FenixExceptionResponse response = FenixExceptionResponse.of(fenixException.getStatus(),
                fenixException.getCode(), fenixException.getDetails());
        return new ResponseEntity<>(response, response.getStatus());
    }

    /**
     * Method to response unhandled exceptions
     *
     * @param runtimeException the generic runtime exception
     * @return ResponseEntity<WarehouseExceptionResponse>
     */
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<FenixExceptionResponse> handleRuntimeException(final RuntimeException runtimeException) {
        final FenixExceptionResponse wer = FenixExceptionResponse.of(HttpStatus.INTERNAL_SERVER_ERROR,
                ERRORS_GENERIC_CODE, String.format(ERRORS_GENERIC_MESSAGE, runtimeException.getMessage()));
        return new ResponseEntity<>(wer, wer.getStatus());
    }

    /**
     * Method to handle constraint exceptions
     *
     * @param constraintViolationException the constraint exception
     * @return ResponseEntity<WarehouseExceptionResponse>
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<FenixExceptionResponse> handleRuntimeException(
            final ConstraintViolationException constraintViolationException) {
        final FenixExceptionResponse wer = FenixExceptionResponse.of(HttpStatus.BAD_REQUEST, ERRORS_VALIDATION_CODE,
                String.format(ERRORS_VALIDATION_MESSAGE, constraintViolationException.getMessage()));
        return new ResponseEntity<>(wer, wer.getStatus());
    }

}
