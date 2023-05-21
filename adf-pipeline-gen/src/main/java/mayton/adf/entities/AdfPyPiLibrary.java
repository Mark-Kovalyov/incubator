package mayton.adf.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeName("pypi")
public class AdfPyPiLibrary extends AdfLibrary {
    @JsonProperty("package")
    private String packageName;
    private String repo;

    public AdfPyPiLibrary(String packageName, String repo) {
        this.packageName = packageName;
        this.repo = repo;
    }
}
