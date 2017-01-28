package main;

import java.io.Serializable;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;
import javax.swing.table.DefaultTableModel;

public class Employee implements Serializable {

//    private static int id;
//    private static String name;
//    private static int age;
//    private static String address;
//    private static int salary;
    
    private int id;
    private String name;
    private int age;
    private String address;
    private int salary;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String getAddress() {
        return address;
    }

    public int getSalary() {
        return salary;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public Employee() { //prvi konstruktor
    }

    public Employee(String name, int age, String address, int salary) { //drugi konstruktor

        this.name = name;
        this.age = age;
        this.address = address;
        this.salary = salary;
    }

    public Employee(int salary) { //treci konstruktor

        this.salary = salary;
    }

    public Employee(int id, String name, int age, String address, int salary) { //cetvrti konstruktor

        this.id = id;
        this.name = name;
        this.age = age;
        this.address = address;
        this.salary = salary;
    }

    public Employee(int id, int salary) { //peti konstruktor

        this.id = id;
        this.salary = salary;
    }

    public Employee(int id, String name) { //cetvrti konstruktor

        this.id = id;
        this.name = name;
    }

    @Override
    public String toString() {
        return "ID: " + id + "  " +"Name: " +name + ";" +"      "+"Age: "+ age + ";"+"  " +"Address: "+ address +";"+"      "+ "Salary: " + salary;
    }
    
    public static DefaultTableModel dtm = null; //varijabla koja sadrzi karakteristike tabele i koja biva prosledjena tabeli
    
    public static void dbContent(String sqlQuery){ //metoda za tabelarni prikaz celokupne baze podataka
        
        try (java.sql.Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/company", "root", "");) {

            Statement st = conn.createStatement();
            st.execute(sqlQuery);
            ResultSet rs = st.getResultSet();
            ResultSetMetaData rsmetadata = rs.getMetaData();
            int columns = rsmetadata.getColumnCount();
            
            dtm = new DefaultTableModel();
            Vector data_rows = new Vector();

            String[] column_name = {"id", "name", "age", "address", "salary"};
            dtm.setColumnIdentifiers(column_name);

            while (rs.next()) {
                
                data_rows = new Vector();
                for(int j=1; j<=columns; j++){
                    data_rows.addElement(rs.getString(j));
                }
                dtm.addRow(data_rows);
            }
            //EmployeeForm.myTable.setModel(dtm);
        } catch (SQLException ex) {
            System.out.println("Error in database connection: \n" + ex.getMessage());
        }
    }//kraj dbContent metode
}
