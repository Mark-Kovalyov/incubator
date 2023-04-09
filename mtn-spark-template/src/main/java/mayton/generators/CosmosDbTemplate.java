package mayton.generators;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.ParseException;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.velocity.VelocityContext;

import java.util.ArrayList;
import java.util.List;

public class CosmosDbTemplate extends GenericTemplate {

    protected CosmosDbTemplate() throws ParseException {
        super();
    }

    @Override
    public String resourceDomain() {
        return "java.azure";
    }

    @Override
    public List<Pair<String, String>> velocityTemplates(CommandLine cli) {
        return new ArrayList<>();
    }

    @Override
    public String usageHelp() {
        return null;
    }

    @Override
    public VelocityContext velocityContext(CommandLine cli) {
        return null;
    }
}
