package com.example.demo.graphical_view.feed;

import lombok.*;

import java.util.List;

/**
 * The type Line chart filter.
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class LineChartFilter extends BaseFilter {
    /**
     * The Metric.
     */
    private List<KpiFeed> metricList;
}
