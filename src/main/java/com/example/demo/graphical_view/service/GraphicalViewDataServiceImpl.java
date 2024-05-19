package com.example.demo.graphical_view.service;

import com.example.demo.exceptions.feed.BadRequestException;
import com.example.demo.graphical_view.Utils.GraphicalViewUtils;
import com.example.demo.graphical_view.feed.BaseFilter;
import com.example.demo.graphical_view.feed.CustomerDataFeed;
import com.example.demo.graphical_view.feed.GraphicalViewFeed;
import com.example.demo.graphical_view.feed.ProjectConfig;
import com.fasterxml.jackson.databind.util.StdDateFormat;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.SortOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.example.demo.graphical_view.Utils.GraphicalViewUtils.*;
import static org.springframework.data.mongodb.core.query.Query.query;

/**
 * The type Graphical view data.
 */
@Service
@Slf4j
public class GraphicalViewDataServiceImpl implements GraphicalViewDataService {

    /**
     * The Date map.
     */
    static List<String> dateMap = new ArrayList<>();
    /**
     * The Occured date time key list.
     */
    static List<String> occuredDateTimeKeyList = List.of("Incident Start", "Failure Datetime", "Failed Datetime", "OCCUR_DATE_TIME", "Occurred Date", "Failed", "Occurred Datetime", "Occurred Datetime");
    /**
     * The Recitified date time key list.
     */
    static List<String> recitifiedDateTimeKeyList = List.of("RECTIFY_DATE _TIME", "Rectified", "Rectified Datetime");

    static {
        dateMap.addAll(List.of("occurredDateTime", "rectifiedDateTime"));
        dateMap.addAll(occuredDateTimeKeyList);
        dateMap.addAll(recitifiedDateTimeKeyList);
    }

    /**
     * The Failure number key list.
     */
    List<String> failureNumberKeyList = List.of("Fault Number", "Failure Number", "FAILURE_ID", "Fault No.", "FMS");
    /**
     * The Elr referance key list.
     */
    List<String> elrReferanceKeyList = List.of("ELR Reference", "ELR");
    /**
     * The Place name key list.
     */
    List<String> placeNameKeyList = List.of("Start/At Location", "PLACE_NAME", "Place", "Place Name");
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
        criteria.and(ACTIVE).is(true);
        SortOperation sortOperation = null;
        if (baseFilter != null) {
            addMatchInCriteria(baseFilter, criteria);
            if (baseFilter.getSortField() != null) {
                sortOperation = Aggregation.sort(Sort.Direction.ASC, baseFilter.getSortField());
            }
        }
        Aggregation aggregation = sortOperation != null ? Aggregation.newAggregation(Aggregation.match(criteria), sortOperation) : Aggregation.newAggregation(Aggregation.match(criteria));
        log.info("aggregation [{}]", aggregation);
        List<Map> documents = mongoTemplate.aggregate(aggregation, GRAPHICAL_VIEW_DATA, Map.class).getMappedResults();
        if (!documents.isEmpty()) {
            List<GraphicalViewFeed> list = documents.stream().map(graphicalViewFeed -> {
                GraphicalViewFeed graphicalViewFeed1 = new GraphicalViewFeed();
                modelMapper.map(graphicalViewFeed, graphicalViewFeed1);
                graphicalViewFeed1.setOccurredDateTime(dateToString(graphicalViewFeed.get("occurredDateTime")));
                graphicalViewFeed1.setRectifiedDateTime(dateToString(graphicalViewFeed.get("rectifiedDateTime")));
                return graphicalViewFeed1;
            }).toList();
            log.info("Mapped results [{}]", list);
            return list;

        }
        return new ArrayList<>();
    }


    //    @Override
