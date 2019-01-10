/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import dominio.Asegurado;
import dominio.Poliza;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.property.Property;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.util.Callback;

/**
 * FXML Controller class
 *
 * @author emilio
 */
public class HomeController implements Initializable {

    ObservableList<Asegurado> datosTableCumple;

    @FXML
    private TreeTableView treeAsegurados;
    @FXML
    private TreeTableColumn nombreTreeTableColumn;
    @FXML
    private TreeTableColumn numeroPolizTreeTableColumn;

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

    @FXML
    private Button buttonNuevo;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        assert tableCumple != null : "fx:id=\"tableCumple was not injected\"";
        configTablaCumple();
        fillTablaAsegurados();
        tableCumple.setItems(initializeAsegurados());

    }

    private void fillTablaAsegurados() {
        List<Asegurado> list = getAsegurados();
        
        List<ObservableAsegurado> obsList = createObservableAsegurados(getAsegurados());
        TreeItem root = new TreeItem(new Asegurado("Control Cartera", "", ""));
        root.setExpanded(true);

        nombreTreeTableColumn.setCellValueFactory(new TreeItemPropertyValueFactory("nombre"));
        numeroPolizTreeTableColumn.setCellValueFactory(new TreeItemPropertyValueFactory("numero"));

        obsList.stream().forEach((asegurado) -> {
            TreeItem aseguradoItem = new TreeItem(asegurado);
            root.getChildren().add(aseguradoItem);
            asegurado.getObservablePolizas().stream().forEach((poliza) -> {
                aseguradoItem.getChildren().add(new TreeItem(poliza));
            });
        });

        treeAsegurados.setRoot(root);
        treeAsegurados.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println("oldValue: " + oldValue);//item previamente seleccionado
            System.out.println("newValue: " + newValue);//item actualmente seleccionado ObservableAsegurado | ObservablePoliza
            System.out.println("observable: " + observable);//item actualmente seleccionado ObservableAsegurado | ObservablePoliza
            System.out.println();
        });
//        treeAsegurados.getSelectionModel().selectedItemProperty().addListener();
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

    private List<Asegurado> getAsegurados() {
        Asegurado asegurado1 = new Asegurado("emilio", "hernandez", "segovia");
        Asegurado asegurado2 = new Asegurado("daniel", "hernandez", "segovia");
        asegurado1.setNacimiento(LocalDate.of(1993, Month.MAY, 22));
        asegurado2.setNacimiento(LocalDate.of(1994, Month.SEPTEMBER, 23));

        Poliza poliza1 = new Poliza();
        poliza1.setNumero("numeor1");
        poliza1.setAseguradora("AXA");
        Poliza poliza2 = new Poliza();
        poliza2.setNumero("numeor2");
        poliza2.setAseguradora("GNP");
        asegurado1.addPoliza(poliza1);
        asegurado1.addPoliza(poliza2);

        Poliza poliza3 = new Poliza();
        poliza3.setNumero("numeor3");
        poliza3.setAseguradora("PLAN SEGURO");
        asegurado2.addPoliza(poliza3);
        List<Asegurado> list = new ArrayList<>();
        list.add(asegurado1);
        list.add(asegurado2);
        return list;
    }
    
    private List<ObservableAsegurado> createObservableAsegurados (List<Asegurado> list){
        List<ObservableAsegurado> obsList = new ArrayList<>();
        for (Asegurado ase : list) {
            ObservableAsegurado obsAsegurdo = new ObservableAsegurado(ase);
            for (Poliza pol : ase.getPolizas()) {
                ObservablePoliza obsPoliza = new ObservablePoliza(pol);
                obsAsegurdo.addObservablePoliza(obsPoliza);
            }
            obsList.add(obsAsegurdo);
        }
        return obsList;
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
//                        System.out.println(asegurado.nombreProperty + ", " + asegurado.notificarProperty);
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
//            System.out.println(asegurado.nombreProperty + ", " + asegurado.notificarProperty);

        }

        tableCumple.setItems(asegurados);

    }

    public void abrirSceneNuevoAsegurado(ActionEvent event) throws IOException {
        Main.getInstance().changeSceneContent("NuevoAsegurado.fxml");
//        try {
//            Parent parent = FXMLLoader.load(getClass().getResource("NuevoAsegurado.fxml"));
//            
////            VBox page = (VBox) FXMLLoader.load(getClass().getResource("NuevoAsegurado.fxml"));
//            Scene newScene = new Scene(parent);
//            
//            Stage mainWindow = (Stage) ((Node)event.getSource()).getScene().getWindow();
//            mainWindow.getScene().getRoot().setEffect(new GaussianBlur());
//            
//            Stage popUpStage = new Stage(StageStyle.DECORATED);
//            popUpStage.initOwner(mainWindow);
//            popUpStage.initModality(Modality.APPLICATION_MODAL);
//            popUpStage.setScene(newScene);
////            popUpStage.setMaximized(true);
//            popUpStage.show();
//            
////            mainWindow.setScene(newScene);
////            mainWindow.setMaximized(true);
////            mainWindow.show();
//            
//        } catch (IOException ex) {
//            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        
    }

}
