package Main;

import Admin.Menu_Admin;
import Admin_Stock.Menu_Admin_Stock;
import Cashier.Menu_Cashier_Transaction;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;

/**
 *
 * @author Aldo Sugiarto
 */
public class SignInModel {
    
    private Connection conn = null;
    private Statement st = null;
    
    private String username;
    private String password;
    private String position;
    
    public void connect(){
        try {
            conn = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/shoestore_database", "root", "");
            st = (Statement) conn.createStatement();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "ERROR! "+ex);
        }
    }
    
    public ResultSet login(){
        try {
            connect();
            String username = getUsername();
            String password = getPassword();
            String position = getPosition();
            String query = "SELECT * FROM database_account WHERE Username='"+username+"' AND Password='"+password+"' AND Position='"+position+"'";
            ResultSet rs = st.executeQuery(query);
            return rs;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "ERROR! "+ex);
            return null;
        }
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    public void setPosition(String position){
        this.position = position;
    }
    
    public String getPosition(){
        return position;
    } 
}
