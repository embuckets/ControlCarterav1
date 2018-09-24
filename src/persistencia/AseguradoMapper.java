/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistencia;

import dominio.Asegurado;
import java.sql.DatabaseMetaData;
import java.util.HashSet;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import utils.Parametros;
import utils.ParametrosAsegurado;

/**
 *
 * @author emilio
 */
public class AseguradoMapper implements Mapper {
    

    //
    public AseguradoMapper() {
        
        
    }

    @Override
    public <T> Set<T> read(Parametros propiedades) {
        ParametrosAsegurado params = (ParametrosAsegurado) propiedades;
        String nombre = "", paterno = "", materno = "";
        nombre = params.getNombre();
//        
//        Set<Entry<Object, Object>> entries = params.getPropiedades().entrySet();
//        for (Entry entry : entries) {
//            if (entry.getKey().equals("nombre")) {
//                nombre = (String) entry.getValue();
//            }
//            if (entry.getKey().equals("paterno")) {
//                paterno = (String) entry.getValue();
//            }
//            if (entry.getKey().equals("materno")) {
//                materno = (String) entry.getValue();
//            }
//        }
        HashSet<T> asegurados = new HashSet<>();
        Asegurado asegurado = new Asegurado(nombre, paterno, materno);
        asegurados.add((T) asegurado);
        return asegurados;
    }

    @Override
    public <T> Set<T> readAll() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
