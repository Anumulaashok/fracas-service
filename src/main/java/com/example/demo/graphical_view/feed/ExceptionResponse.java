package com.example.demo.graphical_view.feed;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

/**
 * The type Exception response.
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ExceptionResponse {

    /**
     * The Message.
     */
    private String message;
    /**
     * The Date.
     */
    private Date date;

    /**
     * The Error code.
     */
    private String errorCode;
}
