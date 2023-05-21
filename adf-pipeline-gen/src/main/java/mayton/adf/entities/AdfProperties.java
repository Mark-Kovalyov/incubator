package mayton.adf.entities;

import com.fasterxml.jackson.annotation.JsonTypeName;

import java.util.List;

@JsonTypeName("properties")
public class AdfProperties {
    private List<Object> activities;
    private List<String> annotations;
    private String lastPublishTime;
}
