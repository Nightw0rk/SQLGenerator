/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sqlgenerator;

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
        SQLGenerator s = new SQLGenerator("TEST");
        s.addFeild("NAME", f, "setText", "getText",String.class);
        s.setFeildValue("NAME", " ti ti ti tsal");
        System.out.println(s.InserSQL());
        
        
    }
}
