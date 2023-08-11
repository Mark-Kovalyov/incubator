package mayton.adf.entities;

import java.util.List;

public class AdfDependency {

    private String activity;
    private List<AdfDependencyCondition> dependencyConditions;

    public AdfDependency(String activity, List<AdfDependencyCondition> dependencyConditions) {
        this.activity = activity;
        this.dependencyConditions = dependencyConditions;
    }
}
