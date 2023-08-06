package com.example.demo.exceptions.feed;

import com.example.demo.graphical_view.feed.ExceptionResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
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
    public ExceptionResponse badExceptionHandler(BadRequestException badRequest) {
        log.error("Exception message [{}] for cause", badRequest.getMessage(), badRequest.getCause());
        return new ExceptionResponse(badRequest.getMessage(), new Date(), "400");
    }

    /**
     * Bad exception handler exception response.
     *
     * @param e the e
     * @return the exception response
     */
    @ExceptionHandler(Exception.class)
    public ExceptionResponse badExceptionHandler(Exception e) {
        log.error("Exception message [{}] for cause", e.getMessage(), e.getCause());
        return new ExceptionResponse(e.getMessage(), new Date(), "500");
    }

}
