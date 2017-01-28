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
@ManagedBean(name = "PurchaseBean", eager = true)
@RequestScoped
public class PurchaseBean {
    
    private String product_id;
    private String customer_id;
    private String purchase_id;
    
    public String getProductId(){
        return product_id;
    }
    
    public String getCustomerId(){
        return customer_id;
    }
    
    public String getPurchaseId(){
        return purchase_id;
    }
    
    public void setProductId(String product_id){
        this.product_id=product_id;
    }
    
    public void setCustomerId(String customer_id){
        this.customer_id=customer_id;
    }
    
    public void setPurchaseId(String purchase_id){
        this.purchase_id=purchase_id;
    }
    
    private final String URL = "jdbc:mysql://localhost/web_store";
    private final String root = "root";
    private final String password = "stkOra011";
    
    public void purchase() throws ClassNotFoundException, SQLException{ //ubacivanje kupovine u bazu
        Class.forName("com.mysql.jdbc.Driver");
        
        try(java.sql.Connection conn = DriverManager.getConnection(URL, root, password);){
            if(product_id != null && !(product_id.isEmpty()) && customer_id != null && !(customer_id.isEmpty())){
                Statement st = conn.createStatement();
                st.execute("insert into buys (product_id,customer_id) values ('" + Integer.valueOf(product_id) + "','" + Integer.valueOf(customer_id) + "')");
            }
        }catch (SQLException ex){
            System.out.println("Error in database connection: \n" + ex.getMessage());
        }
    }
    
    public void deletePrchase() throws ClassNotFoundException, SQLException{ //brisanje kupovine iz baze
        Class.forName("com.mysql.jdbc.Driver");
        
        try(java.sql.Connection conn = DriverManager.getConnection(URL, root, password);){
            if(purchase_id != null && !(purchase_id.isEmpty())){
                Statement st = conn.createStatement();
                st.execute("delete from buys where buy_id = '" + purchase_id + "'");
            }
        }catch (SQLException ex){
            System.out.println("Error in database connection: \n" + ex.getMessage());
        }
    }
    
    public String allPurchase() throws ClassNotFoundException{  //sve prodaje
        StringBuilder all_customers = new StringBuilder();
        Class.forName("com.mysql.jdbc.Driver");
        
        try(java.sql.Connection conn = DriverManager.getConnection(URL, root, password);){
            Statement st = conn.createStatement();
            st.executeQuery("select buys.buy_id, customers.name,  customers.surname, products.product \n" +
                            "from buys\n" +
                            "join customers on customers.customer_id=buys.customer_id\n" +
                            "join products on products.product_id=buys.product_id;");
            ResultSet rs = st.getResultSet();
            
            while (rs.next()){
                all_customers.append("Purchase ID: ");
                all_customers.append(rs.getString("buy_id"));
                all_customers.append("; ");
                all_customers.append(rs.getString("name"));
                all_customers.append(" ");
                all_customers.append(rs.getString("surname"));
                all_customers.append(" ");
                all_customers.append("purchased ");
                all_customers.append(rs.getString("product"));
                all_customers.append("\n");
                all_customers.append("\n");
            }
        }catch (SQLException ex){
           all_customers.append(ex.getMessage());
        }
        return all_customers.toString();
    }
    
}
