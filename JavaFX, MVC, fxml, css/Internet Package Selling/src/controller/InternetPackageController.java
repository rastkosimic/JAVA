/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import model.DBConnection;
import model.InternetPackage;

/**
 * FXML Controller class
 *
 * @author Rastko
 */
public class InternetPackageController implements Initializable {

    @FXML
    private Button saveBtn;
    @FXML
    private Button removeBtn;
    @FXML
    private TextField idFld;
    @FXML
    private ChoiceBox<String> speedBox;
    ObservableList<String> speedList = FXCollections.observableArrayList("2", "5", "10", "20", "50", "100"); //definisanje sadrzaja choice boz kontrole

    @FXML
    private ChoiceBox<String> capacityBox;
    ObservableList<String> capacityList = FXCollections.observableArrayList("1", "5", "10", "100", "Flat");

    @FXML
    private ChoiceBox<String> contractBox;
    ObservableList<String> contractList = FXCollections.observableArrayList("1", "2");

    @FXML
    private TextField nameFld;
    @FXML
    private TextField surnameFld;
    @FXML
    private TextField addressFld;
    @FXML
    private TableView<InternetPackage> tableView;
    @FXML
    private TableColumn<InternetPackage, String> nameCol;
    @FXML
    private TableColumn<InternetPackage, String> surnameCol;
    @FXML
    private TableColumn<InternetPackage, String> addressCol;
    @FXML
    private TableColumn<InternetPackage, Integer> idCol;
    @FXML
    private TableColumn<?, ?> speedCol;
    @FXML
    private TableColumn<?, ?> capacityCol;
    @FXML
    private TableColumn<?, ?> contractCol;
    @FXML
    private Button closeBtn;

    private ObservableList<InternetPackage> data;   //lista za prikaz sadrzaja baze podataka
    private DBConnection dc;
    InternetPackage customer;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) { //automatski se popunjava tabela sa podacima iz baze

        customer = new InternetPackage();

        dc = new DBConnection();

        loadDataFromDb();

        speedBox.setValue("2"); //definisanje choice box-ova
        speedBox.setItems(speedList);

        capacityBox.setValue("Flat");
        capacityBox.setItems(capacityList);

        contractBox.setValue("1");
        contractBox.setItems(contractList);

        idFld.textProperty().bindBidirectional(customer.customerIdProperty()); //povezivanje forme sa view-a i odgovarajuceg polja modela preko instance customer
        customer.speedProperty().bind(speedBox.getSelectionModel().selectedItemProperty());
        customer.capacityProperty().bind(capacityBox.getSelectionModel().selectedItemProperty());
        customer.contractLengthProperty().bind(contractBox.getSelectionModel().selectedItemProperty());
        nameFld.textProperty().bindBidirectional(customer.nameProperty());
        surnameFld.textProperty().bindBidirectional(customer.surnameProperty());
        addressFld.textProperty().bindBidirectional(customer.addressProperty());

    }

    private void loadDataFromDb() {  //ucitavanje podataka iz baze u table view
        try {
            Connection conn = dc.Connect();
            data = FXCollections.observableArrayList();
            ResultSet rs = conn.createStatement().executeQuery("select * from customer");

            while (rs.next()) {
                data.add(new InternetPackage(rs.getString("customer_id"), rs.getString("name"), rs.getString("surname"), rs.getString("address"), rs.getString("speed"), rs.getString("capacity"), rs.getString("contract_length")));
            }
        } catch (SQLException ex) {
            System.err.println("Error: " + ex);
        }

        idCol.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        surnameCol.setCellValueFactory(new PropertyValueFactory<>("surname"));
        addressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
        speedCol.setCellValueFactory(new PropertyValueFactory<>("speed"));
        capacityCol.setCellValueFactory(new PropertyValueFactory<>("capacity"));
        contractCol.setCellValueFactory(new PropertyValueFactory<>("contractLength"));

        tableView.setItems(null);
        tableView.setItems(data);
    }

    @FXML
    private void savePackage(ActionEvent event) throws ClassNotFoundException, SQLException { //metoda kontrolera za dodavanje korisnika
        if (customer.isUserValid()) {
            customer.save(); //poziv metode save kalse InternetPackage
            nameFld.clear();    //brisanje unesenog teksta iz tekst polja
            surnameFld.clear();
            addressFld.clear();
            loadDataFromDb(); //poziv metode za ispis podataka iz baze radi osvezavanja sadrzaja tabele
        } else {

            StringBuilder errMsg = new StringBuilder();

            ArrayList<String> errList = customer.errorsProperty().get();

            for (String errList1 : errList) {
                errMsg.append(errList1);
            }

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Customer can't be added!");
            alert.setHeaderText(null);
            alert.setContentText(errMsg.toString());
            alert.showAndWait();
            errList.clear();
        }

    }

    @FXML
    private void removePackage(ActionEvent event) throws ClassNotFoundException { //metoda kontrolera za brisanje korisnika

        if (customer.isIdValid()) {
            customer.deleteCustomer(); //poziv metode deleteCustomer kalse InternetPackage
            idFld.clear();
            loadDataFromDb();
        } else {

            StringBuilder errMsg = new StringBuilder();

            ArrayList<String> errList = customer.errorsProperty().get();

            for (String errList1 : errList) {
                errMsg.append(errList1);
            }

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Customer can't be removed!");
            alert.setHeaderText(null);
            alert.setContentText(errMsg.toString());
            alert.showAndWait();
            errList.clear();
        }

    }
    
    @FXML
    private void closeForm(ActionEvent event){ //metoda konstruktora za zavaranje prozora
        Platform.exit();
    }

}
