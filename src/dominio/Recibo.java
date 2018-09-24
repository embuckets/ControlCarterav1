/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dominio;

import java.time.LocalDate;

/**
 *
 * @author emilio
 */
public class Recibo {

    public static enum Cobranza {
        PENDIENTE, PAGADO
    }

    private LocalDate cubreDesde;
    private LocalDate cubreHasta;
    private Dinero importe;
    private Cobranza cobranza;
    private int polizaId;

    public Recibo(int polizaId, LocalDate cubreDesde, LocalDate cubreHasta, Dinero importe) {
        this.polizaId = polizaId;
        this.cubreDesde = cubreDesde;
        this.cubreHasta = cubreHasta;
        this.importe = importe;
        this.cobranza = Cobranza.PENDIENTE;
    }

    public Recibo(int polizaId, LocalDate cubreDesde, LocalDate cubreHasta, Dinero importe, String cobranza) {
        this.polizaId = polizaId;
        this.cubreDesde = cubreDesde;
        this.cubreHasta = cubreHasta;
        this.importe = importe;
        this.cobranza = setCobranza(cobranza);
    }

    public LocalDate getCubreDesde() {
        return cubreDesde;
    }

    public LocalDate getCubreHasta() {
        return cubreHasta;
    }

    public Dinero getImporte() {
        return importe;
    }

    //TODO: cambiar por boolean pagado()
    public Cobranza getCobranza() {
        return cobranza;
    }

    public int getPolizaId() {
        return polizaId;
    }

    public void setPolizaId(int polizaId) {
        this.polizaId = polizaId;
    }

    public void setPagado() {
        this.cobranza = Cobranza.PAGADO;
    }

    private Cobranza setCobranza(String cobranza) {
        switch (cobranza.toLowerCase()) {
            case "pendiente":
                return Cobranza.PENDIENTE;
            case "pagado":
                return Cobranza.PAGADO;
            default:
                return Cobranza.PENDIENTE;
        }
    }

    @Override
    public String toString() {
        return "Recibo{" + "cubreDesde=" + cubreDesde + ", cubreHasta=" + cubreHasta + ", importe=" + importe + ", cobranza=" + cobranza + ", polizaId=" + polizaId + '}';
    }

}
