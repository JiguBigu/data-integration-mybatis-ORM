package intergration.util;

import com.alibaba.fastjson.JSONObject;
import intergration.Service.impl.UserServiceImpl;
import intergration.entity.User;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Jigubigu
 * @version 1.0
 * @date 2019/10/10 18:28
 */
public class DBUtil {

    /**
     * 日志工具
     */
    //private final static Logger logger = LoggerFactory.getLogger(DBUtil.class);
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

    private static DBUtil dbUtil = null;

    public static DBUtil getDbUtil(){
        if(dbUtil == null){
            dbUtil = new DBUtil();
        }
        return dbUtil;
    }

    private DBUtil(){}

    public static Connection getConnect() {
        try {
            Class.forName(DRIVER);
            //logger.info("注册驱动成功");
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("注册驱动失败");
            //logger.info("注册驱动失败");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("获取连接失败");
            //logger.info("获取连接失败");
        }
        return connection;
    }

    public static void setDataBase(String dataBase){
        URL = "jdbc:mysql://localhost:3306/"+ dataBase + "?serverTimezone=UTC&useUnicode=true&characterEncoding=utf-8&useSSL=true";
    }


}
