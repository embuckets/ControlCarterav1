/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistencia;

import dominio.Asegurado;
import dominio.Poliza;
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
    private BaseDatos baseDeDatos;

    public PersistenceFacade() {
        this.mappers = new HashMap<>();
        this.mappers.put(Asegurado.class, new AseguradoMapper());
        mappers.put(Asegurado.class, new AseguradoMapper());
        mappers.put(Poliza.class, new PolizaMapper());
        this.baseDeDatos = BaseDatos.getInstance();
    }

    public <T> Set<T>buscar(Parametros parametros, Class clase) {
        return mappers.get(clase).<T>read(parametros);
    }
    
    public Set<Object> buscarTodos(Class clase) {
        return mappers.get(clase).readAll();
    }
    
    public void create(Object object, Class clase) throws SQLException {
        mappers.get(clase).create(object);
    }
    
    public void detenerBaseDeDatos(){
        this.baseDeDatos.detenerBaseDeDatos();
    }

}
