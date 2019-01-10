/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import dominio.Poliza;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author emilio
 */
public class ObservablePoliza {
    //TODO: ID
    private StringProperty numeroProperty;
    private StringProperty aseguradoraProperty;
    private StringProperty ramoProperty;

    public ObservablePoliza(Poliza poliza) {
        this.numeroProperty = new SimpleStringProperty(poliza.getNumero());
        this.aseguradoraProperty = new SimpleStringProperty(poliza.getAseguradora());
        this.aseguradoraProperty = new SimpleStringProperty(poliza.getRamo().toString());
    }

    public StringProperty numeroProperty() {
        return numeroProperty;
    }
    
    public StringProperty aseguradoraProperty() {
        return aseguradoraProperty;
    }

    public StringProperty ramoProperty() {
        return ramoProperty;
    }

    public Property nombreProperty() {
        //TODO: regresar PLAN + PRODUCTO
        return new SimpleStringProperty(numeroProperty.get() + "," + aseguradoraProperty.get());
    }
    
            
}
