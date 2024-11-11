import javax.naming.NameNotFoundException;
import javax.naming.NamingException;
import java.util.HashMap;
import java.util.Map;

public abstract class AbstractNamingContext {
    protected Map<String, Object> bindings = new HashMap<>();

    public void bind(String name, Object obj) throws NamingException {
        if (bindings.containsKey(name)) {
            throw new NamingException("Binding already exists for name: " + name);
        }
        bindings.put(name, obj);
        saveBindings();
    }

    public void rebind(String name, Object obj) throws NamingException {
        bindings.put(name, obj);
        saveBindings();
    }

    public Object lookup(String name) throws NamingException {
        Object obj = bindings.get(name);
        if (obj == null) {
            throw new NameNotFoundException("No binding found for name: " + name);
        }
        return obj;
    }

    public void unbind(String name) throws NamingException {
        if (bindings.remove(name) == null) {
            throw new NameNotFoundException("No binding found for name: " + name);
        }
        saveBindings();
    }

    protected abstract void saveBindings() throws NamingException;

    protected abstract void loadBindings();
}
