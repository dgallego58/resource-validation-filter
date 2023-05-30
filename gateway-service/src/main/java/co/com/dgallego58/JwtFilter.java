package co.com.dgallego58;


import co.com.dgallego58.properties.GroupPath;

import java.util.List;

public class JwtFilter {

    private final GroupPath groupPath;

    public JwtFilter(GroupPath groupPath) {
        this.groupPath = groupPath;
    }

    public List<String> getPathsForGroup(GroupPath.GroupType type) {
        return groupPath.availablePathsFor(type);
    }
}
