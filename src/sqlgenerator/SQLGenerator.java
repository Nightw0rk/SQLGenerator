/* This is code written by Nightw0rk
 * for contact me, please write letter on 
 * nightw0rk@mail.ru
 */
package sqlgenerator;

import java.sql.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author nightw0rk @date 25.02.2012 @time 13:24:42
 */
public class SQLGenerator {

    private HashMap<String, GField> m_Fields;
    private String m_Table;
    private Connection m_Connection;
    private String m_PK;
    private int m_PKValue;

    public SQLGenerator(String Table, Connection Conn) {
        m_Fields = new HashMap<String, GField>();
        m_Table = Table;
        m_Connection = Conn;
    }

    public void setPrimaryKey(String Name) {
        this.m_PK = Name;
    }

    public void setPrimaryKey(String Name, int Value) {
        this.m_PK = Name;
        this.m_PKValue = Value;
    }

    public void setPrimaryKeyValue(int Value) {
        this.m_PKValue = Value;
    }

    public int getPrimaryKeyValue() {
        return m_PKValue;
    }

    public void addField(String FeildName, Object c, String Set, String Get, Class Type) {
        if (!m_Fields.containsKey(FeildName)) {
            m_Fields.put(FeildName, new GField(c, Set, Get, Type));
        } else {
            System.err.format("The field %s in this generator is available, have not added", FeildName);
        }
    }

    public void removeField(String FieldName) {
        if (!m_Fields.containsKey(FieldName)) {
            m_Fields.remove(FieldName);
        } else {
            System.err.format("The field %s in this generator is not available, have not remove", FieldName);
        }
    }

    public Object getFieldValue(String Name) {
        return ((GField) m_Fields.get(Name)).getValue();
    }

    public void setFieldValue(String Name, Object Value) {
        ((GField) m_Fields.get(Name)).setValue(Value);
    }

    /**
     * Возврашает тип поля
     *
     * @param Name имя Поля
     * @return Class тип поля
     */
    public Class getTypeField(String Name) {
        return ((GField) m_Fields.get(Name)).getTypeValue();
    }

    /**
     * Генерирует команду sql для вставки в базу Все значения берутся из
     * Компонентов<br> Generation sql command for insert into database. All
     * values getting from Components
     *
     * @return String SQL Command
     */
    private String InsertSQL() {
        Iterator it = m_Fields.keySet().iterator();
        String SQL = "INSERT INTO " + m_Table;
        String FieldsSQL = "(";
        String ValueSQL = "(";
        while (it.hasNext()) {
            String fName = (String) it.next();
            FieldsSQL += fName + ",";
            if (this.getTypeField(fName) == String.class) {
                ValueSQL += "'" + getFieldValue(fName) + "',";
            } else {
                ValueSQL += getFieldValue(fName) + ",";
            }


        }
        SQL += FieldsSQL.substring(0, FieldsSQL.length() - 1) + ") VALUES " + ValueSQL.substring(0, ValueSQL.length() - 1) + ")";
        return SQL;
    }

    public void insert() {
        try {
            PreparedStatement st = m_Connection.prepareStatement(InsertSQL(), Statement.RETURN_GENERATED_KEYS);
            int rows = st.executeUpdate();
            if (rows == 0) {
                throw new SQLException("not insert");
            } else {
                ResultSet rs = st.getGeneratedKeys();
                
                if(rs.next()){                    
                m_PKValue = rs.getInt(1);
               
                }else{
                    throw new SQLException("failed get PK");
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(SQLGenerator.class.getName()).log(Level.SEVERE, null, ex);            
        }

    }

    /**
     * Генерация запрос для получения данных из базы<br> Generation query for
     * getting data from database
     *
     * @return String SQL query
     */
    private String SelectSQL() {
        Iterator it = m_Fields.keySet().iterator();
        String SQL = "select ";
        String FieldsSQL = "";
        while (it.hasNext()) {
            String fName = (String) it.next();
            FieldsSQL += fName + ",";
        }
        if (m_PK == null) {
            System.err.println("Cannot find Pimary Key, try setPrimaryKey(String,int)");
            return null;
        }
        SQL += FieldsSQL.substring(0, FieldsSQL.length() - 1) + " from " + m_Table + " where " + m_PK + "=" + String.valueOf(getPrimaryKeyValue());
        return SQL;
    }

    /**
     * Заполнить все элемнты из бызы<br> Fill all elements(components) form
     * database
     */
    public void fill() {
        try {
            Statement st = m_Connection.createStatement();
            ResultSet s = st.executeQuery(SelectSQL());
            s.next();
            Iterator it = m_Fields.keySet().iterator();
            while (it.hasNext()) {
                String fName = (String) it.next();
                setFieldValue(fName, s.getObject(fName));
            }
        } catch (SQLException ex) {
            Logger.getLogger(SQLGenerator.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    private String UpdateSQL(){
        Iterator it = m_Fields.keySet().iterator();
        String SQL = "update " +m_Table+" SET ";
        while (it.hasNext()) {            
            String fName = (String)it.next();
            SQL+=fName+"=";
             if (this.getTypeField(fName) == String.class) {
                SQL += "'" + getFieldValue(fName) + "',";
            } else {
                SQL += getFieldValue(fName) + ",";
            }
        }  
         if (m_PK == null) {
            System.err.println("Cannot find Pimary Key, try setPrimaryKey(String,int)");
            return null;
        }        
        SQL=SQL.substring(0, SQL.length()-1)+" where " +m_PK+"="+String.valueOf(m_PKValue);
        return SQL;
    }
    public void update(){
        try{
            Statement st = m_Connection.createStatement();
            st.execute(UpdateSQL());
        }catch(SQLException ex){
            Logger.getLogger(SQLGenerator.class.getName()).log(Level.SEVERE, null, ex); 
        }
    }
}
