/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.time.LocalDate;
import java.util.Enumeration;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;
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
public class ParametrosAseguradoTest {

    public ParametrosAseguradoTest() {
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
     * Test of putNombre method, of class ParametrosAsegurado.
     */
    @Test
    public void testPutNombre() {
        System.out.println("putNombre");
        ParametrosAsegurado instance = new ParametrosAsegurado();
        instance.putNombre("emilio");
        instance.putNombre("daniel");
        instance.putNacimiento(LocalDate.now());
        System.out.println(instance.getPropiedades());
        System.out.println();
        for (Enumeration e = instance.getPropiedades().elements(); e.hasMoreElements();) {
            System.out.println(e.nextElement());
        }
        System.out.println();
        for (Enumeration e = instance.getPropiedades().keys(); e.hasMoreElements();) {
            System.out.println(e.nextElement());
        }
        System.out.println();
        System.out.println(instance.getPropiedades().stringPropertyNames());
        System.out.println();

        Set<Entry<Object, Object>> entries = instance.getPropiedades().entrySet();
        System.out.println(instance.getPropiedades().entrySet());

        for (Entry entry : entries) {
            System.out.println("K: " + entry.getKey() + ", V: " + entry.getValue());

        }
        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
    }

    /**
     * Test of getPropiedades method, of class ParametrosAsegurado.
     */
    @Test
    public void testGetPropiedades() {
        System.out.println("getPropiedades");
        ParametrosAsegurado instance = new ParametrosAsegurado();
        Properties expResult = null;
        Properties result = instance.getPropiedades();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
    }

}
