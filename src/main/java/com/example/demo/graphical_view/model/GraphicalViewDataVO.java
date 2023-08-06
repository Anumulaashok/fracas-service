package com.example.demo.graphical_view.model;

import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

/**
 * The type Graphical view data vo.
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Slf4j
@Document("graphical_view_data")
public class GraphicalViewDataVO {
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
    private String occurredDate;
    /**
     * The Rectified date.
     */
    private String rectifiedDate;
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
     * The Created at.
     */
    private Date createdAt = new Date();
    /**
     * The Date.
     */
    private Date date;
}
