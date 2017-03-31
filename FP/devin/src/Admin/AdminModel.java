package Admin;

import Admin_Stock.Menu_Admin_Stock;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Aldo Sugiarto
 */
public class AdminModel {
    
    private Connection conn = null;
    private Statement st = null;
    
    int ID;
    String name;
    String password;
    String position;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }
    
    
    
    public DefaultTableModel refresh(){
        try
        {
            connect();
            ResultSet rs = st.executeQuery("SELECT * FROM `database_account`");
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
    
    public void addUser(){
        try {
            connect();
            String query = "INSERT INTO database_account VALUES ("+getID()+",'"+getName()+"','"+getPassword()+"','"+getPosition()+"')";
            st.executeUpdate(query);
            refresh();
            JOptionPane.showMessageDialog(null, "SUCCESS ADDING TO DATABASE! ");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "ERROR ADDING TO DATABASE! "+ex);
        }
    }
    //helped by enrico
    public void deleteUser(String ID){
        try {
            connect();
            String query = "DELETE FROM database_account where `ID` = "+ID;
            st.executeUpdate(query);
            JOptionPane.showMessageDialog(null, "SUCCESS DELETING FROM DATABASE! ");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "ERROR DELETING FROM DATABASE! "+ex);
        }
        refresh();
    }
    
    public ResultSet search(String text){
        try {
            connect();
            String query = "SELECT * FROM database_history WHERE `Date` LIKE '%"+text+"%' OR `Product ID` LIKE '%" +text+"%' OR `Product Name` LIKE '%"+text+"%' OR Color LIKE '%"+text+"%' OR Size LIKE '%"+text+"%'"+" OR Price LIKE '%"+text+"%'";
            ResultSet rs  = st.executeQuery(query);
            return rs;
        } catch (SQLException ex) {
            System.out.println(ex);
            return null;
        }
    }
    
    public DefaultTableModel refresh2(){
        try
        {
            connect();
            ResultSet rs = st.executeQuery("SELECT * FROM `database_history`");
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
}
