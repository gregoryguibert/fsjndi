package fr.sirconflex.utils.fsjndi;

import java.util.Hashtable;

import javax.naming.Binding;
import javax.naming.Context;
import javax.naming.Name;
import javax.naming.NameClassPair;
import javax.naming.NameParser;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;

public abstract class SimpleNamingContextCore implements Context {
	@Override
	public Object lookup(Name name) throws NamingException {
		throwBecauseNotImplemented();
		return null;
	}

	@Override
	public void bind(Name name, Object obj) throws NamingException {
		throwBecauseNotImplemented();
	}

	@Override
	public void rebind(Name name, Object obj) throws NamingException {
		throwBecauseNotImplemented();
		
	}

	@Override
	public void unbind(Name name) throws NamingException {
		throwBecauseNotImplemented();
		
	}


	@Override
	public void rename(Name oldName, Name newName) throws NamingException {
		throwBecauseNotImplemented();
		
	}

	@Override
	public void rename(String oldName, String newName) throws NamingException {
		throwBecauseNotImplemented();
		
	}

	@Override
	public NamingEnumeration<NameClassPair> list(Name name) throws NamingException {
		throwBecauseNotImplemented();
		return null;
	}

	@Override
	public NamingEnumeration<NameClassPair> list(String name) throws NamingException {
		throwBecauseNotImplemented();
		return null;
	}

	@Override
	public NamingEnumeration<Binding> listBindings(Name name) throws NamingException {
		throwBecauseNotImplemented();
		return null;
	}

	@Override
	public NamingEnumeration<Binding> listBindings(String name) throws NamingException {
		throwBecauseNotImplemented();
		return null;
	}

	@Override
	public void destroySubcontext(Name name) throws NamingException {
		throwBecauseNotImplemented();
	}

	@Override
	public void destroySubcontext(String name) throws NamingException {
		throwBecauseNotImplemented();
		
	}

	@Override
	public Context createSubcontext(Name name) throws NamingException {
		throwBecauseNotImplemented();
		return null;
	}

	@Override
	public Context createSubcontext(String name) throws NamingException {
		throwBecauseNotImplemented();
		return null;
	}

	@Override
	public Object lookupLink(Name name) throws NamingException {
		throwBecauseNotImplemented();
		return null;
	}

	@Override
	public Object lookupLink(String name) throws NamingException {
		throwBecauseNotImplemented();
		return null;
	}

	@Override
	public NameParser getNameParser(Name name) throws NamingException {
		throwBecauseNotImplemented();
		return null;
	}

	@Override
	public NameParser getNameParser(String name) throws NamingException {
		throwBecauseNotImplemented();
		return null;
	}

	@Override
	public Name composeName(Name name, Name prefix) throws NamingException {
		throwBecauseNotImplemented();
		return null;
	}

	@Override
	public String composeName(String name, String prefix) throws NamingException {
		throwBecauseNotImplemented();
		return null;
	}

	@Override
	public Object addToEnvironment(String propName, Object propVal) throws NamingException {
		throwBecauseNotImplemented();
		return null;
	}

	@Override
	public Object removeFromEnvironment(String propName) throws NamingException {
		throwBecauseNotImplemented();
		return null;
	}

	@Override
	public Hashtable<?, ?> getEnvironment() throws NamingException {
		throwBecauseNotImplemented();
		return null;
	}

	@Override
	public String getNameInNamespace() throws NamingException {
		throwBecauseNotImplemented();
		return null;
	}

	private Object throwBecauseNotImplemented() throws NamingException {
		throw new NamingException("This method is not implemented");
	}
}
