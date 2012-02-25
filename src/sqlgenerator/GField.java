/* This is code written by Nightw0rk
 * for contact me, please write letter on 
 * nightw0rk@mail.ru
 */
package sqlgenerator;

import java.lang.reflect.Method;

/**
 * @author nightw0rk @date 25.02.2012 @time 13:25:04
 */
public class GField {
    public static Class gfString = String.class;
    public static Class gfNumber = double.class;
    public static Class gfInt = int.class;
    public static Class gfBool = boolean.class;

    /**
     * Only SWING components
     */
    private Object m_Object;
    /*
     * settler method must like setValue(VALUE)
     */
    private String m_Settler;
    /*
     * gettler methos must like getValue();
     */
    private String m_Gettler;
    
    private Class m_FeildClass;
    /**
     * 
     * @param m_Object JComponent
     * @param m_Settler String
     * @param m_Gettler  String
     */    

    public GField(Object m_Object, String m_Settler, String m_Gettler,Class typeField) {
        this.m_Object = m_Object;
        this.m_Settler = m_Settler;
        this.m_Gettler = m_Gettler;
        this.m_FeildClass =typeField;
    }

    public Object getValue() {
        Class s = m_Object.getClass().getSuperclass();
        try {
            Method m = s.getMethod(m_Gettler, null);
            return m.invoke(m_Object, null);
        } catch (Exception e) {
            System.err.print(e);
            return null;
        }
    }

    public void setValue(Object Value) {
        Class s = m_Object.getClass().getSuperclass();

        try {
            Method m = s.getMethod(m_Settler, Value.getClass());
            m.invoke(m_Object, Value);
        } catch (Exception e) {
            System.err.print(e);
        }
    }
   public Class getTypeValue(){
       return m_FeildClass;
   }
}
