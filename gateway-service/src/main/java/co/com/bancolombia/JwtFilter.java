package co.com.bancolombia;


import bancolombia.properties.GroupPath;

public class JwtFilter {


    private GroupPath groupPath;

    public JwtFilter(GroupPath groupPath) {
        this.groupPath = groupPath;
    }

    public void doSomething() {
        groupPath.getGroupPaths();
    }
}
