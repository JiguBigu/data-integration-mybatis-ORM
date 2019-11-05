package intergration.mapper;

import com.mysql.jdbc.StringUtils;
import intergration.entity.User;
import intergration.util.DBUtil;
import intergration.util.HikariCPUtil;
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

    private String dataBase;
    private String xmlPath;
    private String tableName;
    private HikariCPUtil hikariCPUtil = HikariCPUtil.getHikariCPUtil();

    public List<User> selectAllUser() throws IOException, SAXException, ParserConfigurationException {
        List<User> userList = new ArrayList<User>();
        //创建映射工具类
        MapperUtil mapperUtil = new MapperUtil();
        mapperUtil.setXmlFilePath(xmlPath);
        //通过Hikari连接池获取连接
        Connection connection = hikariCPUtil.getConnect(dataBase);
        //配置sql语句
        String sql = "select * from " + tableName;
        try {
            assert connection != null;
            Statement statement = connection.createStatement();
            ResultSet resultSet =  statement.executeQuery(sql);
            User user = null;
            while (resultSet.next()){
                user = new User();
                Field[] fields = user.getClass().getDeclaredFields();
                for(Field field: fields){
                    user.setPropertyValue(
                            field.getName(), resultSet.getString(mapperUtil.getColumnName(field.getName()))
                    );
                }
                userList.add(user);
            }
            connection.close();
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
        //通过Hikari连接池获取连接
        Connection connection = hikariCPUtil.getConnect(dataBase);
        //配置sql语句
        String sql = "select * from "+ tableName + " where " + mapperUtil.getColumnName("id") + "=" + id;
        try {
            assert connection != null;
            Statement statement = connection.createStatement();
            ResultSet resultSet =  statement.executeQuery(sql);
            while (resultSet.next()){
                Field[] fields = user.getClass().getDeclaredFields();
                for(Field field: fields){
                    user.setPropertyValue(
                            field.getName(), resultSet.getString(mapperUtil.getColumnName(field.getName()))
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
        //通过Hikari连接池获取连接
        Connection connection = hikariCPUtil.getConnect(dataBase);
        //配置sql语句
        String sql = "insert into " + tableName + " "+ mapperUtil.getColumnList() + " values ( '" + user.getId() + "','"
                + user.getUserName() + "','" + user.getUserSex() + "','" + user.getClassName() + "')";
        try {
            assert connection != null;
            PreparedStatement statement = connection.prepareStatement(sql);
            isInsert = statement.executeUpdate();
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
        //通过Hikari连接池获取连接
        Connection connection = hikariCPUtil.getConnect(dataBase);

        //配置sql语句
        String sql = "delete from " + tableName + " where " + mapperUtil.getColumnName("id") + "=" + id;

        PreparedStatement statement = null;
        try {
            assert connection != null;
            statement = connection.prepareStatement(sql);
            isDelete = statement.executeUpdate();
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
        //通过Hikari连接池获取连接
        Connection connection = hikariCPUtil.getConnect(dataBase);

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
            assert connection != null;
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

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    @Override
    public String toString() {
        return "UserMapper{" +
                "dataBase='" + dataBase + '\'' +
                ", xmlPath='" + xmlPath + '\'' +
                ", tableName='" + tableName + '\'' +
                '}';
    }
}
