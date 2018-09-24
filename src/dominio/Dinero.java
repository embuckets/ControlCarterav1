/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dominio;

import java.math.BigDecimal;

/**
 *
 * @author emilio
 */
public class Dinero {

    public static enum Moneda {
        PESOS, DOLARES, UMAM
    }
    private BigDecimal cantidad;
    private Moneda moneda;

    public Dinero(BigDecimal cantidad, Moneda moneda) {
        this.moneda = moneda;
        this.cantidad = cantidad;
    }
    
    public Dinero(BigDecimal cantidad, String moneda) {
        this.moneda = determinaMoneda(moneda);
        this.cantidad = cantidad;
    }

    final public Moneda determinaMoneda(String moneda) {
        switch (moneda.toLowerCase()) {
            case "pesos":
                return Moneda.PESOS;
            case "dolares":
                return Moneda.DOLARES;
            case "umam":
                return Moneda.UMAM;
            default:
                return Moneda.PESOS;
        }
    }

    public Moneda getMoneda() {
        return moneda;
    }

    public BigDecimal getCantidad() {
        return cantidad;
    }

    @Override
    public String toString() {
        return "Dinero{" + "cantidad=" + cantidad + ", moneda=" + moneda + '}';
    }
    
    

}
