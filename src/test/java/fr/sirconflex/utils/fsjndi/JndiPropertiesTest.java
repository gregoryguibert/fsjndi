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
    	
        // Chargement des propri�t�s JNDI depuis jndi.properties dans src/test/resources
    	
        Properties jndiProps = new Properties();
        jndiProps.load(getClass().getClassLoader().getResourceAsStream("jndi.properties"));

        // Initialisation du contexte avec les propri�t�s JNDI
        context = new InitialContext(jndiProps);
    }

    @Test
    public void testReBindAndLookup_WithJndiProperties() throws NamingException {
        // Test de la liaison et de la r�cup�ration d'un objet en utilisant jndi.properties
        String expectedValue = "Hello from JNDI Properties!";
        context.rebind("testString", expectedValue);

        String actualValue = (String) context.lookup("testString");
        assertNotNull(actualValue);
        assertEquals(expectedValue, actualValue);
    }
    
    @Test
    public void testBindAndLookup_WithJndiProperties() throws NamingException {
        // Test de la liaison et de la r�cup�ration d'un objet en utilisant jndi.properties
        String expectedValue = "My name is groot";
        context.bind("iamgroot", expectedValue);

        String actualValue = (String) context.lookup("iamgroot");
        assertNotNull(actualValue);
        assertEquals(expectedValue, actualValue);
    }

    @Test
    public void testInitialContextCreation() throws NamingException {
        // V�rifie que le contexte a �t� correctement initialis� avec jndi.properties
        assertNotNull(context);

        // Test d'une liaison suppl�mentaire pour v�rifier le fonctionnement global
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

        // V�rification que l'objet est bien li�
        String actualValue = (String) context.lookup("testUnbind");
        assertEquals(expectedValue, actualValue);

        // Suppression de la liaison
        context.unbind("testUnbind");

        // V�rification que la liaison a �t� supprim�e
        assertThrows(NameNotFoundException.class, () -> context.lookup("testUnbind"));
    }    
    
    
    @Test
    public void testClose() throws NamingException {
        // Lier un objet pour verifier son acces avant la fermeture
        String expectedValue = "Close Test String";
        context.bind("testClose", expectedValue);

        // Verification que l'objet est bien lie
        String actualValue = (String) context.lookup("testClose");
        assertEquals(expectedValue, actualValue);

        // Fermer le contexte
        context.close();
        
        // Verifier que les appels de methodes apres fermeture lancent des NamingException
        assertThrows(NamingException.class, () -> context.lookup("testClose"));
        assertThrows(NamingException.class, () -> context.bind("testAfterClose", "Another String"));
        assertThrows(NamingException.class, () -> context.unbind("testClose"));
    }
    
    
    
}
