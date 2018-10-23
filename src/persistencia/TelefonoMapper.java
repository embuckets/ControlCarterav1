/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistencia;

import dominio.Telefono;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;
import utils.Parametros;
import static persistencia.BaseDeDatos.printSQLException;

/**
 *
 * @author emilio
 */
public class TelefonoMapper implements Mapper {

    @Override
    public <T> Set<T> read(Parametros propiedades) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public <T> Set<T> read(int id) throws SQLException {
        Set<T> telefonos = new HashSet<>();
        ResultSet resultSet = null;
        try {
            Connection conn = BaseDeDatos.getInstance().getConnection();
            Statement statement = conn.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM telefonos where aseguradoId = " + id);
            while (resultSet.next()) {
                Telefono telefono = new Telefono(resultSet.getInt(1), resultSet.getString(2));
                telefonos.add((T) telefono);
            }
        } catch (SQLException ex) {
            throw ex;
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                    resultSet = null;
                } catch (SQLException ex) {
                    throw ex;
                }
            }
        }
        return telefonos;
    }

    @Override
    public <T> Set<T> readAll() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void create(Object object) throws SQLException {
        Telefono telefono = (Telefono) object;
        PreparedStatement preparedStatement = null;
        try {
            Connection conn = BaseDeDatos.getInstance().getConnection();
            preparedStatement = conn.prepareStatement("INSERT INTO telefonos VALUES(?,?)");
            preparedStatement.setInt(1, telefono.getAseguradoId());
            preparedStatement.setString(2, telefono.getTelefono());
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            throw ex;
        } finally {
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                    preparedStatement = null;
                } catch (SQLException e) {
                    printSQLException(e);
                }
            }
        }
    }

}
