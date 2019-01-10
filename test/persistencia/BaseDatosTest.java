/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistencia;

import dominio.Asegurado;
import dominio.Dinero;
import dominio.Poliza;
import dominio.Recibo;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import utils.ParametrosAsegurado;

/**
 *
 * @author emilio
 */
public class BaseDatosTest {

    private BaseDeDatos baseDatosTested;
    private Poliza polizaTested;
    private Asegurado aseguradoTested;

    public BaseDatosTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        baseDatosTested = BaseDeDatos.getInstance();
        aseguradoTested = new Asegurado("emilio", "hernandez", "segovia");
        aseguradoTested.setId(1);
        polizaTested = new Poliza("NO-789-789", "gnp", aseguradoTested.getId(), "AUToS", "PRODUCTO", "PLAN", LocalDate.now(), "agente", "mensual", new Dinero(BigDecimal.valueOf(12.123f), "pesos"));
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of crearBaseDeDatos method, of class BaseDeDatos.
     */
    @Test
    public void testGetConnection() {
        System.out.println("crearBaseDeDatos");
        try (Connection connection = baseDatosTested.getConnection();) {
//            Connection connection = baseDatosTested.getConnection();

//        baseDatosTested.borrarBaseDeDatos();
// TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
        } catch (SQLException ex) {
            baseDatosTested.printSQLException(ex);
        }
    }

