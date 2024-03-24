package com.example.demo.graphical_view.feed;

import lombok.*;

import java.util.Date;

/**
 * The type Base filter.
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class BaseFilter {

    /**
     * The Start date.
     */
    private Date startDate;
    /**
     * The End date.
     */
    private Date endDate;
    /**
     * The Project id.
     */
    private String projectId;
    /**
     * The Sheet name.
     */
    private String sheetName;
    /**
     * The Field.
     */
    private String field;
    /**
     * The Value.
     */
    private String value;
    /**
     * The Date.
     */
    private KpiFeed dateField;
}
