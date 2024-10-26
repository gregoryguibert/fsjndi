package fr.sirconflex.utils.fsjndi;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;

import java.io.File;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NameNotFoundException;
import javax.naming.NamingException;

import org.junit.Before;
import org.junit.Test;

public class JndiPropertiesTest {

    private Context context;

    @Before
    public void setUp() throws Exception {
    	
        File testDir = new File("target/test-directory-jndifile");
        if (testDir.exists()) {
        	for (File f : testDir.listFiles()) {
        		f.delete();
        	}
        	testDir.delete();
        }
        testDir.mkdir();
    	
        // Chargement des propriétés JNDI depuis jndi.properties dans src/test/resources
    	
        Properties jndiProps = new Properties();
        jndiProps.load(getClass().getClassLoader().getResourceAsStream("jndi.properties"));

        // Initialisation du contexte avec les propriétés JNDI
        context = new InitialContext(jndiProps);
    }

    @Test
    public void testReBindAndLookup_WithJndiProperties() throws NamingException {
        // Test de la liaison et de la récupération d'un objet en utilisant jndi.properties
        String expectedValue = "Hello from JNDI Properties!";
        context.rebind("testString", expectedValue);

        String actualValue = (String) context.lookup("testString");
        assertNotNull(actualValue);
        assertEquals(expectedValue, actualValue);
    }
    
    @Test
    public void testBindAndLookup_WithJndiProperties() throws NamingException {
        // Test de la liaison et de la récupération d'un objet en utilisant jndi.properties
        String expectedValue = "My name is groot";
        context.bind("iamgroot", expectedValue);

        String actualValue = (String) context.lookup("iamgroot");
        assertNotNull(actualValue);
        assertEquals(expectedValue, actualValue);
    }

    @Test
    public void testInitialContextCreation() throws NamingException {
        // Vérifie que le contexte a été correctement initialisé avec jndi.properties
        assertNotNull(context);

        // Test d'une liaison supplémentaire pour vérifier le fonctionnement global
        Integer expectedInteger = 123;
        context.bind("testInteger", expectedInteger);

        Integer actualInteger = (Integer) context.lookup("testInteger");
        assertEquals(expectedInteger, actualInteger);
    }
    
    @Test
    public void testBindAndUnbind() throws NamingException {
        // Liaison initiale d'un objet
        String expectedValue = "Unbind Test String";
        context.bind("testUnbind", expectedValue);

        // Vérification que l'objet est bien lié
        String actualValue = (String) context.lookup("testUnbind");
        assertEquals(expectedValue, actualValue);

        // Suppression de la liaison
        context.unbind("testUnbind");

        // Vérification que la liaison a été supprimée
        assertThrows(NameNotFoundException.class, () -> context.lookup("testUnbind"));
    }    
    
    
    @Test
    public void testClose() throws NamingException {
        // Lier un objet pour vérifier son accès avant la fermeture
        String expectedValue = "Close Test String";
        context.bind("testClose", expectedValue);

        // Vérification que l'objet est bien lié
        String actualValue = (String) context.lookup("testClose");
        assertEquals(expectedValue, actualValue);

        // Fermer le contexte
        context.close();
        
        // Vérifier que les appels de méthodes après fermeture lancent des NamingException
        assertThrows(NamingException.class, () -> context.lookup("testClose"));
        assertThrows(NamingException.class, () -> context.bind("testAfterClose", "Another String"));
        assertThrows(NamingException.class, () -> context.unbind("testClose"));
    }
    
    
    
}
