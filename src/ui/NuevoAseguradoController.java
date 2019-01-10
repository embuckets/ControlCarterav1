/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import dominio.Asegurado;
import dominio.ControlCartera;
import exceptions.AsociacionARegistroInexistenteException;
import exceptions.BusquedaException;
import exceptions.DatosInvalidosException;
import exceptions.DatosVaciosException;
import exceptions.RegistroDuplicadoException;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.Set;
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
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import persistencia.BaseDeDatos;
import utils.ParametrosAsegurado;

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
    private Label labelApellidoPaterno;
    @FXML
    private Label labelApellidoMaterno;
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
     * @param url
     * @param rb
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
    
    public void tipoPersonaHandler(ActionEvent event) {
        if (radioPersonaFisica.isSelected()){
            textPaterno.setText("");
            textPaterno.setVisible(true);
            textMaterno.setText("");
            textMaterno.setVisible(true);
            labelApellidoMaterno.setVisible(true);
            labelApellidoPaterno.setVisible(true);
        }
        else if (radioPersonaMoral.isSelected()){
            textPaterno.setText("");
            textPaterno.setVisible(false);
            textMaterno.setText("");
            textMaterno.setVisible(false);
            labelApellidoMaterno.setVisible(false);
            labelApellidoPaterno.setVisible(false);
        }
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
            ParametrosAsegurado params = new ParametrosAsegurado();
            params.putNombre(nombre);
            params.putApellidoPaterno(paterno);
            params.putApellidoMaterno(materno);

            Set<Asegurado> resultados = ControlCartera.getInstance().buscar(params, Asegurado.class);
            int id = resultados.stream().filter(a
                    -> nombre.equalsIgnoreCase(a.getNombre()) && paterno.equalsIgnoreCase(a.getApellidoPaterno().get()) && materno.equalsIgnoreCase(a.getApellidoMaterno().get())).findAny().get().getId();

            
            
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("AseguradoHome.fxml"), null, new JavaFXBuilderFactory());
            Parent parent = loader.load();
            AseguradoHomeController controller = loader.<AseguradoHomeController>getController();
            controller.setAsegurado(asegurado);
            controller.setAseguradoId(id);
//        loader.setController(controller);
            Main.getInstance().changeSceneContent(parent);
            //controlCartera dame el id del asegurado
            //guuardar(domicilio, Domicilio.getClass())
            //guardar telefono
            //guardar notas, etc

            //asegurado home
        } catch (SQLException ex) {
            BaseDeDatos.printSQLException(ex);
            return;
        } catch (RegistroDuplicadoException ex) {
            ex.printStackTrace();
            //TODO: desplegar dialogo
            return;
        } catch (BusquedaException ex) {
            ex.printStackTrace();
            return;
        } catch (AsociacionARegistroInexistenteException ex) {
            ex.printStackTrace();
            return;
        } catch (DatosVaciosException ex) {
            ex.printStackTrace();
            return;
        } catch (DatosInvalidosException ex) {
            ex.printStackTrace();
            return;
        }

//        Main.getInstance().changeSceneContent("AseguradoHome.fxml");
    }

}
