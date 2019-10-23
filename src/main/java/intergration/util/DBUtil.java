package intergration.util;

import com.alibaba.fastjson.JSONObject;
import intergration.Service.UserService;
import intergration.entity.User;
import org.xml.sax.SAXException;

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

    public  List<User> selectAllStudentNoMapperByDataBase1(String sql) {
        List<User> users = new ArrayList<User>();
        connection = DBUtil.getConnect();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet =  statement.executeQuery(sql);
            User user = null;
            while (resultSet.next()){
                user = new User();
                user.setId(resultSet.getString(1));
                user.setUserName(resultSet.getString(2));
                user.setUserSex(resultSet.getString(3));
                user.setClassName(resultSet.getString(4));
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            //logger.info("创建Statement失败");
        }
        return users;
    }


    public static void setDataBase(String dataBase){
        URL = "jdbc:mysql://localhost:3306/"+ dataBase + "?serverTimezone=UTC&useUnicode=true&characterEncoding=utf-8&useSSL=true";
    }

    public static void main(String[] args) throws IOException {
        UserService userService = new UserService();

        Map<String, Object> modelMap = new HashMap<String, Object>();
        List<User> users = null;
        try {
            users = userService.getAllUser();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }
        modelMap.put("user", users);
        if(users == null){
            modelMap.put("success", false);
        }else {
            modelMap.put("success", true);
        }

        //数据转换成json向浏览器发送
        JSONObject data = new JSONObject(modelMap);
        System.out.println(data);
    }
}
