package com.example.demo.graphical_view.controller;

import com.example.demo.graphical_view.feed.BaseFilter;
import com.example.demo.graphical_view.feed.GraphicalViewFeed;
import com.example.demo.graphical_view.service.GraphicalViewDataService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * The type Graphical view controller.
 */
@RestController
@RequestMapping("/graphical")
@CrossOrigin(origins = "http://localhost:3000")
@Slf4j
public class GraphicalViewController {

    /**
     * The Graphical view data service.
     */
    @Autowired
    private GraphicalViewDataService graphicalViewDataService;

    /**
     * Save graphical view data string.
     *
     * @param graphicalViewDataVOList the graphical view data vo list
     * @return the string
     */
    @PostMapping("/save/data")
    public String saveGraphicalViewData(@RequestBody List<GraphicalViewFeed> graphicalViewDataVOList) {
        return graphicalViewDataService.saveGraphicalViewData(graphicalViewDataVOList);
    }

    /**
     * Gets graphical view feed.
     *
     * @return the graphical view feed
     */
    @GetMapping("/get")
    public List<GraphicalViewFeed> getGraphicalViewFeed() {
        return graphicalViewDataService.getGraphicalViewDataList();
    }

    /**
     * Gets graphical view feed.
     *
     * @param baseFilter the base filter
     * @return the graphical view feed
     */
    @GetMapping("/get/by/content")
    public List<GraphicalViewFeed> getGraphicalViewFeed(@RequestBody BaseFilter baseFilter) {
        return graphicalViewDataService.getGraphicalViewFeed(baseFilter);
    }

    /**
     * Delete graphical view feed graphical view feed.
     *
     * @param failureNumber the failure number
     * @param occurredDate  the occurred date
     * @return the graphical view feed
     */
    @DeleteMapping("/delete/{failureNumber}")
    public GraphicalViewFeed deleteGraphicalViewFeed(@PathVariable String failureNumber, @RequestParam String occurredDate) {
        return graphicalViewDataService.deleteGraphicalViewFeed(failureNumber.trim(), occurredDate.trim());
    }
}
