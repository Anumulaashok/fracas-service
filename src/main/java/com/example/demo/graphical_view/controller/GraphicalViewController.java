package com.example.demo.graphical_view.controller;

import com.example.demo.graphical_view.feed.*;
import com.example.demo.graphical_view.service.GraphicalViewDataService;
import com.example.demo.graphical_view.service.KpiService;
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
@CrossOrigin(origins = {"*", "localhost:3000"})
@Slf4j
public class GraphicalViewController {

    /**
     * The Graphical view data service.
     */
    @Autowired
    private GraphicalViewDataService graphicalViewDataService;
    /**
     * The Kpi service.
     */
    @Autowired
    private KpiService kpiService;


    /**
     * Save customer data string.
     *
     * @param customerData the customer data
     * @return the string
     */
    @PostMapping("/customer/data")
    public String saveCustomerData(@RequestBody CustomerDataFeed customerData) {
        return graphicalViewDataService.saveCustomerData(customerData);
    }

    /**
     * Save project string.
     *
     * @param projectConfig the project config
     * @return the string
     */
    @PostMapping("/create/project")
    public String saveProject(@RequestBody ProjectConfig projectConfig) {
        return graphicalViewDataService.saveProject(projectConfig);
    }

    /**
     * Gets graphical view feed.
     *
     * @return the graphical view feed
     */
    @GetMapping("/get/projects")
    public List<ProjectConfig> getGraphicalViewFeed() {
        return graphicalViewDataService.getAllProjects();
    }

    /**
     * Gets kpis.
     *
     * @return the kpis
     */
    @GetMapping("/kpis")
    public List<KpiFeed> getKpis() {
        return kpiService.getAllKpis();
    }

    /**
     * Gets graphical view feed.
     *
     * @param baseFilter the base filter
     * @return the graphical view feed
     */
    @GetMapping("/get/data")
    public List<GraphicalViewFeed> getGraphicalViewFeed(@RequestBody(required = false) BaseFilter baseFilter) {
        return graphicalViewDataService.getGraphicalViewFeed(baseFilter);
    }

    /**
     * Gets line chart.
     *
     * @param lineChartFilter the base filter
     * @return the line chart
     */
    @PostMapping("/line")
    public List<Map> getLineChart(@RequestBody LineChartFilter lineChartFilter) {
        return kpiService.getLineChart(lineChartFilter);
    }

//    @GetMapping("/get/data/download")
//    public ResponseEntity<Resource> getGraphicalViewFeedDownload(@RequestBody(required = false) BaseFilter baseFilter) {
//        return graphicalViewDataService.getGraphicalViewFeedDownload(baseFilter);
//    }
}
