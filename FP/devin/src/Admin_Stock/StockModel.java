package Admin_Stock;

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

public class StockModel {
    
    private Connection conn = null;
    private Statement st = null;
    
    private String code;
    private String name;
    private String color;
    private int size;
    private int price;
    
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
    
    public void addStock(){
        try {
            connect();
            String query = "INSERT INTO database_shoes (`Product Name`,Color,Size,Price) VALUES ('"+getName()+"','"+getColor()+"',"+getSize()+","+getPrice()+")";
            st.executeUpdate(query);
            refresh();
            JOptionPane.showMessageDialog(null, "SUCCESS ADDING TO DATABASE! ");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "ERROR ADDING TO DATABASE! "+ex);
        }
    }
    
    public void deleteStock(String ID){
        try {
            Menu_Admin_Stock MAS = new Menu_Admin_Stock();
            connect();
            String query = "DELETE FROM database_shoes where `Product ID` = "+ID;
            st.executeUpdate(query);
            JOptionPane.showMessageDialog(null, "SUCCESS DELETING FROM DATABASE! ");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "ERROR DELETING FROM DATABASE! "+ex);
        }
        refresh();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
    
    
}
