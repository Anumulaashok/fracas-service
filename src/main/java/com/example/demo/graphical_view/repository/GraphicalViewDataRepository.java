package com.example.demo.graphical_view.repository;

import com.example.demo.graphical_view.model.GraphicalViewDataVO;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * The interface Graphical view data repository.
 */
@Repository
public interface GraphicalViewDataRepository extends MongoRepository<GraphicalViewDataVO, String> {

    /**
     * Delete by failure number and occurred date.
     *
     * @param failureNumber the failure number
     * @param occurredDate  the occurred date
     * @return the graphical view feed
     */
    GraphicalViewDataVO findByFailureNumberAndOccurredDate(String failureNumber, String occurredDate);
}