    @Test
    public void testBuscarPorParametros() {
        System.out.println("bucarPorParametro");
        ParametrosAsegurado params = new ParametrosAsegurado();
//        params.putNombre("emi");
        params.putApellidoMaterno("sego");
        params.putApellidoPaterno("hernan");

        try {
            baseDatosTested.contectarABaseDeDatos();
//            baseDatosTested.insertAsegurado(new Asegurado("daniel", "hernandez", "segovia"));
            baseDatosTested.buscarPorParemetros(params).forEach((s) -> System.out.println(s));
            baseDatosTested.detenerBaseDeDatos();
        } catch (SQLException ex) {
            Logger.getLogger(BaseDatosTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Test of crearBaseDeDatos method, of class BaseDeDatos.
     */
    @Test
    public void testCrearBaseDeDatos() {
        System.out.println("crearBaseDeDatos");
        try {
            baseDatosTested.crearBaseDeDatos();
            baseDatosTested.detenerBaseDeDatos();
        } catch (SQLException ex) {
            baseDatosTested.printSQLException(ex);
        }
//        baseDatosTested.borrarBaseDeDatos();

        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
    }

    @Test
    public void testBorrarAsegurados() {
        System.out.println("borrarAsegurados");
        baseDatosTested.borrarAsegurados();
        try {
            baseDatosTested.detenerBaseDeDatos();
        } catch (SQLException ex) {
            baseDatosTested.printSQLException(ex);
        }
    }

    @Test
    public void testBorrarPolizas() {
        System.out.println("borrarPolizas");
        baseDatosTested.borrarPolizas();
        try {
            baseDatosTested.detenerBaseDeDatos();
        } catch (SQLException ex) {
            baseDatosTested.printSQLException(ex);
        }
    }

    @Test
    public void testInsertPoliza() {
        System.out.println("insertPoliza");
        try {
            baseDatosTested.insertPoliza(polizaTested);

            //TODO: CHECAR DUPLICADOS, PONER UNIQUE A TODA LA FILA
        } catch (SQLException ex) {
            baseDatosTested.printSQLException(ex);
//            fail();
            assertTrue(false);
        }
        try {
            baseDatosTested.insertPoliza(polizaTested);
            assertTrue(false);
//            fail();
            //TODO: CHECAR DUPLICADOS, PONER UNIQUE A TODA LA FILA
        } catch (SQLException ex) {
            baseDatosTested.printSQLException(ex);
        }
        try {
            polizaTested = new Poliza("NO-789-789", "gnp", aseguradoTested.getId(), "responsabilidad civil", "PRODUCTO", "PLAN", LocalDate.now(), "agente", "MENSUAL", new Dinero(BigDecimal.valueOf(12.123f), "pesos"));
            baseDatosTested.insertPoliza(polizaTested);
            assertTrue(false);
            //TODO: CHECAR DUPLICADOS, PONER UNIQUE A TODA LA FILA
            baseDatosTested.detenerBaseDeDatos();
        } catch (SQLException ex) {
            baseDatosTested.printSQLException(ex);
        }
    }

    /**
     * Test of insertRecibo method, of class BaseDeDatos.
     */
    @Test
    public void testInsertRecibo() {
        System.out.println("insertRecibo");
        polizaTested.setId(4);
        polizaTested.generarRecibos(5, 1234.56f, 1101.2f);

        for (Recibo recibo : polizaTested.getRecibos()) {
            try {
                baseDatosTested.insertRecibo(recibo);
            } catch (SQLException e) {
                baseDatosTested.printSQLException(e);
            }
        }
    }

    /**
     * Test of selectRecibos method, of class BaseDeDatos.
     */
    @Test
    public void testSelectRecibos() {
        System.out.println("selectRecibos");
        try {
            polizaTested.setId(4);
            List<Recibo> recibos = baseDatosTested.selectRecibos(polizaTested);
            recibos.forEach((r) -> System.out.println(r));
        } catch (SQLException e) {
            baseDatosTested.printSQLException(e);
        }
    }

    /**
     * Test of insertAsegurado method, of class BaseDeDatos.
     */
    @Test
    public void testInsertAsegurado() {
        System.out.println("insertAsegurado");
        Asegurado asegurado = new Asegurado("emilio", "hernández", "segovia");
        try {
            baseDatosTested.insertAsegurado(asegurado);
        } catch (SQLException ex) {
            baseDatosTested.printSQLException(ex);
            fail("emilio hernandez segovia debia ser insertado");
        }
        asegurado = new Asegurado("EMILIO", "HERNANDEZ", "SEGOVIA");
        try {
            baseDatosTested.insertAsegurado(asegurado);
            fail("EMILIO HERNANDEZ SEGOVIA no debia ser insertado");
        } catch (SQLException ex) {
//            baseDatosTested.printSQLException(ex);
        }

        asegurado = new Asegurado("EMILIO", "HERNáNDEZ", "SEGOVIA");
        try {
            baseDatosTested.insertAsegurado(asegurado);
            fail("EMILIO HERNáNDEZ SEGOVIA no debia ser insertado");
        } catch (SQLException ex) {
//            baseDatosTested.printSQLException(ex);    
        }
        asegurado = new Asegurado("EMILIO", "nuñez", "SEGOVIA");
        try {
            baseDatosTested.insertAsegurado(asegurado);
        } catch (SQLException ex) {
            baseDatosTested.printSQLException(ex);
            fail("EMILIO nuñez SEGOVIA debia ser insertado");
        }

        asegurado = new Asegurado("daniel", "hernandez", "segovia");
        try {
            baseDatosTested.insertAsegurado(asegurado);
        } catch (SQLException ex) {
            baseDatosTested.printSQLException(ex);
            fail("daniel hernandez segovia debia ser insertado");
        }
        try {
            baseDatosTested.insertAsegurado(asegurado);
            fail("daniel hernandez segovia no debia ser insertado");
        } catch (SQLException ex) {
//            baseDatosTested.printSQLException(ex);
        }

        asegurado = new Asegurado("procesos automatizados s.a. de c.v.", null, null);

        try {
            baseDatosTested.insertAsegurado(asegurado);
        } catch (SQLException ex) {
            baseDatosTested.printSQLException(ex);
            fail("procesos automatizados s.a. de c.v. debia ser insertado");
        }

        asegurado = new Asegurado("daniela", null, null);

        try {
            baseDatosTested.insertAsegurado(asegurado);
        } catch (SQLException ex) {
            baseDatosTested.printSQLException(ex);
            fail("daniela debia ser insertado");
        }

        try {
            baseDatosTested.insertAsegurado(asegurado);
            fail("daniela no debia ser insertado");
            baseDatosTested.detenerBaseDeDatos();
        } catch (SQLException ex) {
            baseDatosTested.printSQLException(ex);
        }

    }

    /**
     * Test of SelectAsegurado method, of class BaseDeDatos.
     */
    @Test
    public void testSelectAsegurado() {
        System.out.println("selectAseguradoAsegurados");
        Optional<Asegurado> asegurado = baseDatosTested.selectAsegurado(12);
//        System.out.println(asegurado.map(Asegurado::getId).get());
        System.out.format("%8d", asegurado.map(Asegurado::getId).orElse(Integer.SIZE));
//        System.out.format("%-40s", asegurado.map(Asegurado::getNombre));
//        System.out.format("%-25s", asegurado.flatMap(Asegurado::getApellidoPaterno));
//        System.out.format("%-25s", asegurado.flatMap(Asegurado::getApellidoMaterno));
//        System.out.format("%-20s", asegurado.flatMap(Asegurado::getRFC));
//        System.out.format("%-15t", asegurado.flatMap(Asegurado::getNacimiento));
        System.out.println();
        try {
            baseDatosTested.detenerBaseDeDatos();
        } catch (SQLException ex) {
            baseDatosTested.printSQLException(ex);
        }
    }

    @Test
    public void testSelectTodosPolizas() {
        System.out.println("selectTodosPolizas");
        List<Poliza> polizas = baseDatosTested.selectTodoPolizas();
        for (Poliza poliza : polizas) {
            System.out.println(poliza);
        }
        try {
            baseDatosTested.detenerBaseDeDatos();
        } catch (SQLException ex) {
            baseDatosTested.printSQLException(ex);
        }
    }

    /**
     * Test of selectTodosAsegurados method, of class BaseDeDatos.
     */
    @Test
    public void testselectTodosAsegurados() {
        System.out.println("selectTodosAsegurados");
        
        
        try {
            baseDatosTested.contectarABaseDeDatos();
        } catch (SQLException ex) {
            Logger.getLogger(BaseDatosTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        List<Asegurado> asegurados = baseDatosTested.selectTodosAsegurados();
        for (Asegurado asegurado : asegurados) {
            System.out.format("%-8d", asegurado.getId());
            System.out.format("%-40s", asegurado.getNombre());
            System.out.format("%-25s", asegurado.getApellidoPaterno());
            System.out.format("%-25s", asegurado.getApellidoMaterno());
            System.out.format("%-20s", asegurado.getRFC());
            System.out.format("%-15s", asegurado.getNacimiento());
            System.out.println();
        }
        try {
            baseDatosTested.detenerBaseDeDatos();
        } catch (SQLException ex) {
            baseDatosTested.printSQLException(ex);
        }
    }

    /**
     * Test of borrarBaseDeDatos method, of class BaseDeDatos.
     */
    @Test
    public void testBorrarBaseDeDatos() {
        System.out.println("eliminarBaseDeDatos");
        try {
            BaseDeDatos.borrarBaseDeDatos();
            // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Test of detenerBaseDeDatos method, of class BaseDeDatos.
     */
    @Test
    public void testDetenerBaseDeDatos() {
        System.out.println("detenerBaseDeDatos");
        try {
            baseDatosTested.detenerBaseDeDatos();
        } catch (SQLException ex) {
            baseDatosTested.printSQLException(ex);
        }
    }
    /**
     * Test of printSQLException method, of class BaseDatos.
     */
//    @Test
//    public void testPrintSQLException() {
//        System.out.println("printSQLException");
//        SQLException e = null;
//        BaseDeDatos.printSQLException(e);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//    /**
//     * Test of getDataBaseProperties method, of class BaseDeDatos.
//     */
//    @Test
//    public void testGetDataBaseProperties() {
//        System.out.println("getDataBaseProperties");
//        BaseDeDatos.getDataBaseProperties();
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of configurarAutorizacion method, of class BaseDeDatos.
//     */
//    @Test
//    public void testConfigurarAutorizacion() throws Exception {
//        System.out.println("configurarAutorizacion");
//        Connection conn = null;
//        String user = "";
//        String password = "";
//        BaseDeDatos.configurarAutorizacion(conn, user, password);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//    
}
