/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import dominio.Asegurado;
import dominio.ControlCartera;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import persistencia.BaseDeDatos;

/**
 * FXML Controller class
 *
 * @author emilio
 */
public class NuevoAseguradoController implements Initializable {

    @FXML
    private RadioButton radioPersonaFisica;
    @FXML
    private RadioButton radioPersonaMoral;
    @FXML
    private TextField textNombre;
    @FXML
    private TextField textPaterno;
    @FXML
    private TextField textMaterno;
    @FXML
    private TextField textTelefono;
    @FXML
    private TextField textEmail;
    @FXML
    private TextField textRfc;
    @FXML
    private TextField textCalle;
    @FXML
    private TextField textNoExterior;
    @FXML
    private TextField textNoInterior;
    @FXML
    private TextField textCodigoPostal;
    @FXML
    private TextField textColonia;
    @FXML
    private TextField textDelegacion;
    @FXML
    private ComboBox<String> comboBoxEstado;
    @FXML
    private ComboBox<String> comboBoxDocumento;
    @FXML
    private TextArea textNota;

    /**
     * Initializes the controller class.o
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configEstados();
        configDocumentos();
        configTipoPersona();
    }

    private void configEstados() {
        String[] estados = {"Aguascalientes", " Baja California", " Baja California Sur",
            " Campeche", " Chiapas", " Chihuahua", " Ciudad de México",
            " Coahuila", " Colima", " Durango", " Estado de México", " Guanajuato",
            " Guerrero", " Hidalgo", " Jalisco", " Michoacán", " Morelos",
            " Nayarit", " Nuevo León", " Oaxaca", " Puebla", " Querétaro",
            " Quintana Roo", " San Luis Potosí", " Sinaloa", " Sonora",
            " Tabasco", " Tamaulipas", " Tlaxcala", " Veracruz", " Yucatán", " Zacatecas"};
        ObservableList<String> estadosList = FXCollections.observableArrayList(estados);
        comboBoxEstado.getItems().addAll(estadosList);
        comboBoxEstado.getSelectionModel().select(6);
    }

    private void configDocumentos() {
        String[] documentos = {"Identificacion", "Comprobante de domicilio"};
        ObservableList<String> estadosList = FXCollections.observableArrayList(documentos);
        comboBoxDocumento.getItems().addAll(estadosList);
    }

    private void configTipoPersona() {
        ToggleGroup group = new ToggleGroup();
        radioPersonaFisica.setToggleGroup(group);
        radioPersonaFisica.setSelected(true);
        radioPersonaMoral.setToggleGroup(group);
    }

    public void homePage(ActionEvent event) throws IOException {
        Main.getInstance().changeSceneContent("Home.fxml");
//        
//        try {
//            Parent parent = FXMLLoader.load(getClass().getResource("Home.fxml"));
//            Scene newScene = new Scene(parent);
//            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
//            stage.setScene(newScene);
//
//        } catch (IOException ex) {
//            Logger.getLogger(NuevoAseguradoController.class.getName()).log(Level.SEVERE, null, ex);
//        }
    }

    public void guardar(ActionEvent event) throws IOException {
        String nombre = textNombre.getText();
        String paterno = textPaterno.getText();
        String materno = textMaterno.getText();
        //TODO: Validar los valores

        Asegurado asegurado = new Asegurado(nombre, paterno, materno);
        try {
            //        Main.getInstance().setNombre(nombre);
            //TODO: insertat a la base de datos deberia ser recurrente?? con Task de javafx??
            ControlCartera.getInstance().guardar(asegurado, asegurado.getClass());
            //controlCartera dame el id del asegurado
            //guuardar(domicilio, Domicilio.getClass())
            //guardar telefono
            //guardar notas, etc
            
            //asegurado home
        } catch (SQLException ex) {
            Logger.getLogger(NuevoAseguradoController.class.getName()).log(Level.SEVERE, null, ex);
            BaseDeDatos.printSQLException(ex);
            return;
        }
        
//        Main.getInstance().changeSceneContent("AseguradoHome.fxml");

        FXMLLoader loader = new FXMLLoader(Main.class.getResource("AseguradoHome.fxml"), null, new JavaFXBuilderFactory());
        Parent parent = loader.load();
        AseguradoHomeController controller = loader.<AseguradoHomeController>getController();
        controller.setAsegurado(asegurado);
//        loader.setController(controller);
        Main.getInstance().changeSceneContent(parent);
        
    }

}
