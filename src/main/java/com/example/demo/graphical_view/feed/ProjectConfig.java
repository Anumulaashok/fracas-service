package com.example.demo.graphical_view.feed;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * The type Project.
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Document(collection = "project_config")
public class ProjectConfig {

    /**
     * The Id.
     */
    private String id;

    /**
     * The Project name.
     */
    private String projectName;
    /**
     * The Project list.
     */
    private Set<String> ProjectList = new HashSet<>();
    /**
     * The Is active.
     */
    private boolean active = true;
    /**
     * The Is deleted.
     */
    private boolean deleted = false;
    /**
     * The Updated on.
     */
    private Date updatedOn;
}
