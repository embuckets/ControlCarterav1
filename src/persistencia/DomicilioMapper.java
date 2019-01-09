/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistencia;

import dominio.Domicilio;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;
import static persistencia.BaseDeDatos.printSQLException;
import utils.Parametros;

/**
 *
 * @author emilio
 */
public class DomicilioMapper implements Mapper {

    @Override
    public <T> Set<T> read(Parametros propiedades) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /*
    + "aseguradoId INT NOT NULL REFERENCES asegurados(aseguradoId),"
                + "calle varchar(50) NOT NULL,"
                + "exterior varchar(50) NOT NULL,"
                + "interior varchar(20),"
                + "codPostal char(5) NOT NULL,"
                + "colonia varchar(50) NOT NULL,"
                + "delegacion varchar(50) NOT NULL,"
                + "estado varchar(50) NOT NULL REFERENCES estados(estado)"//TODO: deberia crear tabla estados?? proly yes
                + ")";
     */
    @Override
    public <T> Set<T> read(int id) throws SQLException {
        Set<T> domicilios = new HashSet<>();
        ResultSet resultSet = null;
        try {
            Connection conn = BaseDeDatos.getInstance().getConnection();
            Statement statement = conn.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM domicilios where aseguradoId = " + id);
            while (resultSet.next()) {
                Domicilio domicilio = new Domicilio(resultSet.getInt(1));
                domicilio.setCalle(resultSet.getString(2));
                domicilio.setNumeroExterior(resultSet.getString(3));
                domicilio.setNumeroInterior(resultSet.getString(4));
                domicilio.setCodigoPostal(resultSet.getString(5));
                domicilio.setCodigoPostal(resultSet.getString(6));
                domicilio.setColonia(resultSet.getString(7));
                domicilio.setDelegacion(resultSet.getString(8));
                domicilio.setEstado(resultSet.getString(9));
                domicilios.add((T) domicilio);
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
        return domicilios;
    }

    @Override
    public <T> Set<T> readAll() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void create(Object object) throws SQLException {
        Domicilio domicilio = (Domicilio) object;
        PreparedStatement preparedStatement = null;
        try {
            Connection conn = BaseDeDatos.getInstance().getConnection();
            preparedStatement = conn.prepareStatement("INSERT INTO domicilios VALUES(?,?,?,?,?,?,?,?)");
            preparedStatement.setInt(1, domicilio.getAseguradoId());
            preparedStatement.setString(2, domicilio.getCalle());
            preparedStatement.setString(3, domicilio.getNumeroExterior());
            preparedStatement.setString(4, domicilio.getNumeroInterior());
            preparedStatement.setString(5, domicilio.getCodigoPostal());
            preparedStatement.setString(6, domicilio.getColonia());
            preparedStatement.setString(7, domicilio.getDelegacion());
            preparedStatement.setString(8, domicilio.getEstado());
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            throw ex;
        } finally {
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                    preparedStatement = null;
                } catch (SQLException e) {
                    throw e;
                }
            }
        }
    }

}
