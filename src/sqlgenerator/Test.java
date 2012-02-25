/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sqlgenerator;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTextField;

/**
 *
 * @author nightw0rk
 */
public class Test {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        JTextField f = new JTextField("bla-bla");        
        Connection c =null;
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            c= DriverManager.getConnection("jdbc:mysql://localhost:3306/test","root","bn0258zx");
        } catch (Exception ex) {
            Logger.getLogger(Test.class.getName()).log(Level.SEVERE, null, ex);
        }
        SQLGenerator s = new SQLGenerator("testtable",c);
        s.addField("NAME", f, "setText", "getText",String.class);
        s.setPrimaryKey("uid",6);        
        s.fill();                          
        
    }
}
