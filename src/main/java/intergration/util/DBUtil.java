package intergration.util;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;

/**
 * @author Jigubigu
 * @version 1.0
 * @date 2019/10/10 18:28
 */
public abstract class DBUtil {

    /**
     * 日志工具
     */
    private final static Logger logger = LoggerFactory.getLogger(DBUtil.class);
    /**
     * 数据库用户名
     */
    private final static String USERNAME = "root";
    /**
     * 数据库密码
     */
    private final static String PASSWORD = "";
    /**
     * JDBC驱动
     */
    private final static String DRIVER = "com.mysql.jdbc.Driver";
    /**
     * 连接的url
     */
    private static String URL = "jdbc:mysql://localhost:3306/test1?serverTimezone=UTC&useUnicode=true&characterEncoding=utf-8&useSSL=true";
    /**
     * jdbc连接
     */
    private static Connection connection = null;


    private DBUtil(){}

    public static Connection getConnect() {
        try {
            Class.forName(DRIVER);
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            logger.info("注册数据库驱动失败");
        } catch (SQLException e) {
            e.printStackTrace();
            logger.info("获取数据库连接失败");
        }
        return connection;
    }

    public static void setDataBase(String dataBase){
        URL = "jdbc:mysql://localhost:3306/"+ dataBase + "?serverTimezone=UTC&useUnicode=true&characterEncoding=utf-8&useSSL=true";
    }


}
