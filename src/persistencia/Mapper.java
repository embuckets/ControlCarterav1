/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistencia;

import java.util.Set;
import utils.Parametros;

/**
 *
 * @author emilio
 */
public interface Mapper {

    <T> Set<T> read(Parametros propiedades);
    <T> Set<T> readAll();
    void create(Object object);

}
