/* This is code written by Nightw0rk
 * for contact me, please write letter on 
 * nightw0rk@mail.ru
 */
package sqlgenerator;

import java.util.HashMap;
import java.util.Iterator;

/**
 * @author nightw0rk @date 25.02.2012 @time 13:24:42
 */
public class SQLGenerator {

    private HashMap<String, GFeild> m_Feilds;
    private String m_Table;

    public SQLGenerator(String Table) {
        m_Feilds = new HashMap<String, GFeild>();
        m_Table = Table;
    }

    public void addFeild(String FeildName, Object c, String Set, String Get,Class Type) {
        if (!m_Feilds.containsKey(FeildName)) {
            m_Feilds.put(FeildName, new GFeild(c, Set, Get,Type));
        } else {
            System.err.format("The field %s in this generator is available, have not added", FeildName);
        }
    }

    public void removeFeild(String FeildName) {
        if (!m_Feilds.containsKey(FeildName)) {
            m_Feilds.remove(FeildName);
        } else {
            System.err.format("The field %s in this generator is not available, have not remove", FeildName);
        }
    }

    public Object getFeildValue(String Name) {
        return ((GFeild) m_Feilds.get(Name)).getValue();
    }

    public void setFeildValue(String Name, Object Value) {
        ((GFeild) m_Feilds.get(Name)).setValue(Value);
    }
    public Class getTypeFeild(String Name){
        return ((GFeild) m_Feilds.get(Name)).getTypeValue();
    }

    public String InserSQL() {
        Iterator it = m_Feilds.keySet().iterator();
        String SQL = "INSERT INTO " + m_Table;
        String FieldsSQL = "(";
        String ValueSQL = "(";
        while (it.hasNext()) {
            String fName = (String) it.next();
            FieldsSQL += fName + ",";
            if(this.getTypeFeild(fName)==String.class){
                ValueSQL += "'"+getFeildValue(fName) + "',";
            }else{
                ValueSQL += getFeildValue(fName) + ",";
            }
            

        }
        SQL += FieldsSQL.substring(0, FieldsSQL.length()-1) + ") VALUES " + ValueSQL.substring(0, ValueSQL.length()-1) + ")";
        return SQL;
    }
}
