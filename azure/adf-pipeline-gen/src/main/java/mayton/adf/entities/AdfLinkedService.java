package mayton.adf.entities;

import com.fasterxml.jackson.annotation.JsonTypeName;

import java.util.LinkedHashMap;

@JsonTypeName("linkedServiceName")
public class AdfLinkedService {

    private String referenceName;
    private AdfLinkedServiceTypes type;
    private LinkedHashMap<String, Object> parameters;

    public AdfLinkedService(String referenceName, AdfLinkedServiceTypes type, LinkedHashMap<String, Object> parameters) {
        this.referenceName = referenceName;
        this.type = type;
        this.parameters = parameters;
    }
}
