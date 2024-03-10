package com.example.demo.graphical_view.service;

import com.example.demo.graphical_view.feed.BaseFilter;
import com.example.demo.graphical_view.feed.GraphicalViewFeed;

import java.util.List;
import java.util.Map;

/**
 * The type Graphical view data service.
 */
public interface GraphicalViewDataService {

    /**
     * Gets graphical view data list.
     *
     * @return the graphical view data list
     */
    List<Map> getAllProjects();

    /**
     * Gets graphical view feed.
     *
     * @param baseFilter the base filter
     * @return the graphical view feed
     */
    List<GraphicalViewFeed> getGraphicalViewFeed(BaseFilter baseFilter);


    /**
     * Save customer data string.
     *
     * @param customerData the customer data
     * @return the string
     */
    String saveCustomerData(List<Map<String, Object>> customerData);

    /**
     * Save project string.
     *
     * @param projectName the project name
     * @return the string
     */
    String saveProject(String projectName);
}
