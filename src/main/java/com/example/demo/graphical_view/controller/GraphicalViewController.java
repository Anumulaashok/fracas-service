package com.example.demo.graphical_view.controller;

import com.example.demo.graphical_view.feed.BaseFilter;
import com.example.demo.graphical_view.feed.GraphicalViewFeed;
import com.example.demo.graphical_view.service.GraphicalViewDataService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * The type Graphical view controller.
 */
@RestController
@RequestMapping("/graphical")
@CrossOrigin(origins = "*")
@Slf4j
public class GraphicalViewController {

    /**
     * The Graphical view data service.
     */
    @Autowired
    private GraphicalViewDataService graphicalViewDataService;


    /**
     * Save customer data string.
     *
     * @param customerData the customer data
     * @param projectId    the project id
     * @param fileName     the file name
     * @return the string
     */
    @PostMapping("/customer/data")
    public String saveCustomerData(@RequestBody List<Map<String, Object>> customerData, @RequestParam String projectId, @RequestParam String fileName) {
        return graphicalViewDataService.saveCustomerData(customerData);
    }

    /**
     * Save project string.
     *
     * @param projectName the project name
     * @return the string
     */
    @PostMapping("/create/project")
    public String saveProject(@RequestParam String projectName){
        return graphicalViewDataService.saveProject(projectName);
    }

    /**
     * Gets graphical view feed.
     *
     * @return the graphical view feed
     */
    @GetMapping("/get/projects")
    public List<Map> getGraphicalViewFeed() {
        return graphicalViewDataService.getAllProjects();
    }

    /**
     * Gets graphical view feed.
     *
     * @param baseFilter the base filter
     * @return the graphical view feed
     */
    @GetMapping("/get/by/content")
    public List<GraphicalViewFeed> getGraphicalViewFeed(@RequestBody(required = false) BaseFilter baseFilter) {
        return graphicalViewDataService.getGraphicalViewFeed(baseFilter);
    }

}
