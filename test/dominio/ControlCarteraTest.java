/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dominio;

import exceptions.CreacionCarteraException;
import exceptions.DetenerCarteraException;
import exceptions.NoExisteCarteraException;
import exceptions.RegistroDuplicadoException;
import java.sql.SQLException;
import java.util.Set;
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
public class ControlCarteraTest {

    private ControlCartera controlCarteraTested;

    public ControlCarteraTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        controlCarteraTested = ControlCartera.getInstance();
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of getInstance method, of class ControlCartera.
     */
    @Test
    public void testGetInstance() {
        System.out.println("getInstance");
        ControlCartera result = ControlCartera.getInstance();
        assertEquals(controlCarteraTested, result);
        // TODO review the generated test code and remove the default call to fail.
    }

    /**
     * Test of guardar method, of class ControlCartera.
     */
    @Test
    public void testGuardar() {
        System.out.println("guardar");
        try {
            controlCarteraTested.conectarACartera();
            Asegurado asegurado = new Asegurado("Emilio", "Hernández", "Segovia");
            controlCarteraTested.guardar(asegurado, asegurado.getClass());
            controlCarteraTested.detenerCartera();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (DetenerCarteraException ex) {
            ex.printStackTrace();
        } catch (NoExisteCarteraException ex) {
            ex.printStackTrace();
        } catch (RegistroDuplicadoException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Test of detenerCartera method, of class ControlCartera.
     */
    @Test
    public void testDetenerCartera() {
        System.out.println("detenerBaseDeDatos");
        try {
            controlCarteraTested.detenerCartera();
        } catch (DetenerCarteraException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Test of conectarACartera method, of class ControlCartera.
     */
    @Test
    public void testConectarACartera() {
        System.out.println("conectarABaseDeDatos");
        try {
            controlCarteraTested.conectarACartera();
            controlCarteraTested.detenerCartera();
        } catch (NoExisteCarteraException ex) {
            ex.printStackTrace();
        } catch (DetenerCarteraException ex) {
            ex.printStackTrace();
        }

    }

    /**
     * Test of buscarTodos method, of class ControlCartera.
     */
    @Test
    public void testBuscarTodos() {
        System.out.println("buscarTodos");
        try {
            controlCarteraTested.conectarACartera();
            Set<Asegurado> asegurados = controlCarteraTested.buscarTodos(Asegurado.class);
            System.out.println(asegurados.toString());
            controlCarteraTested.detenerCartera();
        } catch (NoExisteCarteraException ex) {
            ex.printStackTrace();
        } catch (DetenerCarteraException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Test of crearCartera method, of class ControlCartera.
     */
    @Test
    public void testCrearCartera() {
        System.out.println("crearCartera");
        try {
            controlCarteraTested.crearCartera();
            controlCarteraTested.detenerCartera();
        } catch (CreacionCarteraException ex) {
            ex.printStackTrace();
        } catch (DetenerCarteraException ex) {
            ex.printStackTrace();
        }
    }

}
