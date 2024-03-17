/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package emart.dao;

import emart.dbutil.DBConnection;
import emart.pojo.ProductsPojo;
import emart.pojo.UserProfile;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author 91708
 */
public class OrderDAO {
   public static String getNextOrderId() throws SQLException {
    Connection conn = DBConnection.getConnection();
    Statement st = conn.createStatement();
    ResultSet rs = st.executeQuery("SELECT MAX(order_id) FROM orders");
    rs.next();
    
    String ordId = rs.getString(1);
    
    if (ordId == null || ordId.isEmpty()) {
        return "O-101";
    }
    
    try {
        int ordno = Integer.parseInt(ordId.substring(2));
        ordno++;
        return "O-" + ordno;
    } catch (NumberFormatException ex) {
        // If parsing fails, return default ID
        return "O-101";
    }
}

    
    
   

    public static boolean addOrder(ArrayList<ProductsPojo> al, String ordId) throws SQLException {
    Connection conn = DBConnection.getConnection();
PreparedStatement ps = conn.prepareStatement("INSERT INTO orders (order_id, p_id, quantity, userid) VALUES (?, ?, ?, ?)");
    int count = 0;
    
    for (ProductsPojo p : al) {
        ps.setString(1, ordId);            // Set the order ID
        ps.setString(2, p.getProductId()); // Set the product ID
        ps.setInt(3, p.getQuantity());     // Set the quantity
        ps.setString(4, UserProfile.getUserid()); // Set the user ID
        
        // Execute the insert query for each product
        count += ps.executeUpdate(); 
    }
    
    // Check if all products were successfully inserted
    return count == al.size();
}

    public static ProductsPojo getOrderDetails(String id)throws SQLException
                
        {
           Connection conn=DBConnection.getConnection ();
           PreparedStatement ps=conn.prepareStatement("Select * from orders where order_id=?  ");
           ps.setString(1, id);
           ResultSet rs=ps.executeQuery();
           ProductsPojo p=new ProductsPojo();
           
           if( rs.next())
           {
               p.setOrderId(rs.getString(1));
             p.setProductId(rs.getString(2));
             p.setQuantity(rs.getInt(3)); 
           UserProfile.getUserid();
            
               
           }
                   return p;
           
        }

   
}
