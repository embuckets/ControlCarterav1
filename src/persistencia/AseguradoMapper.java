/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistencia;

import dominio.Asegurado;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import static persistencia.BaseDatos.printSQLException;
import utils.Parametros;
import utils.ParametrosAsegurado;

/**
 *
 * @author emilio
 */
public class AseguradoMapper implements Mapper {
    private final String COL_NOMBRE = "nombre";
    private final String COL_APELLIDO_PATERNO = "apPaterno";
    private final String COL_APELLIDO_MATERNO = "apMaterno";
    

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

    @Override
    public void create(Object object) throws SQLException{
        Asegurado asegurado = (Asegurado)object;
        PreparedStatement prepStatement = null;
        try {
            Connection conn = BaseDatos.getInstance().getConnection();
            prepStatement = conn.prepareStatement("INSERT INTO asegurados VALUES(DEFAULT,?,?,?,?,?)");
            prepStatement.setString(1, asegurado.getNombre());
            //TODO: todo mayusculas?? o Capitalize???
            prepStatement.setString(2, asegurado.getApellidoPaterno().map(String::toString).orElse(""));
            prepStatement.setString(3, asegurado.getApellidoMaterno().map(String::toString).orElse(""));
            prepStatement.setString(4, asegurado.getRFC().map(String::toString).orElse(null));
            prepStatement.setDate(5, (asegurado.getNacimiento().isPresent() ? java.sql.Date.valueOf(asegurado.getNacimiento().get()) : null));
            prepStatement.executeUpdate();
            
        } catch (SQLException ex) {
            Logger.getLogger(AseguradoMapper.class.getName()).log(Level.SEVERE, null, ex);
            throw ex;
        } finally {
            if (prepStatement != null) {
                try {
                    prepStatement.close();
                    prepStatement = null;
                } catch (SQLException ex) {
                    printSQLException(ex);
                }
            }
        }
        
    }

    @Override
    public <T> Set<T> read(int id) {
        
    }

}
