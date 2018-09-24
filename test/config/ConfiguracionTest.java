/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package config;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author emilio
 */
public class ConfiguracionTest {

    public ConfiguracionTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testSomeMethod() {
        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
    }

    @Test
    public void testConstructor() {
        Configuracion conf = Configuracion.getInstance();
        System.out.println(conf.getProperties());
    }

    @Test
    public void testGuardarUsuarioBaseDeDatos() {
        try {
            String usuario = "emilio";
            Configuracion conf = Configuracion.getInstance();
            conf.guardarUsuarioBaseDeDatos(usuario);
            assertEquals(usuario, conf.getUsuarioBaseDeDatos());
            System.out.println(conf.getProperties());
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }

}
