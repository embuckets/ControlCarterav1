/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.util.Properties;

/**
 *
 * @author emilio
 */
public class ParametrosPoliza extends Parametros {

    public ParametrosPoliza() {
        super();
    }

    public void setNumero(String numero) {
        propiedades.put("numero", numero);
    }

    public String getNumero() {
        return propiedades.getProperty("numero");
    }

    public Properties getProperties() {
        return propiedades;
    }

}
