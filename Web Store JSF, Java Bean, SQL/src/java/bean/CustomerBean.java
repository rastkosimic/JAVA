/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

/**
 *
 * @author Rastko
 */
@ManagedBean(name = "CustomerBean", eager = true)
@RequestScoped
public class CustomerBean {

    private int customer_id;
    private String name;
    private String surname;
    private String address;

    public int getId() {
        return customer_id;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getAddress() {
        return address;
    }

    public void setId(int customer_id) {
        this.customer_id = customer_id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    private final String URL = "jdbc:mysql://localhost/web_store";
    private final String root = "";
    private final String password = "";

    public void insertCustomer() throws ClassNotFoundException, SQLException { //ubacivanje novog kupca
        Class.forName("com.mysql.jdbc.Driver");

        try (java.sql.Connection conn = DriverManager.getConnection(URL, root, password);) {
            if (name != null && !(name.isEmpty()) && surname != null && !(surname.isEmpty()) && address != null && !(address.isEmpty())) {
                Statement st = conn.createStatement();
                st.execute("insert into customers (name,surname,address) values ('" + name + "','" + surname + "','" + address + "')");
            }
        } catch (SQLException ex) {
            System.out.println("Error in database connection: \n" + ex.getMessage());
        }
    }

    public void deleteCustomer() throws ClassNotFoundException, SQLException { //brisanje kupca iz baze
        Class.forName("com.mysql.jdbc.Driver");

        try (java.sql.Connection conn = DriverManager.getConnection(URL, root, password);) {
            if (name != null && !(name.isEmpty()) && surname != null && !(surname.isEmpty()) && address != null && !(address.isEmpty())) {
                Statement st = conn.createStatement();
                st.execute("delete from customers where name = '" + name + "' and surname = '" + surname + "' and address = '" + address + "'");
            }
        } catch (SQLException ex) {
            System.out.println("Error in database connection: \n" + ex.getMessage());
        }
    }

    public String allCustomers() throws ClassNotFoundException {
        StringBuilder all_customers = new StringBuilder();
        Class.forName("com.mysql.jdbc.Driver");

        try (java.sql.Connection conn = DriverManager.getConnection(URL, root, password);) {
            Statement st = conn.createStatement();
            st.executeQuery("select customer_id, name, surname, address from customers");
            ResultSet rs = st.getResultSet();

            while (rs.next()) {
                all_customers.append("Id: ");
                all_customers.append(rs.getString("customer_id"));
                all_customers.append("; ");
                all_customers.append("Name: ");
                all_customers.append(rs.getString("name"));
                all_customers.append("; ");
                all_customers.append("Surame: ");
                all_customers.append(rs.getString("surname"));
                all_customers.append("; ");
                all_customers.append("Address: ");
                all_customers.append(rs.getString("address"));
                all_customers.append("\n");
                all_customers.append("\n");
            }
        } catch (SQLException ex) {
            all_customers.append(ex.getMessage());
        }
        return all_customers.toString();
    }

    public void updeteCustomer() throws ClassNotFoundException, SQLException { //azuriranje kupca u bazi
        Class.forName("com.mysql.jdbc.Driver");

        try (java.sql.Connection conn = DriverManager.getConnection(URL, root, password);) {
            if (name != null && !(name.isEmpty()) && surname != null && !(surname.isEmpty()) && address != null && !(address.isEmpty())) {
                Statement st = conn.createStatement();
                st.execute("update customers set name = '" + name + "', surname = '" + surname + "', address = '" + address + "' where customer_id = '" + customer_id + "'");
            }
        } catch (SQLException ex) {
            System.out.println("Error in database connection: \n" + ex.getMessage());
        }
    }

    public CustomerBean() { //constructor
    }

}
