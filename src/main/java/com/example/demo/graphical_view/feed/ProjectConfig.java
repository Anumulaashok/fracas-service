package com.example.demo.graphical_view.feed;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * The type Project.
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
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
    private List<String> ProjectList;
}
