package com.example.demo.graphical_view.service;

import com.example.demo.graphical_view.feed.BaseFilter;
import com.example.demo.graphical_view.feed.CustomerDataFeed;
import com.example.demo.graphical_view.feed.GraphicalViewFeed;
import com.example.demo.graphical_view.feed.ProjectConfig;

import java.util.List;

/**
 * The type Graphical view data service.
 */
public interface GraphicalViewDataService {

    /**
     * Gets graphical view data list.
     *
     * @return the graphical view data list
     */
    List<ProjectConfig> getAllProjects();

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
    String saveCustomerData(CustomerDataFeed customerData);

    /**
     * Save project string.
     *
     * @param projectConfig the project name
     * @return the string
     */
    String saveProject(ProjectConfig projectConfig);

    /**
     * Gets graphical view feed download.
     *
     * @param baseFilter the base filter
     * @return the graphical view feed download
     */
//    ResponseEntity<Resource> getGraphicalViewFeedDownload(BaseFilter baseFilter);
}
