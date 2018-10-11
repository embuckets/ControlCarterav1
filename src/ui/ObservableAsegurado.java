/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import dominio.Asegurado;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.BooleanPropertyBase;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;

/**
 *
 * @author emilio
 */
public class ObservableAsegurado {

    public StringProperty nombreProperty;
    public StringProperty nacimientoProperty;
    public BooleanProperty notificarProperty;
    public List<ObservablePoliza> polizasProperty;

    public ObservableAsegurado(Asegurado asegurado) {
        this.nombreProperty = new SimpleStringProperty(asegurado.getNombre());
        this.nacimientoProperty = new SimpleStringProperty(asegurado.getNacimiento().get().toString());
        this.notificarProperty = new SimpleBooleanProperty(false);
    }

    public StringProperty nombreProperty() {
        return nombreProperty;
    }

    public StringProperty nacimientoProperty() {
        return nacimientoProperty;
    }

    public BooleanProperty notificarProperty() {
        return notificarProperty;
    }

    public Boolean isNotificado() {
        return notificarProperty.get();
    }

    public void setNotificado(Boolean notificar) {
        notificarProperty.setValue(notificar);
    }
}
