/* This is code written by Nightw0rk
 * for contact me, please write letter on 
 * nightw0rk@mail.ru
 */
package sqlgenerator;

import java.util.HashMap;

/**
 * @author  nightw0rk
 * @date    25.02.2012
 * @time    13:24:42
 */
public class SQLGenerator {
    private HashMap<String,GFeild> m_Feilds;

    public SQLGenerator() {
        m_Feilds = new HashMap<String, GFeild>();
    }
    
    public void  addFeild(String FeildName,Object c, String Set,String Get){
        if(m_Feilds.containsKey(FeildName)){
            m_Feilds.put(FeildName, new GFeild(c, Set, Get));
        }else{
            System.err.format("The field %s in this generator is available, have not added", FeildName);
        }
    }
    
    public void removeFeild(String FeildName){
        if(!m_Feilds.containsKey(FeildName)){
            m_Feilds.remove(FeildName);
        }else{
            System.err.format("The field %s in this generator is not available, have not remove", FeildName);
        }
    }
    

}
