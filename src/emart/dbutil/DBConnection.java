/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package emart.dbutil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author 91708
 */
public class DBConnection {
    
    private static Connection conn;
    
    static{
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");   //Driver load ,connection open
            conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/stock?user=root&password=tiger");
             
              JOptionPane.showMessageDialog( null, "Connection opened Successfully","Success",JOptionPane.INFORMATION_MESSAGE);
                    }
        catch(ClassNotFoundException ex){
            JOptionPane.showMessageDialog( null, "Error in loading the driver ","Driver Error",JOptionPane.ERROR_MESSAGE);//dialog box
            ex.printStackTrace();
            System.exit(1);
    }
        
         catch(SQLException ex){
            JOptionPane.showMessageDialog( null, "Error in opening Connection ","DB Error",JOptionPane.ERROR_MESSAGE);//dialog box
            ex.printStackTrace();
             System.exit(1);
    }
    }
    
    public  static Connection getConnection(){
        return conn;
    }
      
    public static void closeConnection(){
        try{
            conn.close();
             JOptionPane.showMessageDialog( null, "Connection closed Successfully","Success",JOptionPane.INFORMATION_MESSAGE);

        }
        catch(SQLException ex){
            JOptionPane.showMessageDialog( null, "Error in closing the Connection ","DB Error",JOptionPane.ERROR_MESSAGE);//dialog box
            ex.printStackTrace();
             
    }
        
    }
    
}
