package intergration.mapper;

import com.mysql.jdbc.StringUtils;
import intergration.entity.User;
import intergration.util.DBUtil;
import intergration.util.MapperUtil;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Jigubigu
 * @version 1.0
 * @date 2019/10/13 15:10
 */
public class UserMapper {

    private String dataBase = null;
    private String xmlPath = null;
    private static final String tableName = "users";
    String a = User.class.getName();

    public List<User> selectAllUser() throws IOException, SAXException, ParserConfigurationException {
        List<User> userList = new ArrayList<User>();
        //创建映射工具类
        MapperUtil mapperUtil = new MapperUtil();
        mapperUtil.setXmlFilePath(xmlPath);
        //jdbc工具类设置数据库并获取连接
        DBUtil.setDataBase(dataBase);
        Connection connection = DBUtil.getConnect();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet =  statement.executeQuery("select * from " + tableName);
            User user = null;
            while (resultSet.next()){
                user = new User();
                Field[] fields = user.getClass().getDeclaredFields();
                for(int i = 0; i < fields.length; i++){
                    user.setPropertyValue(
                             fields[i].getName(), resultSet.getString(mapperUtil.getColumnName(fields[i].getName()))
                     );
                }
                userList.add(user);
//                statement.close();
//                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userList;
    }

    public User selectUserById(String id) throws IOException, SAXException, ParserConfigurationException {
        User user = new User();
        //创建映射工具类
        MapperUtil mapperUtil = new MapperUtil();
        mapperUtil.setXmlFilePath(xmlPath);
        //jdbc工具类设置数据库并获取连接
        DBUtil.setDataBase(dataBase);
        Connection connection = DBUtil.getConnect();

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet =  statement.executeQuery("select * from "+ tableName + " where " + mapperUtil.getColumnName("id") + "=" + id);
            while (resultSet.next()){
                Field[] fields = user.getClass().getDeclaredFields();
                for(int i = 0; i < fields.length; i++){
                    user.setPropertyValue(
                            fields[i].getName(), resultSet.getString(mapperUtil.getColumnName(fields[i].getName()))
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }


    public int insertUser(User user) throws IOException, SAXException, ParserConfigurationException {
        int isInsert = 0;
        //创建映射工具类
        MapperUtil mapperUtil = new MapperUtil();
        mapperUtil.setXmlFilePath(xmlPath);
        //jdbc工具类设置数据库并获取连接
        DBUtil.setDataBase(dataBase);
        Connection connection = DBUtil.getConnect();


        String sql = "insert into " + tableName + " "+ mapperUtil.getColumnList() + " values ( '" + user.getId() + "','"
                + user.getUserName() + "','" + user.getUserSex() + "','" + user.getClassName() + "')";

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            isInsert = statement.executeUpdate();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return isInsert;
    }

    public int deleteUser(String id) throws IOException, SAXException, ParserConfigurationException {
        int isDelete = 0;
        MapperUtil mapperUtil = new MapperUtil();
        mapperUtil.setXmlFilePath(xmlPath);
        DBUtil.setDataBase(dataBase);
        Connection connection = DBUtil.getConnect();

        String sql = "delete from " + tableName + " where " + mapperUtil.getColumnName("id") + "=" + id;

        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(sql);
            isDelete = statement.executeUpdate();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return isDelete;
    }

    public int updateUser(User user) throws IllegalAccessException, IOException, SAXException, ParserConfigurationException {
        //成功执行的sql语句条数
        int isUpdate = 0;
        //创建映射工具类
        MapperUtil mapperUtil = new MapperUtil();
        mapperUtil.setXmlFilePath(xmlPath);
        //jdbc工具类设置数据库并获取连接
        DBUtil.setDataBase(dataBase);
        Connection connection = DBUtil.getConnect();

        //反射构造sql语句
        StringBuilder sql = new StringBuilder("UPDATE " + tableName + " SET ");
        Field[] fields = user.getClass().getDeclaredFields();
        for(Field field: fields){
            field.setAccessible(true);
            sql.append(mapperUtil.getColumnName(field.getName()) + "='" + field.get(user) + "',");
        }
        sql.replace(sql.length()-1, sql.length(), "");
        sql.append(" where " + mapperUtil.getColumnName("id") + "='" + user.getId() + "'");

        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(sql.toString());
            isUpdate = statement.executeUpdate();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return isUpdate;
    }


    public void setDataBase(String dataBase) {
        this.dataBase = dataBase;
    }

    public void setXmlPath(String xmlPath) {
        this.xmlPath = xmlPath;
    }

}
