package intergration.util;

/**
 * @author Jigubigu
 * @version 1.0
 * @date 2019/10/24 22:01
 */
public class IntegrationSetting {
    /**
     * 数据库名
     */
    public String databaseName;
    /**
     * 表名
     */
    public String tableName;
    /**
     * 表述表的XML文件路径
     */
    public String xmlPath;

    @Override
    public String toString() {
        return "IntegrationSetting{" +
                "databaseName='" + databaseName + '\'' +
                ", tableName='" + tableName + '\'' +
                ", xmlPath='" + xmlPath + '\'' +
                '}';
    }
}
