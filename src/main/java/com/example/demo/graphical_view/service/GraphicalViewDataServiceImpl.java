package com.example.demo.graphical_view.service;

import com.example.demo.exceptions.feed.BadRequestException;
import com.example.demo.graphical_view.Utils.GraphicalViewUtils;
import com.example.demo.graphical_view.feed.BaseFilter;
import com.example.demo.graphical_view.feed.GraphicalViewFeed;
import com.example.demo.graphical_view.model.GraphicalViewDataVO;
import com.example.demo.graphical_view.repository.GraphicalViewDataRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * The type Graphical view data.
 */
@Service
@Slf4j
public class GraphicalViewDataServiceImpl implements GraphicalViewDataService {
    /**
     * The Graphical view data repository.
     */
    @Autowired
    private GraphicalViewDataRepository graphicalViewDataRepository;
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

    /**
     * Save graphical view data string.
     *
     * @param graphicalViewFeedList the graphical view data vo list
     * @return the string
     */
    @Override
    public String saveGraphicalViewData(List<GraphicalViewFeed> graphicalViewFeedList) {
        log.info("Going to map data from feed to vo and saving ");
        List<GraphicalViewDataVO> graphicalViewDataVOList = new ArrayList<>();
        graphicalViewFeedList.forEach(graphicalViewFeed -> {
            log.info("Going to map  data [{}]", graphicalViewFeed.getOccurredDate());
            GraphicalViewDataVO graphicalViewDataVO = modelMapper.map(graphicalViewFeed, GraphicalViewDataVO.class);
            graphicalViewDataVO.setId(GraphicalViewUtils.generateUniqueHashId(graphicalViewDataVO.getFailureNumber(), graphicalViewDataVO.getOccurredDate()));
            graphicalViewDataVO.setDate(getDateByOccuredDate(graphicalViewDataVO));
            graphicalViewDataVOList.add(graphicalViewDataVO);
        });
        graphicalViewDataRepository.saveAll(graphicalViewDataVOList);
        return "success";
    }

    @Override
    public List<GraphicalViewFeed> getGraphicalViewDataList() {

        List<GraphicalViewDataVO> graphicalViewDataVOList = graphicalViewDataRepository.findAll();
        if (graphicalViewDataVOList.isEmpty()) {
            throw new RuntimeException("data not available");
        }
        return getGraphicalViewFeeds(graphicalViewDataVOList);
    }

    @Override
    public List<GraphicalViewFeed> getGraphicalViewFeed(BaseFilter baseFilter) {
        Criteria criteria = new Criteria();
        criteria.and("date").gte(baseFilter.getStartDate()).lte(baseFilter.getEndDate());
        if (baseFilter.getField() != null && baseFilter.getValue() != null) {
            criteria.and(baseFilter.getField()).is(baseFilter.getValue());
        }
        return mongoTemplate.aggregate(Aggregation.newAggregation(Aggregation.match(criteria)), GraphicalViewDataVO.class, GraphicalViewFeed.class).getMappedResults();
    }

    @Override
    public GraphicalViewFeed deleteGraphicalViewFeed(String failureNumber, String occurredDate) {
        log.info("Going to delete graphical view row for failure number [{} occurred date {}]", failureNumber, occurredDate);
        GraphicalViewDataVO viewFeed = graphicalViewDataRepository.findByFailureNumberAndOccurredDate(failureNumber, occurredDate);
        if (viewFeed == null) {
            throw new BadRequestException("Value doesn't exist with the value " + failureNumber);
        }
        graphicalViewDataRepository.delete(viewFeed);
        return modelMapper.map(viewFeed, GraphicalViewFeed.class);

    }

    /**
     * Sets date.
     *
     * @return
     */
    public Date getDateByOccuredDate(GraphicalViewDataVO graphicalViewDataVO) {
        if (graphicalViewDataVO.getOccurredDate() != null) {
            try {
                return new SimpleDateFormat("dd-MMM-yy").parse(graphicalViewDataVO.getOccurredDate());
            } catch (ParseException e) {
                log.error("Exception occur while parsing the date ", e);
            }
        }
        return null;
    }

    /**
     * Gets graphical view feeds.
     *
     * @param graphicalViewDataVOList the graphical view data vo list
     * @return the graphical view feeds
     */
    private List<GraphicalViewFeed> getGraphicalViewFeeds(List<GraphicalViewDataVO> graphicalViewDataVOList) {
        return graphicalViewDataVOList.stream().map(graphicalViewDataVO ->
                modelMapper.map(graphicalViewDataVO, GraphicalViewFeed.class)
        ).toList();
    }
}
