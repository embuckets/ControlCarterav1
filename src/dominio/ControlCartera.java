/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dominio;

import exceptions.AsociacionARegistroInexistenteException;
import exceptions.BusquedaException;
import exceptions.CreacionCarteraException;
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

    public void guardar(Object object, Class clase) throws RegistroDuplicadoException, SQLException, AsociacionARegistroInexistenteException {
        try {
            persistenceFacade.create(object, clase);
        } catch (SQLException ex) {
            if (ex.getErrorCode() == 23505) {
                throw new RegistroDuplicadoException(ex.getMessage(), ex);
            } else if (ex.getErrorCode() == 23503){
                throw new AsociacionARegistroInexistenteException(ex.getMessage(), ex);
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
