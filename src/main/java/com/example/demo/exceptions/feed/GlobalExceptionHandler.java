package com.example.demo.exceptions.feed;

import com.example.demo.graphical_view.feed.ExceptionResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Date;

/**
 * The type Exception handler.
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * Bad exception handler exception response.
     *
     * @param badRequest the bad request
     * @return the exception response
     */
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ExceptionResponse> badExceptionHandler(BadRequestException badRequest) {
        log.error(" bad request Exception message [{}] for cause", badRequest.getMessage(), badRequest.getCause());
        ExceptionResponse exceptionResponse = new ExceptionResponse(badRequest.getMessage(), new Date(), "400");
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);

    }

    /**
     * Bad exception handler exception response.
     *
     * @param e the e
     * @return the exception response
     */
//    @ExceptionHandler(Exception.class)
//    public ExceptionResponse badExceptionHandler(Exception e) {
//        return new ExceptionResponse(e.getMessage(), new Date(), "500");
//    }
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseEntity<ExceptionResponse> handleException(Exception e) {
        log.error("Exception message [{}] for cause", e.getMessage(), e.getCause());
        ExceptionResponse response = new ExceptionResponse(e.getMessage(), new Date(), "500");
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
