/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dominio;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author emilio
 */
public class Poliza {

    public static enum ConductoDeCobro {
        AGENTE, CAT, CASH, OTRO
    };

    public static enum FormaDePago {
        MENSUAL, TRIMESTRAL, SEMESTRAL, ANUAL, OTRO
    };

    public static enum Ramo {
        AUTOS, HOGAR, TRANSPORTE, EMPRESARIAL, RESPONSABILIDAD_CIVIL, GASTOS_MEDICOS, ACCIDENTES_PERSONALES, VIDA, INVERSION, OTRO
    };
    private int id;

    private String numero;
    private String aseguradora;
    private int aseguradoId;
    private Ramo ramo;
    private String producto;
    private String plan;
    private LocalDate inicioVigencia;
    private LocalDate finVigencia;//TODO: es una año? en polizas de vida es un plazo mas largo?
    private ConductoDeCobro conductoDeCobro;
    private FormaDePago formaDePago;
    private Dinero prima;
    private List<Recibo> recibos;
    private Optional<Dinero> deducible;
    private Optional<Dinero> sumaAsegurada;
    private Optional<Short> coaseguro;//porcentaje
    private Optional<String> comentarios;//TODO: hacer objeto beneficiario para recordar cumpleanos? objeto auto?

    public Poliza(String numero, String aseguradora, int aseguradoId, Ramo ramo, String producto, String plan, LocalDate inicioVigencia, ConductoDeCobro conductoDeCobro, FormaDePago formaDePago, Dinero prima) {
        this.numero = numero;
        this.aseguradora = aseguradora;
        this.aseguradoId = aseguradoId;
        this.ramo = ramo;
        this.producto = producto;
        this.plan = plan;
        this.inicioVigencia = inicioVigencia;
        this.finVigencia = inicioVigencia.plusYears(1);
        this.conductoDeCobro = conductoDeCobro;
        this.formaDePago = formaDePago;
        this.prima = prima;
        this.recibos = new ArrayList<Recibo>();
        this.deducible = Optional.empty();
        this.sumaAsegurada = Optional.empty();
        this.coaseguro = Optional.empty();
        this.comentarios = Optional.empty();
    }

    public Poliza(String numero, String aseguradora, int aseguradoId, String ramo, String producto, String plan, LocalDate inicioVigencia, String conductoDeCobro, String formaDePago, Dinero prima) {
        this.numero = numero;
        this.aseguradora = aseguradora;
        this.aseguradoId = aseguradoId;
        this.ramo = determinarRamo(ramo);
        this.producto = producto;
        this.plan = plan;
        this.inicioVigencia = inicioVigencia;
        this.finVigencia = inicioVigencia.plusYears(1);
        this.conductoDeCobro = determinarConductoDeCobro(conductoDeCobro);
        this.formaDePago = determinarFormaDePago(formaDePago);
        this.prima = prima;
        this.recibos = new ArrayList<Recibo>();
        this.deducible = Optional.empty();
        this.sumaAsegurada = Optional.empty();
        this.coaseguro = Optional.empty();
        this.comentarios = Optional.empty();
    }

    public Poliza() {
        this.recibos = new ArrayList<Recibo>();
        this.deducible = Optional.empty();
        this.sumaAsegurada = Optional.empty();
        this.coaseguro = Optional.empty();
        this.comentarios = Optional.empty();
        this.ramo = Ramo.VIDA;
    }

    public Poliza(String ramo) {
        this.ramo = determinarRamo(ramo);
    }

    public void setAseguradora(String asegurdora) {
        this.aseguradora = aseguradora;
    }

    public String getNumero() {
        return numero;
    }

    public String getAseguradora() {
        return aseguradora;
    }

    public Ramo getRamo() {
        return ramo;
    }

    public String getProducto() {
        return producto;
    }

    public String getPlan() {
        return plan;
    }

    public LocalDate getInicioVigencia() {
        return inicioVigencia;
    }

    public LocalDate getFinVigencia() {
        return finVigencia;
    }

    public ConductoDeCobro getConductoDeCobro() {
        return conductoDeCobro;
    }

    public FormaDePago getFormaDePago() {
        return formaDePago;
    }

    public Dinero getPrima() {
        return prima;
    }

    public List<Recibo> getRecibos() {
        return recibos;
    }

    public Optional<Dinero> getDeducible() {
        return deducible;
    }

    public Optional<Dinero> getSumaAsegurada() {
        return sumaAsegurada;
    }

    public Optional<Short> getCoaseguro() {
        return coaseguro;
    }

    public Optional<String> getComentarios() {
        return comentarios;
    }

    public int getId() {
        return id;
    }

    public int getAseguradoId() {
        return aseguradoId;
    }

