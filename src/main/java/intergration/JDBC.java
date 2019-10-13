package intergration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author Jigubigu
 * @version 1.0
 * @date 2019/9/26 19:14
 */
public abstract class JDBC {
    /**
     * 数据库用户名
     */
    final static String USERNAME = "root";
    /**
     * 数据库密码
     */
    final static String PASSWORD = "";
    /**
     * JDBC驱动
     */
    final static String DRIVER = "com.mysql.jdbc.Driver";
    /**
     * 连接的url
     */
    static String URL = "jdbc:mysql://localhost:3306/test1?useUnicode=true&characterEncoding=utf8&serverTimezone=UTC";
    /**
     * jdbc连接
     */
    static Connection connection;


    /**
     * 私有化构造方法，保证本类不能被实例化
     */
    private JDBC(){
    }

    /**
     * 获取jdbc连接
     * @return jdbc连接
     */
    public static Connection getConnection(){
        Connection conn=null;
        try {
            //加载MySQL JDBC驱动程序
            Class.forName(DRIVER);
            //获得数据库连接
            conn=DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //返回获得的数据库连接
        return conn;
    }


    /**
     * 关闭数据库连接的静态方法
     * @param conn
     */
    public static void closeConnection(Connection conn){
        //连接是否有效
        if(conn!=null){
            try {
                //连接是否已经关闭
                if(!conn.isClosed()){
                    //关闭数据库连接
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    static void setDataBase(String dataBase){
        JDBC.URL = "jdbc:mysql://localhost:3306/" + dataBase + "?useUnicode=true&characterEncoding=utf8&serverTimezone=UTC";
    }

}
