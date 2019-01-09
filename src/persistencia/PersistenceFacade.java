/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistencia;

import dominio.Asegurado;
import dominio.Domicilio;
import dominio.Email;
import dominio.Poliza;
import dominio.Telefono;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import utils.Parametros;

/**
 *
 * @author emilio
 */
public class PersistenceFacade {

    private Map<Class, Mapper> mappers;
    private BaseDeDatos baseDeDatos;

    public PersistenceFacade() {
        this.mappers = new HashMap<>();
        this.mappers.put(Asegurado.class, new AseguradoMapper());
        mappers.put(Asegurado.class, new AseguradoMapper());
        mappers.put(Poliza.class, new PolizaMapper());
        mappers.put(Telefono.class, new TelefonoMapper());
        mappers.put(Domicilio.class, new DomicilioMapper());
        mappers.put(Email.class, new EmailMapper());
        this.baseDeDatos = BaseDeDatos.getInstance();
    }
    //TODO: Casi todos se pueden buscar por id. 
    //ya sea por llave primaria o por foreign key 
    //que identifica varios registros como telefonos de un asegurado
//    public <T> Set<T> buscar(Parametros parametros, Class clase) {
//        return mappers.get(clase).<T>read(parametros);
//    }

    public <T> Set<T> buscar(Parametros parametros, Class clase) throws SQLException {
        return mappers.get(clase).<T>read(parametros);
    }

    public <T> Set<T> buscar(int id, Class clase) throws SQLException {
        return mappers.get(clase).<T>read(id);
    }

    public <T> Set<T> buscarTodos(Class clase) {
        return mappers.get(clase).readAll();
    }

    public void create(Object object, Class clase) throws SQLException {
        mappers.get(clase).create(object);
    }

    public void detenerBaseDeDatos() throws SQLException {
        baseDeDatos.detenerBaseDeDatos();
    }

    public void conectarABaseDeDatos() throws SQLException {
        baseDeDatos.contectarABaseDeDatos();
    }

    public void crearBaseDeDatos() throws SQLException {
        baseDeDatos.crearBaseDeDatos();
    }

    public void beginTransaction() throws SQLException {
        baseDeDatos.beginTransaction();
    }

    public void endTransaction() throws SQLException {
        baseDeDatos.endTransaction();
    }

    public void commit() throws SQLException {
        baseDeDatos.commit();
    }

    public void rollback() throws SQLException {
        baseDeDatos.rollback();
    }

}
