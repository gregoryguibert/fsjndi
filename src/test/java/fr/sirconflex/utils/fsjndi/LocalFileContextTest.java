package fr.sirconflex.utils.fsjndi;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.naming.Context;
import javax.naming.NameNotFoundException;
import javax.naming.NamingException;
import javax.jms.TopicConnectionFactory;

import java.io.File;
import java.io.Serializable;

import static org.junit.Assert.*;

public class LocalFileContextTest {

    private Context context;
    private File testDir;

    // Classe pour tester la sérialisation d'objets personnalisés
    static class SerializablePerson implements Serializable {
        private String name;
        private int age;

        public SerializablePerson(String name, int age) {
            this.name = name;
            this.age = age;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;
            SerializablePerson person = (SerializablePerson) obj;
            return age == person.age && name.equals(person.name);
        }
    }

    @Before
    public void setUp() {
        // Création d'un répertoire temporaire pour le stockage des fichiers
        testDir = new File("target/test-directory");
        if (testDir.exists()) {
        	for (File f : testDir.listFiles()) {
        		f.delete();
        	}
        	testDir.delete();
        }
        testDir.mkdir();

        // Initialisation du contexte avec le répertoire temporaire
        context = new SimpleNamingContext(testDir.getAbsolutePath());
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testBindAndLookup_String() throws NamingException {
        // Test de la liaison et de la récupération d'une chaîne
        String expectedValue = "Hello, World!";
        context.bind("testString", expectedValue);

        String actualValue = (String) context.lookup("testString");
        assertEquals(expectedValue, actualValue);
    }

    @Test
    public void testBindAndLookup_CustomSerializableObject() throws NamingException {
        // Test de la liaison et de la récupération d'un objet sérialisable personnalisé
        SerializablePerson person = new SerializablePerson("John Doe", 30);
        context.bind("person", person);

        SerializablePerson retrievedPerson = (SerializablePerson) context.lookup("person");
        assertEquals(person, retrievedPerson);
    }

    @Test
    public void testBindAndLookup_TopicConnectionFactory() throws NamingException {
        // Test de la liaison et de la récupération de TopicConnectionFactory
        TopicConnectionFactory factory = new ActiveMQConnectionFactory("tcp://localhost:61616");
        context.bind("TopicConnectionFactory", factory);

        TopicConnectionFactory retrievedFactory = (TopicConnectionFactory) context.lookup("TopicConnectionFactory");
        assertEquals(factory, retrievedFactory);
    }

    @Test(expected = NameNotFoundException.class)
    public void testLookup_NonExistentEntry() throws NamingException {
        // Test de la recherche d'un objet non existant (devrait lancer NameNotFoundException)
        context.lookup("nonExistentEntry");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testBind_NonSerializableObject() throws NamingException {
        // Test de la tentative de liaison d'un objet non sérialisable (devrait lancer IllegalArgumentException)
        Object nonSerializableObject = new Object();
        context.bind("nonSerializableObject", nonSerializableObject);
    }
}