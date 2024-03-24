package com.example.demo.graphical_view.service;

import com.example.demo.graphical_view.feed.KpiFeed;
import com.example.demo.graphical_view.feed.LineChartFilter;

import java.util.List;
import java.util.Map;

/**
 * The interface Kpi service.
 */
public interface KpiService {
    /**
     * Gets all kpis.
     *
     * @return the all kpis
     */
    List<KpiFeed> getAllKpis();

    /**
     * Gets line chart.
     *
     * @param lineChartFilter the line chart filter
     * @return the line chart
     */
    List<Map> getLineChart(LineChartFilter lineChartFilter);

}
