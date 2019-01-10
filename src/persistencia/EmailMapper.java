/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistencia;

import dominio.Email;
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
public class EmailMapper implements Mapper {
@Override
    public <T> Set<T> read(Parametros propiedades) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public <T> Set<T> read(int id) throws SQLException {
        Set<T> emails = new HashSet<>();
        ResultSet resultSet = null;
        try {
            Connection conn = BaseDeDatos.getInstance().getConnection();
            Statement statement = conn.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM emails where aseguradoId = " + id);
            while (resultSet.next()) {
                Email email = new Email(resultSet.getInt(1), resultSet.getString(2));
                emails.add((T) email);
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
        return emails;
    }

    @Override
    public <T> Set<T> readAll() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void create(Object object) throws SQLException {
        Email email = (Email) object;
        PreparedStatement preparedStatement = null;
        try {
            Connection conn = BaseDeDatos.getInstance().getConnection();
            preparedStatement = conn.prepareStatement("INSERT INTO emails VALUES(?,?)");
            preparedStatement.setInt(1, email.getAseguradoId());
            preparedStatement.setString(2, email.getEmail());
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
