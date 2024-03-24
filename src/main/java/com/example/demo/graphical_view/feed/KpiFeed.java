package com.example.demo.graphical_view.feed;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * The type Kpi feed.
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class KpiFeed {

    /**
     * The Key.
     */
    private String key;
    /**
     * The Display name.
     */
    private String displayName;
    /**
     * The Data type.
     */
    private String dataType;

}
