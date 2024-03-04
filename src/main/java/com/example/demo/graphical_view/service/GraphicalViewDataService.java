package com.example.demo.graphical_view.service;

import com.example.demo.graphical_view.feed.BaseFilter;
import com.example.demo.graphical_view.feed.GraphicalViewFeed;

import java.util.List;

/**
 * The type Graphical view data service.
 */
public interface GraphicalViewDataService {
    /**
     * Save graphical view data string.
     *
     * @param graphicalViewDataVOList the graphical view data vo list
     * @return the string
     */
    String saveGraphicalViewData(List<GraphicalViewFeed> graphicalViewDataVOList);

    /**
     * Gets graphical view data list.
     *
     * @return the graphical view data list
     */
    List<GraphicalViewFeed> getGraphicalViewDataList();

    /**
     * Gets graphical view feed.
     *
     * @param baseFilter the base filter
     * @return the graphical view feed
     */
    List<GraphicalViewFeed> getGraphicalViewFeed(BaseFilter baseFilter);

    /**
     * Delete graphical view feed graphical view feed.
     *
     * @param failureNumber the failure number
     * @param occurredDateTime  the occurred date Time
     * @return the graphical view feed
     */
    GraphicalViewFeed deleteGraphicalViewFeed(String failureNumber, String occurredDateTime);
}
