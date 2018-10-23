/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistencia;

import dominio.Asegurado;
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
import utils.Parametros;
import utils.ParametrosAsegurado;

/**
 *
 * @author emilio
 */
public class AseguradoMapperTest {
    
    public AseguradoMapperTest() {
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

    /**
     * Test of read method, of class AseguradoMapper.
     */
    @Test
    public void testRead_Parametros() {
        System.out.println("read");
        Parametros propiedades = null;
        AseguradoMapper instance = new AseguradoMapper();
        Set expResult = null;
        Set result = null;
        try {
            result = instance.read(propiedades);
        } catch (SQLException ex) {
            Logger.getLogger(AseguradoMapperTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of createQuery method, of class AseguradoMapper.
     */
    @Test
    public void testCreateQuery() {
        System.out.println("createQuery");
        String nombre = "emi";
        String paterno = "hernan";
        String materno = "sego";
        ParametrosAsegurado params = new ParametrosAsegurado();
        params.putNombre(nombre);
        String expResult = "SELECT * FROM asegurados WHERE nombre LIKE '%" + nombre + "%'";
        AseguradoMapper instance = new AseguradoMapper();
        String result = instance.createQuery(params);
        assertEquals(expResult, result);
        System.out.println("exp: " + expResult);
        System.out.println("result: " + result);
        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
    }

    /**
     * Test of readAll method, of class AseguradoMapper.
     */
    @Test
    public void testReadAll() {
        System.out.println("readAll");
        AseguradoMapper instance = new AseguradoMapper();
        Set expResult = null;
        Set result = instance.readAll();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of create method, of class AseguradoMapper.
     */
    @Test
    public void testCreate() throws Exception {
        System.out.println("create");
        Object object = null;
        AseguradoMapper instance = new AseguradoMapper();
        instance.create(object);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of read method, of class AseguradoMapper.
     */
    @Test
    public void testRead_int() {
        System.out.println("read");
        int id = 0;
        AseguradoMapper instance = new AseguradoMapper();
        Set expResult = null;
        Set result = instance.read(id);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
