package intergration.util;

import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

/**
 * @author Jigubigu
 * @version 1.0
 * @date 2019/10/10 21:19
 */
public class MapperUtil {
    private String xmlFilePath = null;

    /**
     * 设置需要查询的xml表的文件路径
     * @param xmlFilePath xml表路径
     */
    public void setXmlFilePath(String xmlFilePath) {
        this.xmlFilePath = xmlFilePath;
    }

    public String getColumnName(String property) throws ParserConfigurationException, IOException, SAXException {
        String columnName = null;
        // 创建文档构建器工厂(采用单例模式)
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        // 创建文档构建器
        DocumentBuilder builder = factory.newDocumentBuilder();
        // 由xml文件创建文档对象
        org.w3c.dom.Document document = builder.parse(xmlFilePath);
        // 获得根节点<mapper>
        org.w3c.dom.Element root = document.getDocumentElement();
        // 获得一级子节点列表
        org.w3c.dom.NodeList firstLevelList = root.getChildNodes();
       find: for(int i = 0; i < firstLevelList.getLength(); i++){
            //获取一级子节点<resultMap>
            org.w3c.dom.Node firstLevelNode = firstLevelList.item(i);
            if (firstLevelNode.getNodeType() == org.w3c.dom.Node.ELEMENT_NODE){
                org.w3c.dom.NodeList secondLevelList = firstLevelNode.getChildNodes();
                for(int j = 0; j < secondLevelList.getLength(); j++){
                    //获取二级子节点<属性>
                    org.w3c.dom.Node secondLevelNode = secondLevelList.item(j);
                    //若xml中的标签与传入的属性名相同，则获取标签的值，结束循环
                    if (secondLevelNode.getNodeType() == org.w3c.dom.Node.ELEMENT_NODE) {
                        if (property.equals(secondLevelNode.getNodeName())) {
                            columnName = secondLevelNode.getTextContent();
                            break find;
                        }
                    }
                }
            }
        }
        return columnName;
    }

    public String getColumnList() throws ParserConfigurationException, IOException, SAXException {
        StringBuilder columnList = new StringBuilder();
        columnList.append("(");
        // 创建文档构建器工厂(采用单例模式)
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        // 创建文档构建器
        DocumentBuilder builder = factory.newDocumentBuilder();
        // 由xml文件创建文档对象
        org.w3c.dom.Document document = builder.parse(xmlFilePath);
        // 获得根节点<mapper>
        org.w3c.dom.Element root = document.getDocumentElement();
        // 获得一级子节点列表
        org.w3c.dom.NodeList firstLevelList = root.getChildNodes();
        for(int i = 0; i < firstLevelList.getLength(); i++){
            //获取一级子节点<resultMap>
            org.w3c.dom.Node firstLevelNode = firstLevelList.item(i);
            if (firstLevelNode.getNodeType() == org.w3c.dom.Node.ELEMENT_NODE){
                org.w3c.dom.NodeList secondLevelList = firstLevelNode.getChildNodes();
                for(int j = 0; j < secondLevelList.getLength(); j++){
                    //获取二级子节点<属性>
                    org.w3c.dom.Node secondLevelNode = secondLevelList.item(j);
                    //若xml中的标签与传入的属性名相同，则获取标签的值，结束循环
                    if (secondLevelNode.getNodeType() == org.w3c.dom.Node.ELEMENT_NODE) {
                        columnList.append(secondLevelNode.getTextContent() + ",");
                    }
                }
            }
        }
        columnList.replace(columnList.length()-1,columnList.length(),")");
        return columnList.toString();
    }

    public static void main(String[] args) throws IOException, SAXException, ParserConfigurationException {
        MapperUtil mapperUtil = new MapperUtil();
        mapperUtil.setXmlFilePath("G:\\spring-boot-examples-master\\spring-boot-mybatis\\dataintepration2\\src\\main\\resources\\xml\\database1\\users.xml");
        System.out.println(mapperUtil.getColumnName("userName"));
        System.out.println(mapperUtil.getColumnList());
    }
}
