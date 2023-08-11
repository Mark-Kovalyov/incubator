package mayton.adf.entities;

import com.fasterxml.jackson.annotation.JsonTypeName;

import java.util.List;

@JsonTypeName("mvn")
public class AdfMavenLibrary extends AdfLibrary {

    private String coordinates;
    private String repo;
    private List<String> exclusions;

    public AdfMavenLibrary(String coordinates) {
        this.coordinates = coordinates;
    }

    public String getRepo() {
        return repo;
    }

    public void setRepo(String repo) {
        this.repo = repo;
    }

    public List<String> getExclusions() {
        return exclusions;
    }

    public void setExclusions(List<String> exclusions) {
        this.exclusions = exclusions;
    }
}
