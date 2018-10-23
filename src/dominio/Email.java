/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dominio;

/**
 *
 * @author emilio
 */
public class Email {

    private int aseguradoId;
    private String email;

    public Email(int aseguradoId, String correo) {
        this.aseguradoId = aseguradoId;
        this.email = correo;
    }

    public int getAseguradoId() {
        return aseguradoId;
    }

    public void setAseguradoId(int aseguradoId) {
        this.aseguradoId = aseguradoId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
