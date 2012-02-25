/* This is code written by Nightw0rk
 * for contact me, please write letter on 
 * nightw0rk@mail.ru
 */
package sqlgenerator;

import java.lang.reflect.Method;

/**
 * @author  nightw0rk
 * @date    25.02.2012
 * @time    13:25:04
 */
public class GFeild {
    
    private Object m_Object;
    private String m_Settler;
    private String m_Gettler;

    public GFeild(Object m_Object, String m_Settler, String m_Gettler) {
        this.m_Object = m_Object;
        this.m_Settler = m_Settler;
        this.m_Gettler = m_Gettler;
    }
    
    public Object getValue(){
        Class s = m_Object.getClass();
            
        try{
            Method m = s.getMethod(m_Gettler, (Class) null);
            return m.invoke(m_Object, (Object) null);
        }catch(Exception e){
            System.err.print(e);
            return null;
        }
    }
    
    public void setValue(Object Value){
        Class s = m_Object.getClass();
            
        try{
            Method m = s.getMethod(m_Settler, Value.getClass());
            m.invoke(m_Object, Value);
        }catch(Exception e){
            System.err.print(e);            
        }
    }
    

}
