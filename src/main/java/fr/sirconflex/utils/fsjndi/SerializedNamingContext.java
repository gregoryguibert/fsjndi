import java.io.*;
import javax.naming.NamingException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SerializedNamingContext extends AbstractNamingContext {
    private static final String BINDINGS_FILE = "bindings.dat";
    private static final Logger LOGGER = Logger.getLogger(SerializedNamingContext.class.getName());

    public SerializedNamingContext() {
        loadBindings();
    }

    @Override
    protected void saveBindings() throws NamingException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(BINDINGS_FILE))) {
            oos.writeObject(bindings);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Failed to save bindings", e);
            throw new NamingException("Failed to save bindings due to IOException.");
        }
    }

    @Override
    protected void loadBindings() {
        File file = new File(BINDINGS_FILE);
        if (file.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                bindings = (Map<String, Object>) ois.readObject();
            } catch (IOException | ClassNotFoundException e) {
                LOGGER.log(Level.SEVERE, "Failed to load bindings", e);
            }
        }
    }
}
