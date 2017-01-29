package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class InternetPackage {

    private final StringProperty customerId = new SimpleStringProperty(this, "customerId");
    private final StringProperty name = new SimpleStringProperty(this, "name", "");
    private final StringProperty surname = new SimpleStringProperty(this, "surname", "");
    private final StringProperty address = new SimpleStringProperty(this, "address", "");
    private final StringProperty speed = new SimpleStringProperty(this, "speed");
    private final StringProperty capacity = new SimpleStringProperty(this, "capacity");
    private final StringProperty contractLength = new SimpleStringProperty(this, "contractLength");

    public InternetPackage() {    //podrazumenvani konstruktor
    }

    public InternetPackage(String customerId, String name, String surname, String address, String speed, String capacity, String contractLength) { //konstruktor
        this.customerId.set(customerId);
        this.address.set(address);
        this.name.set(name);
        this.surname.set(surname);
        this.speed.set(speed);
        this.capacity.set(capacity);
        this.contractLength.set(contractLength);
    }

    public InternetPackage(String customerId) {
        this.customerId.set(customerId);
    }

    public String getAddress() {
        return address.get();
    }

    public void setAddress(String address) {
        this.address.set(address);
    }

    public StringProperty addressProperty() {
        return address;
    }

    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public StringProperty nameProperty() {
        return name;
    }

    public String getSurame() {
        return surname.get();
    }

    public void setSurame(String surname) {
        this.surname.set(surname);
    }

    public StringProperty surnameProperty() {
        return surname;
    }

    public String getSpeed() {
        return speed.get();
    }

    public void setSpeed(String speed) {
        this.speed.set(speed);
    }

    public StringProperty speedProperty() {
        return speed;
    }

    public String getCapacity() {
        return capacity.get();
    }

    public void setCapacity(String capacity) {
        this.capacity.set(capacity);
    }

    public StringProperty capacityProperty() {
        return capacity;
    }

    public String getContractLength() {
        return contractLength.get();
    }

    public void setContractLength(String contractLength) {
        this.contractLength.set(contractLength);
    }

    public StringProperty contractLengthProperty() {
        return contractLength;
    }

    public String getCustomerId() {
        return customerId.get();
    }

    public void setCustomerId(String customerId) {
        this.customerId.set(customerId);
    }

    public StringProperty customerIdProperty() {
        return customerId;
    }

    private final ObjectProperty<ArrayList<String>> errorList = new SimpleObjectProperty<>(this, "errorList", new ArrayList<>());

    public ObjectProperty<ArrayList<String>> errorsProperty() {
        return errorList;
    }

    public boolean isIdValid() {    //proverava da li je unesen id prilikom brisanja

        boolean isValid = true;

        if (customerId.get() != null && customerId.get().equals("")) {
            errorList.getValue().add("ID can't be empty!");
            isValid = false;
        }
        return isValid;
    }
    
    public boolean isUserValid() {    //proverava da li su svi korisnikovi podaci uneseni prilikom kreiranja prodaje
        
        boolean isValid = true;
 
        if (name.get() != null && name.get().equals("")) {
            errorList.getValue().add(" First name can't be empty!");
            isValid = false;
        }
        if (surname.get().equals("")) {
            errorList.getValue().add(" \n Last name can't be empty!");
            isValid = false;
        }
        if (address.get().equals("")) {
            errorList.getValue().add(" \n Address can't be empty!");
            isValid = false;
        }
 
        return isValid;
    }

    public void save() throws ClassNotFoundException, SQLException { //cuvanje podataka o kupovini
        
        DBConnection dc = new DBConnection();
        try (Connection conn = dc.Connect()) {
              if (isUserValid()) {
                Statement st = conn.createStatement();
                st.execute("insert into customer (name,surname,address,speed,capacity,contract_length) values ('" + name.get() + "','" + surname.get() + "','" + address.get() + "','" + speed.get() + "','" + capacity.get() + "','" + contractLength.get() + "')");
            }
        } catch (SQLException ex) {
            System.out.println("Error in database connection: \n" + ex.getMessage());
        }
    }

    public void deleteCustomer() throws ClassNotFoundException { //brisanje korisnika iz baze

        DBConnection dc = new DBConnection();
        try (Connection conn = dc.Connect()) {
            if (isIdValid()) {
                Statement st = conn.createStatement();
                st.execute("delete from customer where customer_id = '" + customerId.get() + "'");
            }
        } catch (SQLException ex) {
            System.out.println("Error in database connection: \n" + ex.getMessage());
        }

    }

    @Override
    public String toString() {
        return "Address: " + address.get();
    }
    
}//end InternetPackage class
