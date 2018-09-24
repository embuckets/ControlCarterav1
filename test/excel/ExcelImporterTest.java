/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package excel;

import dominio.Asegurado;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
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
public class ExcelImporterTest {

    public ExcelImporterTest() {
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
     * Test of importarDatos method, of class ExcelImporter.
     */
    @Test
    public void testImportarDatos() {
        System.out.println("importarDatos");
        try {
            List<Asegurado> asegurados = ExcelImporter.importarDatos("libro.xlsx");
            asegurados.stream().forEach(a -> System.out.println(a + " : " + a.getPolizas()));

        } catch (IOException ex) {
            Logger.getLogger(ExcelImporterTest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidFormatException ex) {
            Logger.getLogger(ExcelImporterTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
