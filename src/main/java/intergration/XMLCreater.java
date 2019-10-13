package intergration;


import org.jdom.Document;
import org.jdom.Element;
import org.jdom.output.XMLOutputter;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

/**
 * @author Jigubigu
 * @version 1.0
 * @date 2019/9/26 19:19
 */
public class XMLCreater {

    public static void buildXMLDocFromTable(String tableName, String XMLFileName) throws SQLException, IOException {
        Connection connection = JDBC.getConnection();
        String sql = "select * from " + tableName;
        //创建根节点
        Element root = new Element(tableName + "s");
        //将根节点添加到XML文档对象
        Document document = new Document(root);

        //获取数据库数据
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        ResultSetMetaData resultSetMetaData = resultSet.getMetaData();

        //字段总数
        int fileCount = resultSetMetaData.getColumnCount();
        //字段名数组
        String[] fileNames = new String[fileCount];
        for(int i = 0; i < fileCount; i++){
            fileNames[i] = resultSetMetaData.getColumnName(i + 1);
        }

        //遍历结果集
        while (resultSet.next()){
            //创建子节点
            Element element = new Element(tableName);
            //将第一个字段值设置为子节点属性
            element.setAttribute(fileNames[0],resultSet.getString(1));
            //给子节点的子孙赋值
            for(int i = 1; i < fileNames.length; i++){
                element.addContent(
                        new Element(fileNames[i]).setText(resultSet.getString(i + 1))
                );
            }
            root.addContent(element);
        }

        //创建XML输出器
        XMLOutputter outputter = new XMLOutputter();
        //输出到xml文件
        outputter.output(document, new PrintWriter(
                new FileOutputStream(XMLFileName)
        ));
    }



    public static void main(String[] args) throws IOException, SQLException, ParserConfigurationException, SAXException {
        JDBC.setDataBase("test1");
        String tableName = "users";
        String XMLFileName = "G:\\spring-boot-examples-master\\spring-boot-mybatis\\dataintepration2\\src\\main\\java\\xml\\" + tableName + ".xml";
        XMLCreater.buildXMLDocFromTable(tableName, XMLFileName);
        String content = XMLReader.readXMLDoc(XMLFileName);
        System.out.println("?");
    }
}