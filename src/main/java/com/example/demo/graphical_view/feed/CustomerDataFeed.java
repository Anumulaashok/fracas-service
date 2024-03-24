package com.example.demo.graphical_view.feed;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * The type Customer data feed.
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CustomerDataFeed {
    /**
     * The Project id.
     */
    private String projectId;
    /**
     * The Project name.
     */
    private String projectName;
    /**
     * The Sheet name.
     */
    private String sheetName;
    /**
     * The Date.
     */
    private Date date;
    /**
     * The Force add.
     */
    private boolean forceAdd;
    /**
     * The Data.
     */
    private List<Map<String, Object>> data;
}
