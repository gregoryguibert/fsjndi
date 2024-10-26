package fr.sirconflex.utils.fsjndi;

import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.spi.InitialContextFactory;

public class SimpleFileContextFactory implements InitialContextFactory {

    @Override
    public Context getInitialContext(Hashtable<?, ?> environment) throws NamingException {
        String directoryPath = (String) environment.get("java.naming.provider.url");
        return new SimpleNamingContext(directoryPath);
    }
}

	