package main;

import java.awt.GridLayout;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.swing.Icon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;

public class Main extends javax.swing.JFrame {

    public static void main(String[] args) {

        EmployeeForm form = new EmployeeForm();
        form.setVisible(true);

    }//kraj metode main

    public static void addEmployee(String name, int age, String address, int salary) { //dodavanje zaposlenog

        Employee employee = null;

        try (java.sql.Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/company", "root", "");) {

            employee = new Employee(name, age, address, salary);
            PreparedStatement st = conn.prepareStatement("insert into employee (name, age, address, salary) values (?,?,?,?)");
            st.setString(1, employee.getName());
            st.setString(2, String.valueOf(employee.getAge()));
            st.setString(3, employee.getAddress());
            st.setString(4, String.valueOf(employee.getSalary()));
            st.execute();

            ResultSet rs = st.executeQuery("select last_insert_id() as person_id from employee");
            rs.next();
            employee.setId(Integer.valueOf(rs.getString("person_id")));

            System.out.println(employee);

        } catch (SQLException ex) {
            System.out.println("Error in database connection: \n" + ex.getMessage());
        }
    }// kraj metode addEmployee

    public static void allEmployees() { //prikaz svih zaposlenih
        Employee employee = null;
        List<Employee> employee_collection = new ArrayList();

        try (java.sql.Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/company", "root", "");) {

            Statement st = conn.createStatement();
            st.execute("select * from employee");
            ResultSet rs = st.getResultSet();

            while (rs.next()) {
                employee = new Employee(rs.getInt("person_id"), rs.getString("name"), rs.getInt("age"), rs.getString("address"), rs.getInt("salary"));
                employee_collection.add(employee);
            }
        } catch (SQLException ex) {
            System.out.println("Error in database connection: \n" + ex.getMessage());
        }

        for (Employee emp : employee_collection) {
            System.out.println(emp);
        }

    }// kraj metode allEmployees

    public static void showEmployee(int salary) {    //prikaz zaposlenih sa platom vecom od upisane vrednosti

        Employee employee = null;
        List<Employee> employee_collection = new ArrayList();

        try (java.sql.Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/company", "root", "");) {

            employee = new Employee(salary);
            PreparedStatement st = conn.prepareStatement("select person_id, name, age, address, salary from employee where salary >= ? order by person_id");
            st.setString(1, String.valueOf(employee.getSalary()));
            st.execute();
            ResultSet rs = st.getResultSet();

            while (rs.next()) {

                employee = new Employee(rs.getInt("person_id"), rs.getString("name"), rs.getInt("age"), rs.getString("address"), rs.getInt("salary"));
                employee_collection.add(employee);
            }

        } catch (SQLException ex) {
            System.out.println("Error in database connection: \n" + ex.getMessage());
        }

        for (Employee emp : employee_collection) {

            if (employee_collection.size() >= 1) {  //ne radi kako treba, pozabaviti se ovim kasnije
                System.out.println(emp);
            } else if (employee_collection.size() < 1) {
                System.out.println("There are no employees with salary higher than " + salary + " rsd.");
            }
        }

    }// kraj metode showEmployee

    public static void updateSalary(int id, int salary) {  //azuriranje informacija o zaposlenim na osnovu id-a

        Employee employee = null;

        try (java.sql.Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/company", "root", "");) {

            employee = new Employee(id, salary);
            PreparedStatement st = conn.prepareStatement("update employee set salary = ? where person_id = ?");
            st.setString(1, String.valueOf(employee.getSalary()));
            st.setString(2, String.valueOf(employee.getId()));
            st.execute();

        } catch (SQLException ex) {
            System.out.println("Error in database connection: \n" + ex.getMessage());
        }

    }//kraj metode updateInfo

    public static void deleteEmployee(int id, String name) {    //brisanje zaposlenog iz baze

        Employee employee = null;

        try (java.sql.Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/company", "root", "");) {

            employee = new Employee(id, name);
            PreparedStatement st = conn.prepareStatement("delete from employee where person_id = ?");
            st.setString(1, String.valueOf(employee.getId()));
            st.executeUpdate();

        } catch (SQLException ex) {
            System.out.println("Error in database connection: \n" + ex.getMessage());
        }
    }

}//kraj klase Main
