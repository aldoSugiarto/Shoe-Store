package Cashier;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Aldo Sugiarto
 */
public class CashierModel {
    
    private Connection conn = null;
    private Statement st = null;
    private Menu_Cashier_Transaction MCT;
    java.util.Date myDate = new java.util.Date();
    private java.sql.Date sqlDate = new java.sql.Date(myDate.getTime());
    
    private String text;
    
    public void setText(String text){
        this.text = text;
    }
    
    public String getText(){
        return text;
    }
    
    public DefaultTableModel refresh(){
        try
        {
            connect();
            ResultSet rs = st.executeQuery("SELECT * FROM `database_shoes`");
            DefaultTableModel dtm = new DefaultTableModel();
            ResultSetMetaData rmd = rs.getMetaData();
            for(int i=0;i<rmd.getColumnCount();i++)
            dtm.addColumn(rmd.getColumnLabel(i + 1));
            while(rs.next())
            {
                Object[] objects = new Object[rmd.getColumnCount()];
                for(int i=0;i<rmd.getColumnCount();i++)
                objects[i] = rs.getObject(i+1);
                dtm.addRow(objects);
            }
            return dtm;
        }
        catch (SQLException ex)
        {
            JOptionPane.showMessageDialog(null, "ERROR! "+ ex);
            return null;
        }
    }
    
    public DefaultTableModel fillTable(ResultSet rs){
        try{
            DefaultTableModel dtm = new DefaultTableModel();
            ResultSetMetaData rmd = rs.getMetaData();
            for(int i=0;i<rmd.getColumnCount();i++)
            dtm.addColumn(rmd.getColumnLabel(i + 1));
            while(rs.next())
            {
                Object[] objects = new Object[rmd.getColumnCount()];
                for(int i=0;i<rmd.getColumnCount();i++)
                objects[i] = rs.getObject(i+1);
                dtm.addRow(objects);
            }
            return dtm;
        }
        catch(Exception ex){
            System.out.println(ex);
            return null;
        }
    }
    
    public void connect(){
        try {
            conn = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/shoestore_database", "root", "");
            st = (Statement) conn.createStatement();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "ERROR! "+ex);
        }
    }
    
    public ResultSet search(String text){
        try {
            connect();
            String query = "SELECT * FROM database_shoes WHERE `Product ID` LIKE '%" +text+"%' OR `Product Name` LIKE '%"+text+"%' OR Color LIKE '%"+text+"%' OR Size LIKE '%"+text+"%'"+" OR Price LIKE '%"+text+"%'";
            ResultSet rs  = st.executeQuery(query);
            return rs;
        } catch (SQLException ex) {
            System.out.println(ex);
            return null;
        }
    }
    
    public void checkOut(int ID, String name, String color, int size, int price){
        try{
            connect();
            String update = "INSERT INTO database_history VALUES ('"+sqlDate+"',"+ID+",'"+name+"','"+color+"',"+size+","+price+")";
            st.executeUpdate(update);
            String query = "DELETE FROM database_shoes WHERE `Product ID` = "+ID;
            st.executeUpdate(query);
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }
}
