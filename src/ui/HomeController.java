/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import dominio.Asegurado;
import java.net.URL;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

/**
 * FXML Controller class
 *
 * @author emilio
 */
public class HomeController implements Initializable {

    ObservableList<Asegurado> datosTableCumple;

    @FXML
    private TableView<ObservableAsegurado> tableCumple;

    @FXML
    private TableColumn<ObservableAsegurado, String> colNombre;
    @FXML
    private TableColumn<ObservableAsegurado, String> colNacimiento;
    @FXML
    private TableColumn<ObservableAsegurado, CheckBox> colNotificar;
    
    @FXML
    private CheckBox checkNotificarCumple;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        assert tableCumple != null : "fx:id=\"tableCumple was not injected\"";
        configTablaCumple();

        tableCumple.setItems(initializeAsegurados());

    }

    private ObservableList<ObservableAsegurado> initializeAsegurados() {
        Asegurado asegurado1 = new Asegurado("emilio", "hernandez", "segovia");
        Asegurado asegurado2 = new Asegurado("daniel", "hernandez", "segovia");
        asegurado1.setNacimiento(LocalDate.of(1993, Month.MAY, 22));
        asegurado2.setNacimiento(LocalDate.of(1994, Month.SEPTEMBER, 23));
        List<ObservableAsegurado> list = new ArrayList<>();

        list.add(new ObservableAsegurado(asegurado1));
        list.add(new ObservableAsegurado(asegurado2));

        return FXCollections.observableArrayList(list);
    }

    private void configTablaCumple() {
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colNacimiento.setCellValueFactory(new PropertyValueFactory<>("nacimiento"));
        colNotificar.setCellValueFactory(new Callback<CellDataFeatures<ObservableAsegurado, CheckBox>, ObservableValue<CheckBox>>() {
            //This callback tell the cell how to bind the data model 'Registered' property to
            //the cell, itself.
            @Override
            public ObservableValue<CheckBox> call(CellDataFeatures<ObservableAsegurado, CheckBox> param) {
                ObservableAsegurado asegurado = param.getValue();
                CheckBox checkbox = new CheckBox();
                checkbox.selectedProperty().setValue(asegurado.isNotificado());
                checkbox.selectedProperty().addListener(new ChangeListener<Boolean>() {
                    @Override
                    public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                        asegurado.setNotificado(newValue);
                        System.out.println(asegurado.nombreProperty + ", " + asegurado.notificarProperty);
                    }
                });
                
                checkbox.selectedProperty().addListener(new ChangeListener<Boolean>() {
                    @Override
                    public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                        asegurado.setNotificado(checkNotificarCumple.isSelected());
//                        checkbox.setSelected(asegurado.notificarProperty.getValue());
                    }
                });
                

                return new SimpleObjectProperty<CheckBox>(checkbox);
//                return param.getValue().notificarProperty();
            }
        });
        
        
//        colNotificar.setCellFactory(CheckBoxTableCell.forTableColumn(colNotificar));

    }


    @FXML
    void notificarTodosCumple(ActionEvent event) {
        CheckBox checkBox = (CheckBox) event.getSource();
        ObservableList<ObservableAsegurado> asegurados = tableCumple.getItems();
        for (ObservableAsegurado asegurado : asegurados) {
            asegurado.setNotificado(checkBox.isSelected());
            colNotificar.getCellData(asegurado).setSelected(checkBox.isSelected());
            System.out.println(asegurado.nombreProperty + ", " + asegurado.notificarProperty);

        }
        
        tableCumple.setItems(asegurados);

    }

}
