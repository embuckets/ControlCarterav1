/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dominio;

import exceptions.CreacionCarteraException;
import exceptions.DetenerCarteraException;
import exceptions.NoExisteCarteraException;
import exceptions.RegistroDuplicadoException;
import java.sql.SQLException;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import persistencia.PersistenceFacade;

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

    public void guardar(Object object, Class clase) throws RegistroDuplicadoException, SQLException {
        try {
            persistenceFacade.create(object, clase);
        } catch (SQLException ex) {
            if (ex.getErrorCode() == 23505){
                throw new RegistroDuplicadoException(ex.getMessage(), ex);
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

    public <T> Set<T> buscarTodos(Class clase) {
        return persistenceFacade.buscarTodos(clase);
    }
    
}
