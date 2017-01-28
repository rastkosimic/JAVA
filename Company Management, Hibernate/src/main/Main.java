package main;

import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class Main extends javax.swing.JFrame {

    public static void main(String[] args) {

        EmployeeForm form = new EmployeeForm();
        form.setVisible(true);

    }//kraj metode main

    public static void addEmployee(String name, int age, String address, int salary) { //dodavanje zaposlenog

        Session session = HibernateUtil.createSessionFactory().openSession();
        Transaction tx = null;

        Employee employee = new Employee(name, age, address, salary);

        try {
            tx = session.beginTransaction();

            session.persist(employee);

            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            System.out.println(e);
        } finally {
            HibernateUtil.close();
        }

    }// kraj metode addEmployee

    public static void allEmployees() { //prikaz svih zaposlenih

        Session session = HibernateUtil.createSessionFactory().openSession();
        Transaction tx = null;

        Query query = session.createQuery("from Employee");

        List<Employee> employees = null;

        try {
            tx = session.beginTransaction();

            employees = query.list();

            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            System.out.println(e);
        } finally {
            HibernateUtil.close();
        }

    }// kraj metode allEmployees

    public static void showEmployee(int salary) {    //prikaz zaposlenih sa platom vecom od upisane vrednosti

        Session session = HibernateUtil.createSessionFactory().openSession();
        Transaction tx = null;

        Query query = session.createQuery("from Employee as employee where employee.salary>=('" + salary + "')");

        //Query query = session.createQuery("from Employee as employee where employee.salary=10000");
        List<Employee> employees = null;

        try {
            tx = session.beginTransaction();

            employees = query.list();

            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            System.out.println(e);
        } finally {
            HibernateUtil.close();
        }

        for (Employee emp : employees) {

            if (employees.size() >= 1) {  //ne radi kako treba, pozabaviti se ovim kasnije
                System.out.println(emp);
            } else if (employees.size() < 1) {
                System.out.println("There are no employees with salary higher than " + salary + " rsd.");
            }
        }

    }// kraj metode showEmployee

    public static void updateSalary(int id, int salary) {  //azuriranje informacija o zaposlenim na osnovu id-a

        Session session = HibernateUtil.createSessionFactory().openSession();
        Transaction tx = null;

        Employee employee = (Employee) session.get(Employee.class, id);
        employee.setSalary(salary);

        try {
            tx = session.beginTransaction();
            
            session.update(employee);
            
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            System.out.println(e);
        } finally {
            HibernateUtil.close();
        }

    }//kraj metode updateInfo

    public static void deleteEmployee(int id) {    //brisanje zaposlenog iz baze
        
        Session session = HibernateUtil.createSessionFactory().openSession();
        Transaction tx = null;

        Employee employee = (Employee) session.get(Employee.class, id);

        try {
            tx = session.beginTransaction();
            
            session.delete(employee);
            
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            System.out.println(e);
        } finally {
            HibernateUtil.close();
        }

    }

}//kraj klase Main
