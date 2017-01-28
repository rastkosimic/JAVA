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
@ManagedBean(name = "ProductBean", eager = true)
@RequestScoped
public class ProductBean {
    
    private int product_id;
    private String product;
    private String price;
    
    public int getId(){
        return product_id;
    }
    
    public String getProduct(){
        return product;
    }
    
    public String getPrice(){
        return price;
    }
    
    public void setId(int product_id){
        this.product_id=product_id;
    }
    
    public void setProduct(String product){
        this.product=product;
    }
    
    public void setPrice(String price){
        this.price=price;
    }
    
    private final String URL = "jdbc:mysql://localhost/web_store";
    private final String root = "root";
    private final String password = "stkOra011";
    
    public void insertProduct() throws ClassNotFoundException, SQLException{ //ubacivanje novog proizvoda
        Class.forName("com.mysql.jdbc.Driver");
        
        try(java.sql.Connection conn = DriverManager.getConnection(URL, root, password);){
            if(product != null && !(product.isEmpty()) && price != null && !(price.isEmpty())){
                Statement st = conn.createStatement();
                st.execute("insert into products (product,price) values ('" + product + "','" + price + "')");
            }
        }catch (SQLException ex){
            System.out.println("Error in database connection: \n" + ex.getMessage());
        }
    }
    
    public void deleteProduct() throws ClassNotFoundException, SQLException{ //brisanje proizvoda iz baze
        Class.forName("com.mysql.jdbc.Driver");
        
        try(java.sql.Connection conn = DriverManager.getConnection(URL, root, password);){
            if(product != null && !(product.isEmpty()) && price != null && !(price.isEmpty())){
                Statement st = conn.createStatement();
                st.execute("delete from products where product = '" + product + "' and price = '" + price + "'");
            }
        }catch (SQLException ex){
            System.out.println("Error in database connection: \n" + ex.getMessage());
        }
    }
    
//    ResultSet rs = null;
    
    public String allProducts() throws ClassNotFoundException{
        StringBuilder all_products = new StringBuilder();
        Class.forName("com.mysql.jdbc.Driver");
        
        try(java.sql.Connection conn = DriverManager.getConnection(URL, root, password);){
            Statement st = conn.createStatement();
            st.executeQuery("select product_id, product, price from products");
            ResultSet rs = st.getResultSet();
//        rs = st.getResultSet();
            
            while (rs.next()){
                all_products.append("Id: ");
                all_products.append(rs.getString("product_id"));
                all_products.append("; ");
                all_products.append("Product: ");
                all_products.append(rs.getString("product"));
                all_products.append("; ");
                all_products.append("Price: ");
                all_products.append(rs.getString("price"));
                all_products.append("\n");
                all_products.append("\n");
            }
        }catch (SQLException ex){
           all_products.append(ex.getMessage());

//            ex.getMessage();
        }
        return all_products.toString();

//        return rs;
    }
    
    public void updateProduct() throws ClassNotFoundException, SQLException{ //azuriranje proizvoda u bazi
        Class.forName("com.mysql.jdbc.Driver");
        
        try(java.sql.Connection conn = DriverManager.getConnection(URL, root, password);){
            if(product != null && !(product.isEmpty()) && price != null && !(price.isEmpty())){
                Statement st = conn.createStatement();
                st.execute("update products set product = '" + product + "', price = '" + price + "' where product_id = '" + product_id + "'");
            }
        }catch (SQLException ex){
            System.out.println("Error in database connection: \n" + ex.getMessage());
        }
    }
    
    public ProductBean() { //constructor
    }
    
}
