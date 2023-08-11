package mayton.adf.entities;

import java.util.ArrayList;
import java.util.List;

public class AdfPipeline {

    private String name;
    private String type;

    public List<AdfActivity> activities;

    public static class Builder {

        private String type;
        private String name;
        public List<AdfActivity> activityList = new ArrayList<>();

        public Builder addActivity(AdfActivity adfActivity) {
            activityList.add(adfActivity);
            return this;
        }

        public Builder withType(String type) {
            this.type = type;
            return this;
        }

        public Builder withName(String name) {
            this.name = name;
            return this;
        }

        public AdfPipeline build() {
            return new AdfPipeline(name, activityList, type);
        }
    }


    private AdfPipeline(String name, List<AdfActivity> activityList, String type) {
        this.name = name;
        this.activities = activityList;
        this.type = type;
    }



}
