/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dominio;

import exceptions.AsociacionARegistroInexistenteException;
import exceptions.BusquedaException;
import exceptions.CreacionCarteraException;
import exceptions.DatosInvalidosException;
import exceptions.DatosVaciosException;
import exceptions.DetenerCarteraException;
import exceptions.NoExisteCarteraException;
import exceptions.RegistroDuplicadoException;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import persistencia.PersistenceFacade;
import utils.Parametros;

/**
 * representa el sistema
 *
 * @author emilio
 */
public class ControlCartera {

    private static ControlCartera instance;
    private PersistenceFacade persistenceFacade;
//    private BaseDatos baseDeDatos;

    private ControlCartera() {
        persistenceFacade = new PersistenceFacade();
//        this.baseDeDatos = BaseDatos.getInstance();
    }

    public static ControlCartera getInstance() {
        if (instance == null) {
            instance = new ControlCartera();
        }
        return instance;
    }

    public void guardar(Object object, Class clase) throws RegistroDuplicadoException, SQLException, AsociacionARegistroInexistenteException, DatosVaciosException, DatosInvalidosException {
        try {
            persistenceFacade.create(object, clase);
        } catch (SQLException ex) {
            if ("23505".equals(ex.getSQLState())) {
                throw new RegistroDuplicadoException(ex.getMessage(), ex);
            } else if ("23503".equals(ex.getSQLState())) {
                throw new AsociacionARegistroInexistenteException(ex.getMessage(), ex);
            } else if ("23502".equals(ex.getSQLState())) {
                throw new DatosVaciosException(ex.getMessage(), ex);
            } else if ("23513".equals(ex.getSQLState())) {
                throw new DatosInvalidosException(ex.getMessage(), ex);
            } else {
                throw ex;
            }
        }
    }

    public void detenerCartera() throws DetenerCarteraException {
        try {
            persistenceFacade.detenerBaseDeDatos();
        } catch (SQLException ex) {
            throw new DetenerCarteraException(ex.getMessage(), ex);
        }
    }

    public void conectarACartera() throws NoExisteCarteraException {
        try {
            persistenceFacade.conectarABaseDeDatos();
        } catch (SQLException ex) {
            throw new NoExisteCarteraException(ex.getMessage(), ex);
        }
    }

    public void crearCartera() throws CreacionCarteraException {
        try {
            persistenceFacade.crearBaseDeDatos();
        } catch (SQLException ex) {
            throw new CreacionCarteraException(ex.getMessage(), ex);
        }
    }

    public void empezarTransaccion() throws SQLException {
        try {
            persistenceFacade.beginTransaction();
        } catch (SQLException ex) {
            throw ex;
        }
    }

    public void terminarTransaccion() throws SQLException {
        try {
            persistenceFacade.endTransaction();
        } catch (SQLException ex) {
            throw ex;
        }
    }

    public void commit() throws SQLException {
        try {
            persistenceFacade.commit();
        } catch (SQLException ex) {
            throw ex;
        }
    }

    public void rollback() throws SQLException {
        try {
            persistenceFacade.rollback();
        } catch (SQLException ex) {
            throw ex;
        }
    }

    public <T> Set<T> buscarTodos(Class clase) {
        return persistenceFacade.buscarTodos(clase);
    }

    public <T> Set<T> buscar(Parametros parametros, Class clase) throws BusquedaException {
        try {
            return persistenceFacade.buscar(parametros, clase);
        } catch (SQLException ex) {
            throw new BusquedaException(ex.getMessage(), ex);
        }
    }

    public <T> Set<T> buscar(int id, Class clase) throws BusquedaException, SQLException {
        try {
            return persistenceFacade.buscar(id, clase);
        } catch (SQLException ex) {
            throw ex;
//            throw new BusquedaException(ex.getMessage(), ex);
        }
    }

}
