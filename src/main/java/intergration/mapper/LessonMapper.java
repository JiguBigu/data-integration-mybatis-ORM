package intergration.mapper;

import intergration.entity.Lesson;
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
 * @date 2019/10/13 20:54
 */
public class LessonMapper {

    /**
     * 数据库名
     */
    private String dataBase;
    /**
     * 表名
     */
    private String tableName;
    /**
     * 对应表的XML文件路径
     */
    private String xmlPath;
    /**
     * Hikari连接池工具类
     */
    private HikariCPUtil hikariCPUtil = HikariCPUtil.getHikariCPUtil();

    public List<Lesson> selectAllLesson() throws IOException, SAXException, ParserConfigurationException {
        List<Lesson> lessonList = new ArrayList<Lesson>();
        //创建映射工具类
        MapperUtil mapperUtil = new MapperUtil();
        mapperUtil.setXmlFilePath(xmlPath);
        //通过Hikari连接池获取连接
        Connection connection = hikariCPUtil.getConnect(dataBase);
        //配置sql语句
        String sql = "select * from " + tableName;
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet =  statement.executeQuery(sql);
            Lesson lesson = null;
            while (resultSet.next()){
                lesson = new Lesson();
                Field[] fields = lesson.getClass().getDeclaredFields();
                for(int i = 0; i < fields.length; i++){
                    lesson.setPropertyValue(
                            fields[i].getName(), resultSet.getString(mapperUtil.getColumnName(fields[i].getName()))
                    );
                }
                lessonList.add(lesson);
            }
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lessonList;
    }

    public Lesson selectLessonById(String id) throws IOException, SAXException, ParserConfigurationException {
        Lesson lesson = new Lesson();
        //创建映射工具类
        MapperUtil mapperUtil = new MapperUtil();
        mapperUtil.setXmlFilePath(xmlPath);
        //通过Hikari连接池获取连接
        Connection connection = hikariCPUtil.getConnect(dataBase);
        //配置sql语句
        String sql = "select * from "+ tableName + " where " + mapperUtil.getColumnName("lessonId") + "=" + id;
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet =  statement.executeQuery(sql);
            while (resultSet.next()){
                Field[] fields = lesson.getClass().getDeclaredFields();
                for(int i = 0; i < fields.length; i++){
                    lesson.setPropertyValue(
                            fields[i].getName(), resultSet.getString(mapperUtil.getColumnName(fields[i].getName()))
                    );
                }
            }
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lesson;
    }


    public int insertLesson(Lesson lesson) throws IOException, SAXException, ParserConfigurationException {
        int isInsert = 0;
        //创建映射工具类
        MapperUtil mapperUtil = new MapperUtil();
        mapperUtil.setXmlFilePath(xmlPath);
        //通过Hikari连接池获取连接
        Connection connection = hikariCPUtil.getConnect(dataBase);
        //配置sql语句
        String sql = "insert into " + tableName + " "+ mapperUtil.getColumnList() + " values ( '" + lesson.getLessonId() + "','"
                + lesson.getLessonName() + "','" + lesson.getTeacherName() + "','" + lesson.getHours() + "')";

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            isInsert = statement.executeUpdate();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return isInsert;
    }

    public int deleteLesson(String id) throws IOException, SAXException, ParserConfigurationException {
        int isDelete = 0;
        MapperUtil mapperUtil = new MapperUtil();
        mapperUtil.setXmlFilePath(xmlPath);
        //通过Hikari连接池获取连接
        Connection connection = hikariCPUtil.getConnect(dataBase);
        //配置sql语句
        String sql = "delete from " + tableName + " where " + mapperUtil.getColumnName("lessonId") + "=" + id;

        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(sql);
            isDelete = statement.executeUpdate();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return isDelete;
    }

    public int updateLesson(Lesson lesson) throws IllegalAccessException, IOException, SAXException, ParserConfigurationException {
        //成功执行的sql语句条数
        int isUpdate = 0;
        //创建映射工具类
        MapperUtil mapperUtil = new MapperUtil();
        mapperUtil.setXmlFilePath(xmlPath);
        //通过Hikari连接池获取连接
        Connection connection = hikariCPUtil.getConnect(dataBase);

        //反射构造sql语句
        StringBuilder sql = new StringBuilder("UPDATE " + tableName + " SET ");
        Field[] fields = lesson.getClass().getDeclaredFields();
        for(Field field: fields){
            field.setAccessible(true);
            sql.append(mapperUtil.getColumnName(field.getName()) + "='" + field.get(lesson) + "',");
        }
        sql.replace(sql.length()-1, sql.length(), "");
        sql.append(" where " + mapperUtil.getColumnName("lessonId") + "='" + lesson.getLessonId() + "'");

        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(sql.toString());
            isUpdate = statement.executeUpdate();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return isUpdate;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public void setDataBase(String dataBase) {
        this.dataBase = dataBase;
    }

    public void setXmlPath(String xmlPath) {
        this.xmlPath = xmlPath;
    }

}
