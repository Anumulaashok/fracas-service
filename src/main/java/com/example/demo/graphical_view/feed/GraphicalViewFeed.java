package com.example.demo.graphical_view.feed;

import lombok.*;

import java.util.Date;

/**
 * The type Graphical view feed.
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class GraphicalViewFeed {
    /**
     * The Id.
     */
    private String id;
    /**
     * The Failure number.
     */
    private String failureNumber;
    /**
     * The Occurred date.
     */
    private String occurredDateTime;
    /**
     * The Rectified date.
     */
    private String rectifiedDateTime;
    /**
     * The Elr reference.
     */
    private String elrReference;
    /**
     * The Place name.
     */
    private String placeName;
    /**
     * The Failure details.
     */
    private String failureDetails;
    /**
     * The Failure cause.
     */
    private String failureCause;
    /**
     * The Failure notes.
     */
    private String failureNotes;
    /**
     * The Failure action.
     */
    private String failureAction;
    /**
     * The Failure category.
     */
    private String failureCategory;
    /**
     * The Status.
     */
//TODO NEED TO MAKE AS ENUM
    private String status;
    /**
     * The Project attribution supplier.
     */
    private String projectAttributionSupplier;
    /**
     * The Review notes.
     */
    private String reviewNotes;
    /**
     * The Root cause component details.
     */
    private String rootCauseComponentDetails;
    /**
     * The Attributable.
     */
//TODO need make it boolean
    private String attributable;
    /**
     * The Date.
     */
    private Date date;
}
