package mayton.adf.entities;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class AdfTypeProperties {

    private String notebookPath;
    private List<AdfLibrary> libraries;
    private List<String> parameters;
    private LinkedHashMap<String, Object> baseParameters;
    private String mainClassName;

    public AdfTypeProperties(String notebookPath, List<AdfLibrary> libraries, List<String> parameters, LinkedHashMap<String, Object> baseParameters, String mainClassName) {
        this.notebookPath = notebookPath;
        this.libraries = libraries;
        this.parameters = parameters;
        this.baseParameters = baseParameters;
        this.mainClassName = mainClassName;
    }

    public static class Builder {

        private String notebookPath;
        private List<AdfLibrary> libraries = new ArrayList<>();
        private List<String> parameters = new ArrayList<>();
        private LinkedHashMap<String, Object> baseParameters = new LinkedHashMap<>();
        private String mainClassName;

        public Builder withNotebookPath(String notebookPath) {
            this.notebookPath = notebookPath;
            return this;
        }

        public Builder withMainClassName(String mainClassName) {
            this.mainClassName = mainClassName;
            return this;
        }

        public Builder addParameter(String parameter) {
            this.parameters.add(parameter);
            return this;
        }

        public Builder addLibrary(AdfLibrary library) {
            this.libraries.add(library);
            return this;
        }

        public Builder addBaseParameter(String key, Object value) {
            this.baseParameters.put(key, value);
            return this;
        }

        public AdfTypeProperties build() {
            return new AdfTypeProperties(notebookPath, libraries, parameters, baseParameters, mainClassName);
        }
    }
}