    public void setAseguradoId(int aseguradoId) {
        this.aseguradoId = aseguradoId;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public void setDeducible(Dinero deducible) {
        this.deducible = Optional.ofNullable(deducible);
    }

    public void setSumaAsegurada(Dinero sumaAsegurada) {
        this.sumaAsegurada = Optional.ofNullable(sumaAsegurada);
    }

    public void setCoaseguro(Short coaseguro) {
        this.coaseguro = Optional.ofNullable(coaseguro);
    }

    public void setComentarios(String comentarios) {
        this.comentarios = Optional.ofNullable(comentarios);
    }

    /**
     * AUTOS, HOGAR, TRANSPORTE, EMPRESARIAL, RESPONSABILIDAD_CIVIL,
     * GASTOS_MEDICOS, ACCIDENTES_PERSONALES, VIDA, INVERSION, OTRA
     *
     * @param ramoString
     * @return
     */
    final public Ramo determinarRamo(String ramoString) {
        switch (ramoString.toLowerCase()) {
            case "autos":
                return Ramo.AUTOS;
            case "hogar":
                return Ramo.HOGAR;
            case "transporte":
                return Ramo.TRANSPORTE;
            case "empresarial":
                return Ramo.EMPRESARIAL;
            case "responsabilidad civil":
                return Ramo.RESPONSABILIDAD_CIVIL;
            case "gastos medicos":
                return Ramo.GASTOS_MEDICOS;
            case "accidentes personales":
                return Ramo.ACCIDENTES_PERSONALES;
            case "vida":
                return Ramo.VIDA;
            case "inversion":
                return Ramo.INVERSION;
            default:
                return Ramo.OTRO;
        }
    }

    final public FormaDePago determinarFormaDePago(String formaDePago) {
        switch (formaDePago.toLowerCase()) {
            case "mensual":
                return FormaDePago.MENSUAL;
            case "trimestral":
                return FormaDePago.TRIMESTRAL;
            case "semestral":
                return FormaDePago.SEMESTRAL;
            case "anual":
                return FormaDePago.ANUAL;
            default:
                return FormaDePago.OTRO;
        }
    }

    final public ConductoDeCobro determinarConductoDeCobro(String conductoDeCobro) {
        switch (conductoDeCobro.toLowerCase()) {
            case "agente":
                return ConductoDeCobro.AGENTE;
            case "cat":
                return ConductoDeCobro.CAT;
            case "cash":
                return ConductoDeCobro.CASH;
            default:
                return ConductoDeCobro.OTRO;
        }
    }

    public void generarRecibos(int recibosPagados, Float importeConDerechoDePoliza, Float importeSubsecuente) {
        int recibos = cuantosRecibos();
        int siguienteMes = siguienteMes();
        LocalDate inicioVigenciaAnterior = this.inicioVigencia;
        LocalDate finVigenciaAnterior = inicioVigenciaAnterior.plusMonths(siguienteMes);
        Recibo recibo = new Recibo(this.id, inicioVigenciaAnterior, finVigenciaAnterior, new Dinero(BigDecimal.valueOf(importeConDerechoDePoliza), prima.getMoneda()));
        if (recibosPagados > 0) {
            recibo.setPagado();
            recibosPagados--;
        }
        this.recibos.add(recibo);
        for (int i = 1; i < recibos; i++) {
            inicioVigenciaAnterior = finVigenciaAnterior;
            finVigenciaAnterior = finVigenciaAnterior.plusMonths(siguienteMes);
            Dinero dinero = new Dinero(BigDecimal.valueOf(importeSubsecuente), prima.getMoneda());
            recibo = new Recibo(this.id, inicioVigenciaAnterior, finVigenciaAnterior, dinero);
            if (recibosPagados > 0) {
                recibo.setPagado();
                recibosPagados--;
            }
            this.recibos.add(recibo);
        }
    }

    private int cuantosRecibos() {
        switch (this.formaDePago) {
            case MENSUAL:
                return 12;
            case TRIMESTRAL:
                return 4;
            case SEMESTRAL:
                return 2;
            case ANUAL:
                return 1;
            default:
                return 12;
        }
    }

    private int siguienteMes() {
        switch (this.formaDePago) {
            case MENSUAL:
                return 1;
            case TRIMESTRAL:
                return 3;
            case SEMESTRAL:
                return 6;
            case ANUAL:
                return 12;
            default:
                return 1;

        }
    }

    @Override
    public String toString() {
        return "Poliza{" + "id=" + id + ", numero=" + numero + ", aseguradora=" + aseguradora + ", aseguradoId=" + aseguradoId + ", ramo=" + ramo + ", producto=" + producto + ", plan=" + plan + ", inicioVigencia=" + inicioVigencia + ", finVigencia=" + finVigencia + ", conductoDeCobro=" + conductoDeCobro + ", formaDePago=" + formaDePago + ", prima=" + prima + ", deducible=" + deducible + ", sumaAsegurada=" + sumaAsegurada + ", coaseguro=" + coaseguro + ", comentarios=" + comentarios + '}';
    }

}