//    public ResponseEntity<Resource> getGraphicalViewFeedDownload(BaseFilter baseFilter) {
//        List<GraphicalViewFeed> dataList = getGraphicalViewFeed(baseFilter);
//
//        byte[] documentBytes = downloadDataAsWord(dataList);
//        ByteArrayResource resource = new ByteArrayResource(documentBytes);
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentDispositionFormData("attachment", fileName + ".docx");
//        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
//
//        return ResponseEntity.ok()
//                .headers(headers)
//                .body(resource);
//        return null;
//    }
//    public static byte[] downloadDataAsWord(List<Map<String, Object>> dataList) throws IOException {
//        try (XWPFDocument document = new XWPFDocument()) {
//            // Create paragraph for headers
//            XWPFParagraph headerParagraph = document.createParagraph();
//            XWPFRun headerRun = headerParagraph.createRun();
//            headerRun.setBold(true);
//            headerRun.setText("Data:");
//
//            // Get headers dynamically from the first map in the dataList
//            String[] headers = dataList.isEmpty() ? new String[0] : dataList.get(0).keySet().toArray(new String[0]);
//
//            // Create table
//            XWPFTable table = document.createTable(dataList.size() + 1, headers.length);
//
//            // Set headers
//            XWPFTableRow headerRow = table.getRow(0);
//            for (int i = 0; i < headers.length; i++) {
//                headerRow.getCell(i).setText(headers[i]);
//            }
//
//            // Populate data rows
//            for (int i = 0; i < dataList.size(); i++) {
//                Map<String, Object> data = dataList.get(i);
//                XWPFTableRow row = table.getRow(i + 1);
//                for (int j = 0; j < headers.length; j++) {
//                    Object value = data.get(headers[j]);
//                    row.getCell(j).setText(value != null ? value.toString() : "");
//                }
//            }
//
//            // Write document content to ByteArrayOutputStream
//            try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
//                document.write(outputStream);
//                return outputStream.toByteArray();
//            }
//        }
//    }
    @Override
    public List<ProjectConfig> getAllProjects() {
        log.debug("Going to get the projects");
        Query query = query(new Criteria(ACTIVE).is(true).and(GraphicalViewUtils.DELETED).is(false));
        List<ProjectConfig> mappedResults = mongoTemplate.find(query, ProjectConfig.class, PROJECT_CONFIG);
        log.debug("Got the results [{}]", mappedResults);
        return mappedResults;

    }

    @Override
    public String saveProject(ProjectConfig projectConfig) {
        projectConfig.setProjectName(projectConfig.getProjectName().trim());
        log.info("Going to save project for projectConfig [{}]", projectConfig);
        Query projectName = query(new Criteria("projectName").is(projectConfig.getProjectName()).and(ACTIVE).is(true).and(GraphicalViewUtils.DELETED).is(false));
        List<ProjectConfig> maps = mongoTemplate.find(projectName, ProjectConfig.class, PROJECT_CONFIG);
        log.info("project config size [{}] for projectConfig [{}]", maps.size(), projectConfig);
        if (maps.isEmpty()) {
            return mongoTemplate.save(projectConfig, PROJECT_CONFIG).getId();
        } else {
            throw new BadRequestException("Project Name already exist with id : " + maps.get(0).getId());
        }
    }


    @Override
    public String saveCustomerData(CustomerDataFeed customerData) {
        log.info("customer data [{}]", customerData);
        if (customerData == null || customerData.getData().isEmpty()) {
            throw new BadRequestException("data must not be empty");
        }
        customerData.setSheetName(customerData.getSheetName().trim());
        checkValidProject(customerData);
        Set<String> keys = customerData.getData().get(0).keySet();
        Map<String, String> mappedData = getMappedData(keys);
        customerData.getData().forEach(data -> {
            try {
                if (data.get("Failure Number") != null && !data.get("Failure Number").equals("x")) {
                    Map<String, Object> graphData = new HashMap<>();
                    for (String key : keys) {
                        if (dateMap.contains(mappedData.get(key))) {
                            log.info("key [{}] Date [{}] value [{}]", key, mappedData.get(key), data.get(key));
                            graphData.put(mappedData.get(key), stringToDate(data.get(key)));
                        } else {
                            graphData.put(mappedData.get(key), data.get(key));
                        }
                    }
                    graphData.put("projectId", customerData.getProjectId());
                    graphData.put("sheetName", customerData.getSheetName());
                    graphData.put(ACTIVE, true);
                    graphData.put(GraphicalViewUtils.DELETED, false);
                    graphData.put("date", customerData.getDate());
                    graphData.put("createdOn", new Date());
                    log.info("Going to save data [{}]", graphData);
                    mongoTemplate.save(graphData, GRAPHICAL_VIEW_DATA);
                }
            } catch (Exception e) {
                log.error("exception occured while saving data ");
                //todo need to inform user
            }
        });
        return "success";
    }

    /**
     * Gets mapped data.
     *
     * @param keys the keys
     * @return the mapped data
     */
    private Map<String, String> getMappedData(Set<String> keys) {
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
        return mappedData;
    }

    /**
     * Check valid project.
     *
     * @param customerDataFeed the customer data feed
     */
    private void checkValidProject(CustomerDataFeed customerDataFeed) {
        Query query = query(new Criteria("_id").is(customerDataFeed.getProjectId()).and(ACTIVE).is(true).and(GraphicalViewUtils.DELETED).is(false));
        List<ProjectConfig> mappedResults = mongoTemplate.find(query, ProjectConfig.class, PROJECT_CONFIG);
        if (mappedResults.isEmpty()) {
            throw new BadRequestException("Not a valid project");
        }
        ProjectConfig projectConfig = mappedResults.get(0);
        Optional<String> first = projectConfig.getProjectList().stream().filter(s -> s.equals(customerDataFeed.getSheetName())).findFirst();
        if (first.isEmpty() || customerDataFeed.isForceAdd()) {
            projectConfig.getProjectList().add(customerDataFeed.getSheetName());
            projectConfig.setUpdatedOn(new Date());
            mongoTemplate.save(projectConfig);
        } else {
            log.error("Sheet name is already present that means data already present for that sheet");
            throw new BadRequestException("Data present for this sheet");
        }
    }

    /**
     * Gets date by occured date.
     *
     * @param date the date
     * @return the date by occured date
     */
    public Date stringToDate(Object date) {
        try {
            log.info("date [{}]", date);
            return new StdDateFormat().parse((String) date);
        } catch (ParseException e) {
            if (date != null) {
                ((String) date).replace(" ", "T");
                try {
                    return new SimpleDateFormat("dd-MMM-yy").parse(date.toString());
                } catch (ParseException ex) {
                    try {
                        new SimpleDateFormat("yyyy-MM-dd").parse((String) date);
                    } catch (ParseException exception) {
                        log.error("Exception occur while parsing 3rd time date ", e);
                    }
                }
            }
            log.error("Exception occur while parsing the date ", e);
        } catch (NullPointerException e) {
            log.error("Value is null so ignore");
        }
        return null;
    }

    /**
     * Date to string string.
     *
     * @param date the date
     * @return the string
     */
    public String dateToString(Object date) {
        if (date != null) {
            try {
                return new SimpleDateFormat("dd-MMM-yy").format(date);
            } catch (Exception e) {
                log.error("Exception while converting the date [{}]", date);
                return new StdDateFormat().format(date);
            }
        } else {
            return null;
        }
    }

}
