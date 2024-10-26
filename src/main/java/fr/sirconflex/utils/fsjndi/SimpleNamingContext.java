package fr.sirconflex.utils.fsjndi;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.naming.NameNotFoundException;
import javax.naming.NamingException;

public class SimpleNamingContext extends SimpleNamingContextCore {

	private final String storageDirectoryPath;
	private final Map<String, Object> bindings = new HashMap<>();
	private static final String bindingsFileName = "bindings.dat";

	private static Logger LOGGER = Logger.getLogger(SimpleNamingContext.class.getName());

	public SimpleNamingContext(String storageDirectoryPath) {
		this.storageDirectoryPath = storageDirectoryPath;
		loadBindings();
	}

	@Override
	public void bind(String name, Object obj) throws NamingException {
		if (bindings.containsKey(name)) {
			throw new NamingException("Binding already exists for name : " + name);
		}
		if (!(obj instanceof Serializable)) {
			throw new IllegalArgumentException("Cannot bind a non-serializable object");
		}
		bindings.put(name, obj);
		saveBindings();
	}

	@Override
	public void rebind(String name, Object obj) throws NamingException {
		if (!(obj instanceof Serializable)) {
			throw new IllegalArgumentException("Cannot rebind a non-serializable object");
		}
		bindings.put(name, obj);
		saveBindings();
	}

	@Override
	public Object lookup(String name) throws NamingException {
		Object obj = bindings.get(name);
		if (obj == null) {
			String error = "Cannot lookup object with name " + name;
			LOGGER.log(Level.FINE, error );
			throw new NameNotFoundException(error);
		}
		return obj;
	}

	private void saveBindings() throws NamingException {
		try (ObjectOutputStream oos = new ObjectOutputStream(
				new FileOutputStream(new File(storageDirectoryPath, bindingsFileName)))) {
			oos.writeObject(bindings);
		} catch (IOException e) {
			String errormessage = "Cannot save bindings due to IOException";
			LOGGER.log(Level.SEVERE, errormessage, e);
			throw new NamingException(errormessage);
		}
	}

	@SuppressWarnings("unchecked")
	private void loadBindings() {
		File file = new File(storageDirectoryPath, bindingsFileName);
		if (file.exists()) {
			try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
				bindings.putAll((Map<String, Object>) ois.readObject());
			} catch (IOException | ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
	}


	@Override
	public void unbind(String name) throws NamingException {
		if (!bindings.containsKey(name)) {
			throw new NameNotFoundException("No binding for : " + name);
		}
		bindings.remove(name);
		saveBindings();
	}

	@Override
	public void close() throws NamingException {
		bindings.clear();

	}

}
