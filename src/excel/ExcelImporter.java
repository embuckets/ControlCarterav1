/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package excel;

import dominio.Asegurado;
import dominio.Dinero;
import dominio.Poliza;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

/**
 *
 * @author emilio
 */
public class ExcelImporter {

    public static List<Asegurado> importarDatos(String nombreArchivo) throws IOException, InvalidFormatException {
        final int NOMBRE = 0;
        final int PATERNO = 1;
        final int MATERNO = 2;
        final int NUM_POLIZA = 3;
        final int ASEGURADORA = 4;
        final int RAMO = 5;
        final int PRODUCTO = 6;
        final int PLAN = 7;
        final int INICIO_VIGENCIA = 8;
        final int PRIMA = 9;
        final int MONEDA = 10;
        final int FORMA_DE_PAGO = 11;
        final int CONDUCTO_DE_COBRO = 12;
        final int NUMERO_DE_RECIBOS_PAGADOS = 13;
        final int IMPORTE_DERECHO_POLIZA = 14;
        final int IMPORTE_SUBSECUENTES = 15;

        Map<String, Asegurado> asegurados = new HashMap<String, Asegurado>();

        try {
            Workbook wb = WorkbookFactory.create(new File(nombreArchivo));
            Sheet sheet = wb.getSheetAt(0);
            int primerFila = 1;
            int ultimaFila = sheet.getLastRowNum();
            for (int numeroFila = 1; numeroFila <= ultimaFila; numeroFila++) {
                Row row = sheet.getRow(numeroFila);
                if (row == null) {
                    continue;
                }
                // construye asegurado
                String nombre = row.getCell(NOMBRE).getStringCellValue();//if blank returns empty string else exception
                String apellidoPaterno = row.getCell(PATERNO).getStringCellValue();
                String apellidoMaterno = row.getCell(MATERNO).getStringCellValue();
                Asegurado asegurado = new Asegurado(nombre, apellidoPaterno, apellidoMaterno);
                //datos de poliza
                String numPoliza = row.getCell(NUM_POLIZA).getStringCellValue();
                String aseguradora = row.getCell(ASEGURADORA).getStringCellValue();
                String ramo = row.getCell(RAMO).getStringCellValue();
                String producto = row.getCell(PRODUCTO).getStringCellValue();
                String plan = row.getCell(PLAN).getStringCellValue();
                LocalDate inicioVigencia = row.getCell(INICIO_VIGENCIA).getDateCellValue().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                Double prima = row.getCell(PRIMA).getNumericCellValue();
                String moneda = row.getCell(MONEDA).getStringCellValue();//si vacia pesos
                Dinero dinero = new Dinero(BigDecimal.valueOf(prima), moneda);
                String formaDePago = row.getCell(FORMA_DE_PAGO).getStringCellValue();
                String conductoDeCobro = row.getCell(CONDUCTO_DE_COBRO).getStringCellValue();
                //datos para los recibos
                Double numRecibosPagados = row.getCell(NUMERO_DE_RECIBOS_PAGADOS).getNumericCellValue();
                Double importeDerechoPoliza = row.getCell(IMPORTE_DERECHO_POLIZA).getNumericCellValue();
                Double importeSubsecuentes = row.getCell(IMPORTE_SUBSECUENTES).getNumericCellValue();
                //constriye poliza con sus recibos
                Poliza poliza = new Poliza(numPoliza, aseguradora, 0, ramo, producto, plan, inicioVigencia, conductoDeCobro, formaDePago, dinero);
                poliza.generarRecibos(numRecibosPagados.intValue(), importeDerechoPoliza.floatValue(), importeSubsecuentes.floatValue());//TODO: redondeo de int puede causar error

//                asegurado.agregarPoliza(poliza);
                //si el asegurado existe agrega la nueva poliza
                //si no agrega el nuevo asegurado
                String key = asegurado.getNombre() + asegurado.getApellidoPaterno() + asegurado.getApellidoMaterno();
                if (asegurados.containsKey(key)) {
                    asegurados.get(key).agregarPoliza(poliza);
                } else {
                    asegurado.agregarPoliza(poliza);
                    asegurados.put(key, asegurado);
                }

            }

        } catch (IOException ex) {
            throw ex;
//            Logger.getLogger(ExcelImporter.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidFormatException ex) {
            throw ex;
//            Logger.getLogger(ExcelImporter.class.getName()).log(Level.SEVERE, null, ex);
        } catch (EncryptedDocumentException ex) {
            throw ex;
//            Logger.getLogger(ExcelImporter.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new ArrayList<>(asegurados.values());
    }
}
