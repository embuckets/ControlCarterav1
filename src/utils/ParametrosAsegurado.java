/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.time.LocalDate;
import java.util.Properties;

/**
 *
 * @author emilio
 */
public class ParametrosAsegurado extends Parametros {
    

    public ParametrosAsegurado() {
        super();
    }

    public void putId(String id) {
        propiedades.put("aseguradoId", id);
    }
    
    public void putNombre(String nombre) {
        propiedades.put("nombre", nombre);
    }
    
    public void putApellidoPaterno(String paterno) {
        propiedades.put("apPaterno", paterno);
    }
    
    public void putApellidoMaterno(String materno) {
        propiedades.put("apMaterno", materno);
        
    }
    
    public void putRfc(String rfc) {
        propiedades.put("rfc", rfc);
    }

    public void putNacimiento(LocalDate nacimiento) {
        propiedades.put("nacimiento", nacimiento);
    }

    public String getNombre() {
        return propiedades.getProperty("nombre");
    }

    public Properties getPropiedades() {
        return propiedades;
    }

}
