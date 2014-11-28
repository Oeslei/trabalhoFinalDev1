/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author oeslei.250995
 */
abstract public class DAO {
    
    protected Connection connection;
    
    public DAO() {
        try {
            Class.forName("com.mysql.jdbc.Driver");

            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/simpletasks", "root", "");
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e);
        }
    }
    
}
