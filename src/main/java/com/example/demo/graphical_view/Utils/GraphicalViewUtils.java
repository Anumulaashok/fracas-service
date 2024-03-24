package com.example.demo.graphical_view.Utils;


import com.example.demo.graphical_view.feed.BaseFilter;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * The type Graphical view utils.
 */
@Component
public class GraphicalViewUtils {


    /**
     * The constant GRAPHICAL_VIEW_DATA.
     */
    public static final String GRAPHICAL_VIEW_DATA = "graphical_view_data";
    /**
     * The constant PROJECT_CONFIG.
     */
    public static final String PROJECT_CONFIG = "project_config";
    /**
     * The constant ACTIVE.
     */
    public static final String ACTIVE = "active";
    /**
     * The constant DELETED.
     */
    public static final String DELETED = "deleted";

    /**
     * Generate unique hash id string.
     *
     * @param values the values
     * @return the string
     */
    public static String generateUniqueHashId(String... values) {
        try {
            // Concatenate all the values into a single string
            StringBuilder concatenatedString = new StringBuilder();
            for (String value : values) {
                concatenatedString.append(value);
            }

            // Hash the concatenated string using SHA-256
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(concatenatedString.toString().getBytes(StandardCharsets.UTF_8));

            // Convert the hash bytes to a hexadecimal string representation
            StringBuilder hashIdBuilder = new StringBuilder();
            for (byte b : hashBytes) {
                hashIdBuilder.append(String.format("%02x", b));
            }

            // Return the unique hash ID
            return hashIdBuilder.toString();
        } catch (NoSuchAlgorithmException e) {
            // Handle any exceptions related to hashing algorithm
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Add match in criteria.
     *
     * @param baseFilter the base filter
     * @param criteria   the criteria
     */
    public static void addMatchInCriteria(BaseFilter baseFilter, Criteria criteria) {
        if (baseFilter.getProjectId() != null) {
            criteria.and("projectId").is(baseFilter.getProjectId());
        }
        if (baseFilter.getSheetName() != null) {
            criteria.and("sheetName").is(baseFilter.getSheetName().trim());
        }
        if (baseFilter.getStartDate() != null) {
            criteria.and(baseFilter.getDateField() == null || baseFilter.getDateField().getKey() == null ? "date" : baseFilter.getDateField().getKey()).gte(baseFilter.getStartDate()).lte(baseFilter.getEndDate());
        }
        if (baseFilter.getField() != null && baseFilter.getValue() != null) {
            criteria.and(baseFilter.getField()).is(baseFilter.getValue());
        }
    }

}
