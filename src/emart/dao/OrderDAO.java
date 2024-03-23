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
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

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

  public static ArrayList<ProductsPojo> getOrderDetails(String orderId) throws SQLException, ClassNotFoundException {
      
    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    ArrayList<ProductsPojo> orderDetails = new ArrayList<>();
    
    try {
       
         Class.forName("com.mysql.cj.jdbc.Driver");   //Driver load ,connection open
         conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/stock?user=root&password=tiger");
      //  conn = DBConnection.getConnection();
        String sql = "SELECT p.p_id, p.p_name, p.p_companyname, p.p_price, p.our_price, o.quantity, p.p_tax FROM orders o JOIN products p ON o.p_id = p.p_id WHERE o.order_id=?";
        ps = conn.prepareStatement(sql);
        ps.setString(1, orderId);
        rs = ps.executeQuery();
        
        while (rs.next()) {
            ProductsPojo p = new ProductsPojo();
            p.setProductId(rs.getString("p_id"));
            p.setProductName(rs.getString("p_name"));
            p.setProductCompany(rs.getString("p_companyname"));
            p.setProductPrice(rs.getDouble("p_price"));
            p.setOurPrice(rs.getDouble("our_price"));
            p.setQuantity(rs.getInt("quantity"));
            p.setTax(rs.getInt("p_tax"));
            orderDetails.add(p);
        }
    } finally {
        // Close resources and log connection closure
        if (rs != null) {
            rs.close();
        }
        if (ps != null) {
            ps.close();
        }
        if (conn != null) {
            conn.close();
            
        }
    }
    
    return orderDetails;
}

    
 public static ArrayList<String> getAllOrderIds() throws SQLException, ClassNotFoundException {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        ArrayList<String> orderIds = new ArrayList<>();
        
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");   //Driver load ,connection open
         conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/stock?user=root&password=tiger");
            String sql = "SELECT DISTINCT order_id FROM orders";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            
            while (rs.next()) {
                String orderId = rs.getString("order_id");
                orderIds.add(orderId);
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (ps != null) {
                ps.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
        return orderIds;
    }
 
                
      public static ArrayList<String> getLastOrderId() throws SQLException, ClassNotFoundException {
         Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        ArrayList<String> lastOrderId = new ArrayList<>();
        
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");   //Driver load ,connection open
         conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/stock?user=root&password=tiger");
            String sql = "SELECT order_id FROM Orders ORDER BY order_id DESC LIMIT 1";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            
            while (rs.next()) {
                String orderId = rs.getString("order_id");
                lastOrderId.add(orderId);
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (ps != null) {
                ps.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
       

        return lastOrderId;
    }
    }
   

