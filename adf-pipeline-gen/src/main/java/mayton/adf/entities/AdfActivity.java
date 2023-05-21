package mayton.adf.entities;

import java.util.ArrayList;
import java.util.List;

public class AdfActivity {

    private String name;
    private AdfActivityTypes type;
    private List<AdfDependency> dependsOn;
    private AdfPolicy policy;
    private List<String> userProperties;
    private AdfTypeProperties typeProperties;
    private AdfLinkedService linkedServiceName;

    private AdfActivity(String name, AdfActivityTypes type, List<AdfDependency> dependsOn, AdfPolicy policy,
                        List<String> userProperties, AdfTypeProperties typeProperties, AdfLinkedService linkedServiceName) {
        this.name = name;
        this.type = type;
        this.dependsOn = dependsOn;
        this.policy = policy;
        this.userProperties = userProperties;
        this.typeProperties = typeProperties;
        this.linkedServiceName = linkedServiceName;
    }

    public Builder cloneBuilder() {
        return new Builder(name, type, dependsOn, policy, userProperties, typeProperties, linkedServiceName);
    }

    public static class Builder {

        private String name;
        private AdfActivityTypes type;
        private List<AdfDependency> dependsOn = new ArrayList<>();
        private AdfPolicy policy;
        private List<String> userProperties = new ArrayList<>();
        private AdfTypeProperties typeProperties;
        private AdfLinkedService linkedServiceName;

        public Builder() {

        }

        public Builder(String name, AdfActivityTypes type, List<AdfDependency> dependsOn, AdfPolicy policy,
                       List<String> userProperties, AdfTypeProperties typeProperties, AdfLinkedService linkedServiceName) {
            this.name = name;
            this.type = type;
            this.dependsOn = dependsOn;
            this.policy = policy;
            this.userProperties = userProperties;
            this.typeProperties = typeProperties;
            this.linkedServiceName = linkedServiceName;
        }

        public Builder withLinkedService(AdfLinkedService linkedServiceName) {
            this.linkedServiceName = linkedServiceName;
            return this;
        }

        public Builder withName(String name) {
            this.name = name;
            return this;
        }

        public Builder withPolicy(AdfPolicy policy) {
            this.policy = policy;
            return this;
        }

        public Builder withTypeProperties(AdfTypeProperties adp) {
            this.typeProperties = adp;
            return this;
        }

        public Builder withType(AdfActivityTypes type) {
            this.type = type;
            return this;
        }

        public Builder withDependsOn(List<AdfDependency> dependsOn) {
            this.dependsOn = dependsOn;
            return this;
        }

        public AdfActivity build() {
            return new AdfActivity(name, type, dependsOn, policy, userProperties, typeProperties, linkedServiceName);
        }
    }

}
