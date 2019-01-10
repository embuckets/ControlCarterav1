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
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author emilio
 */
public class ObservableAsegurado  {
    //TODO: ID
    private StringProperty nombreProperty;
    private StringProperty nacimientoProperty;
    private BooleanProperty notificarProperty;
    private ListProperty<ObservablePoliza> polizasProperty;

    public ObservableAsegurado(Asegurado asegurado) {
        this.nombreProperty = new SimpleStringProperty(asegurado.getNombre());
        this.nacimientoProperty = new SimpleStringProperty(asegurado.getNacimiento().get().toString());
        this.notificarProperty = new SimpleBooleanProperty(false);
        ObservableList<ObservablePoliza> obsList = FXCollections.observableArrayList();
        this.polizasProperty = new SimpleListProperty<ObservablePoliza>(obsList);
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

    public void addObservablePoliza(ObservablePoliza poliza) {
        polizasProperty.add(poliza);
    }

    public ListProperty<ObservablePoliza> getObservablePolizas() {
        return polizasProperty;
    }
}
