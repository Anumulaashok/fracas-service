package com.example.demo.graphical_view.service;

import com.example.demo.graphical_view.feed.KpiFeed;
import com.example.demo.graphical_view.feed.LineChartFilter;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.GroupOperation;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import static com.example.demo.graphical_view.Utils.GraphicalViewUtils.GRAPHICAL_VIEW_DATA;
import static com.example.demo.graphical_view.Utils.GraphicalViewUtils.addMatchInCriteria;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.group;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.match;

/**
 * The type Kpi service.
 */
@Service
@Slf4j
public class KpiServiceImpl implements KpiService {


    /**
     * The constant baseKpiList.
     */
    public static final List<KpiFeed> baseKpiList = List.of(
            new KpiFeed("id", "Id", "STRING"),
            new KpiFeed("failureNumber", "Failure Number", "STRING"),
            new KpiFeed("occurredDateTime", "occurred Date Time", "DATE"),
            new KpiFeed("rectifiedDateTime", "rectified Date Time", "DATE"),
            new KpiFeed("elrReference", "Elr Reference", "STRING"),
            new KpiFeed("placeName", "place Name", "STRING"),
            new KpiFeed("failureDetails", "Failure Details", "STRING"),
            new KpiFeed("failureCause", "Failure Cause", "STRING"),
            new KpiFeed("failureNotes", "Failure Notes", "STRING"),
            new KpiFeed("failureAction", "Failure Action", "STRING"),
            new KpiFeed("failureCategory", "Failure Category", "STRING"),
            new KpiFeed("status", "Status", "STRING"), // Update ENUM type as per your requirement
            new KpiFeed("projectAttributionSupplier", "Project Attribution Supplier", "STRING"),
            new KpiFeed("reviewNotes", "Review Notes", "STRING"),
            new KpiFeed("rootCauseComponentDetails", "Root Cause Component Details", "STRING"),
            new KpiFeed("attributable", "Attributable", "BOOLEAN"),
            new KpiFeed("date", "Date", "DATE")
    );

    /**
     * The Mongo template.
     */
    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public List<KpiFeed> getAllKpis() {
        return baseKpiList;
    }


    @Override
    public List<Map> getLineChart(LineChartFilter lineChartFilter) {

        Criteria criteria = new Criteria();
        addMatchInCriteria(lineChartFilter, criteria);
        MatchOperation match = match(criteria);
        GroupOperation groupOperation = addMetrics(group(lineChartFilter.getDateField().getKey()).first(lineChartFilter.getDateField().getKey()).as(lineChartFilter.getDateField().getKey()), lineChartFilter);
        Aggregation aggregation = Aggregation.newAggregation(match, groupOperation, Aggregation.project().andExclude("_id"), Aggregation.sort(Sort.Direction.ASC, lineChartFilter.getDateField().getKey()));
        List<Map> mappedResults = mongoTemplate.aggregate(aggregation, GRAPHICAL_VIEW_DATA, Map.class).getMappedResults();
        if (!mappedResults.isEmpty()) {
            try {
                log.info("Mapped data [{}]", new ObjectMapper().writeValueAsString(mappedResults));
            } catch (Exception w) {

            }
        }
        return mappedResults;
    }

    /**
     * Add metrics group operation.
     *
     * @param group           the group
     * @param lineChartFilter the line chart filter
     * @return the group operation
     */
    private GroupOperation addMetrics(GroupOperation group, LineChartFilter lineChartFilter) {
        if (lineChartFilter.getMetricList().isEmpty()) {
            group = group.count().as("count");
        } else {
            for (KpiFeed kpiFeed : lineChartFilter.getMetricList()) {
                if (Objects.equals(kpiFeed.getDataType(), "BOOLEAN")) {
                    group = group.count().as(kpiFeed.getKey());
                } else {
                    //intentinally kept the same ijn if and else we have to improve
                    group = group.count().as(kpiFeed.getKey());
                }
            }
        }
        return group;
    }
}
