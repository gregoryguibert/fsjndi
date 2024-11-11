import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;
import org.yaml.snakeyaml.representer.Representer;

import javax.naming.NamingException;
import java.io.*;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class YamlNamingContext extends AbstractNamingContext {
    private static final String BINDINGS_FILE = "bindings.yaml";
    private static final Logger LOGGER = Logger.getLogger(YamlNamingContext.class.getName());

    public YamlNamingContext() {
        loadBindings();
    }

    @Override
    protected void saveBindings() throws NamingException {
        DumperOptions options = new DumperOptions();
        options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
        Yaml yaml = new Yaml(options);

        try (FileWriter writer = new FileWriter(BINDINGS_FILE)) {
            yaml.dump(bindings, writer);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Failed to save bindings to YAML", e);
            throw new NamingException("Failed to save bindings to YAML.");
        }
    }

    @Override
    protected void loadBindings() {
        File file = new File(BINDINGS_FILE);
        if (file.exists()) {
            Yaml yaml = new Yaml(new Constructor(Map.class));
            try (FileReader reader = new FileReader(file)) {
                bindings = yaml.load(reader);
            } catch (IOException e) {
                LOGGER.log(Level.SEVERE, "Failed to load bindings from YAML", e);
            }
        }
    }
}
