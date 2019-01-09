/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistencia;

import dominio.Asegurado;
import dominio.Poliza;
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
import utils.ParametrosPoliza;

/**
 *
 * @author emilio
 */
public class PersistenceFacadeTest {
    
    public PersistenceFacadeTest() {
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
     * Test of buscar method, of class PersistenceFacade.
     */
    @Test
    public void testBuscar() {
        try {
            System.out.println("buscar");
            ParametrosAsegurado parametros = new ParametrosAsegurado();
            parametros.putNombre("emilio");
            PersistenceFacade instance = new PersistenceFacade();
            Set<Asegurado> asegurados = instance.buscar(parametros, Asegurado.class);
            Object expResult = new Asegurado("emilio", "", "");
            assertEquals(expResult, asegurados);
            
            ParametrosPoliza parametrosPoliza = new ParametrosPoliza();
            parametrosPoliza.setNumero("NO-123");
            Set<Poliza> polizas = instance.buscar(parametrosPoliza, Poliza.class);
            Poliza expPoliza = new Poliza();
            expPoliza.setNumero("NO-123");
            assertEquals(expPoliza, asegurados);
            
            // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
        } catch (SQLException ex) {
            Logger.getLogger(PersistenceFacadeTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
