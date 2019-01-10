/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dominio;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author emilio
 */
public class Asegurado {

    private int id;
    private String nombre;
    private Optional<String> apellidoPaterno;
    private Optional<String> apellidoMaterno;
    private List<String> telefonos;
    private List<String> emails;
    private Optional<String> RFC;
    private Optional<Domicilio> domicilio;
    private Optional<LocalDate> nacimiento;
    private List<Poliza> polizas;
    //TODO: private File[] documentos ? se pone aqui o solo en la base de datos

    public Asegurado(String nombre, String apellidoPaterno, String apellidoMaterno) {
        this.nombre = nombre;
        this.apellidoPaterno = Optional.ofNullable(apellidoPaterno);
        this.apellidoMaterno = Optional.ofNullable(apellidoMaterno);
        this.telefonos = new ArrayList<String>();
        this.emails = new ArrayList<String>();
        this.RFC = Optional.empty();
        this.domicilio = Optional.empty();
        this.nacimiento = Optional.empty();
        this.polizas = new ArrayList<Poliza>();
    }
    
    

    @Override
    public boolean equals(Object o) {
        if (o instanceof Asegurado) {
            Asegurado obj = (Asegurado) o;
            if (this.nombre.equalsIgnoreCase(obj.getNombre())) {
                if (this.apellidoPaterno.orElse("").equalsIgnoreCase(obj.getApellidoPaterno().orElse(""))) {
                    return this.apellidoMaterno.orElse("").equalsIgnoreCase(obj.getApellidoMaterno().orElse(""));
                }
            }
        }
        return false;
    }

    public void addPoliza(Poliza poliza) {
        polizas.add(poliza);
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public Optional<String> getApellidoPaterno() {
        return apellidoPaterno;
    }

    public Optional<String> getApellidoMaterno() {
        return apellidoMaterno;
    }

    public List<String> getTelefonos() {
        return telefonos;
    }

    public List<String> getEmails() {
        return emails;
    }

    public Optional<String> getRFC() {
        return RFC;
    }

    public void setRFC(String rfc) {
        this.RFC = Optional.ofNullable(rfc);
    }

    public void agregarCorreo(String correo) {
        this.emails.add(correo);
    }

    public void agregarTelefono(String telefono) {
        this.telefonos.add(telefono);
    }

    public void agregarPoliza(Poliza poliza) {
        this.polizas.add(poliza);
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<Poliza> getPolizas() {
        return polizas;
    }

    public Optional<Domicilio> getDomicilio() {
        return domicilio;
    }

    public void setDomicilio(Optional<Domicilio> domicilio) {
        this.domicilio = domicilio;
    }

    public Optional<LocalDate> getNacimiento() {
        return nacimiento;
    }

    public void setNacimiento(Optional<LocalDate> nacimiento) {
        this.nacimiento = nacimiento;
    }

    public void setNacimiento(LocalDate nacimiento) {
        this.nacimiento = Optional.ofNullable(nacimiento);
    }

    public void setNacimiento(java.sql.Date nacimiento) {
        this.nacimiento = (nacimiento == null ? Optional.empty() : Optional.ofNullable(nacimiento.toLocalDate()));
    }

    @Override
    public String toString() {
        if (!apellidoPaterno.get().isEmpty() && !apellidoMaterno.get().isEmpty()) {

            return nombre + " " + apellidoPaterno.get() + " " + apellidoMaterno.get();
        } else {
            return nombre;
        }
    }

}
