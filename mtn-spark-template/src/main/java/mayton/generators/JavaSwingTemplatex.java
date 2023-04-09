package mayton.generators;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.ParseException;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.velocity.VelocityContext;

import java.util.List;

public class JavaSwingTemplatex extends GenericTemplate {

    protected JavaSwingTemplatex() throws ParseException {

    }

    @Override
    public String resourceDomain() {
        return "java.swing";
    }

    @Override
    public List<Pair<String, String>> velocityTemplates(CommandLine cli) {
        return null;
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
