/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dominio;

import persistencia.BaseDatos;
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
    public static ControlCartera getInstance(){
        if (instance == null){
            instance = new ControlCartera();
        }
        return instance;
    }

    public void guardar(Object object, Class clase) {
        persistenceFacade.create(object, clase);

    }

}
