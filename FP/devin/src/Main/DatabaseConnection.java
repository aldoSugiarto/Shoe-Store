/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Aldo Sugiarto
 *         Devin Christian
 *         Andriana Pratama Putra
 *         Dedi Alamsah
 */
public abstract class DatabaseConnection {
    
    public Connection conn = null;
    public Statement st = null;
    
    public void connect(){
        try {
            conn = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/shoestore_database", "root", "");
            st = (Statement) conn.createStatement();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "ERROR! "+ex);
        }}
    public DefaultTableModel refresh(){return null;}
    public DefaultTableModel fillTable(ResultSet rs){return null;}
    
}
