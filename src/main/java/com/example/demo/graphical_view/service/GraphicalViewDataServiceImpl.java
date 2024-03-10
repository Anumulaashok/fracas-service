package com.example.demo.graphical_view.service;

import com.example.demo.exceptions.feed.BadRequestException;
import com.example.demo.graphical_view.feed.BaseFilter;
import com.example.demo.graphical_view.feed.GraphicalViewFeed;
import com.example.demo.graphical_view.feed.ProjectConfig;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * The type Graphical view data.
 */
@Service
@Slf4j
public class GraphicalViewDataServiceImpl implements GraphicalViewDataService {

    /**
     * The constant GRAPHICAL_VIEW_DATA.
     */
    public static final String GRAPHICAL_VIEW_DATA = "graphical_view_data";
    /**
     * The constant PROJECT_CONFIG.
     */
    public static final String PROJECT_CONFIG = "project_config";
    /**
     * The Failure number key list.
     */
    List<String> failureNumberKeyList = List.of("Fault Number", "FAILURE_ID", "Fault No.", "FMS");
    /**
     * The Occured date time key list.
     */
    List<String> occuredDateTimeKeyList = List.of("Incident Start", "Failed Datetime", "OCCUR_DATE_TIME", "Occurred Date", "Failed", "Occurred Datetime");
    /**
     * The Recitified date time key list.
     */
    List<String> recitifiedDateTimeKeyList = List.of("Rectified Datetime", "RECTIFY_DATE _TIME", "Rectified", "Rectified Datetime");
    /**
     * The Elr referance key list.
     */
    List<String> elrReferanceKeyList = List.of("ELR Reference", "ELR", "ELR Reference");
    /**
     * The Place name key list.
     */
    List<String> placeNameKeyList = List.of("Place Name", "Start/At Location", "Place Name", "PLACE_NAME", "Place", "Place Name");
    /**
     * The Failure details key list.
     */
    List<String> failureDetailsKeyList = List.of("Failure Details", "Incident Title", "Event Detail", "NOTES_DETAILS", "Details", "Failure Details");
    /**
     * The Failure cause key list.
     */
    List<String> failureCauseKeyList = List.of("Failure cause", "NOTES_ CAUSE", "Cause", "Failure Cause");
    /**
     * The Failure action key list.
     */
    List<String> failureActionKeyList = List.of("Failure actions", "NOTES_ACTIONS", "Action", "Failure Actions");
    /**
     * The Failure notes key list.
     */
    List<String> failureNotesKeyList = List.of("Failure Notes", "Events");
    /**
     * The Model mapper.
     */
    @Autowired
    private ModelMapper modelMapper;
    /**
     * The Mongo template.
     */
    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public List<GraphicalViewFeed> getGraphicalViewFeed(BaseFilter baseFilter) {
        Criteria criteria = new Criteria();
        criteria.and("active").is(true);
        if (baseFilter != null && baseFilter.getProjectId() != null) {
            criteria.and("projectId").is(baseFilter.getProjectId());
        }
        if (baseFilter != null) {
            criteria.and("date").gte(baseFilter.getStartDate()).lte(baseFilter.getEndDate());
            if (baseFilter.getField() != null && baseFilter.getValue() != null) {
                criteria.and(baseFilter.getField()).is(baseFilter.getValue());
            }
        }
        Aggregation aggregation = Aggregation.newAggregation(Aggregation.match(criteria));
        log.info("aggregation [{}]", aggregation);
        List<GraphicalViewFeed> documents = mongoTemplate.aggregate(aggregation, GRAPHICAL_VIEW_DATA, GraphicalViewFeed.class).getMappedResults();
        log.info("mapped results {}", documents);
        return documents;
    }


    @Override
    public List<ProjectConfig> getAllProjects() {
        log.debug("Going to get the projects");
        List<ProjectConfig> mappedResults = mongoTemplate.findAll(ProjectConfig.class, PROJECT_CONFIG);
        log.debug("Got the results [{}]", mappedResults);
        return mappedResults;
    }

    @Override
    public String saveProject(String projectName) {
        ProjectConfig project = new ProjectConfig();
        project.setProjectName(projectName);
        return mongoTemplate.save(project, PROJECT_CONFIG).getId();
    }

    @Override
    public String saveCustomerData(List<Map<String, Object>> customerData, String projectId) {
        log.info("customer data [{}]", customerData);
        if (customerData == null || customerData.isEmpty()) {
            throw new BadRequestException("data must not be empty");
        }
        Set<String> keys = customerData.get(0).keySet();
        Map<String, String> mappedData = new HashMap<>();
        keys.forEach(displayName -> {
            if (failureNumberKeyList.contains(displayName)) {
                mappedData.put(displayName, "failureNumber");
            } else if (occuredDateTimeKeyList.contains(displayName)) {
                mappedData.put(displayName, "occurredDateTime");
            } else if (recitifiedDateTimeKeyList.contains(displayName)) {
                mappedData.put(displayName, "rectifiedDateTime");
            } else if (elrReferanceKeyList.contains(displayName)) {
                mappedData.put(displayName, "elrReference");
            } else if (placeNameKeyList.contains(displayName)) {
                mappedData.put(displayName, "placeName");
            } else if (failureDetailsKeyList.contains(displayName)) {
                mappedData.put(displayName, "failureDetails");
            } else if (failureCauseKeyList.contains(displayName)) {
                mappedData.put(displayName, "failureCause");
            } else if (failureActionKeyList.contains(displayName)) {
                mappedData.put(displayName, "failureAction");
            } else if (failureNotesKeyList.contains(displayName)) {
                mappedData.put(displayName, "failureNotes");
            } else if (!displayName.equals("null")) {
                mappedData.put(displayName, displayName);
            }
        });
        customerData.forEach(data -> {
            Map<String, Object> graphData = new HashMap<>();
            for (String key : mappedData.keySet()) {
                graphData.put(mappedData.get(key), data.get(key));
            }
            log.info("data [{}]", graphData);
            graphData.put("projectId", projectId);
            graphData.put("active", true);
            mongoTemplate.save(graphData, "graphical_view_data");
        });
        return "success";
    }

    /**
     * Sets date.
     *
     * @param date the date
     * @return date by occured date
     */
    public Date getDateByOccuredDate(String date) {
        try {
            return new SimpleDateFormat("dd-MMM-yy").parse(date);
        } catch (ParseException e) {
            log.error("Exception occur while parsing the date ", e);
        }
        return null;
    }

}
