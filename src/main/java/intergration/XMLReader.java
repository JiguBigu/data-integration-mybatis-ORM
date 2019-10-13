package intergration;

import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

/**
 * @author Jigubigu
 * @version 1.0
 * @date 2019/9/26 20:25
 */
public class XMLReader {


    public static String readXMLDoc(String xml_file_name) throws ParserConfigurationException, SAXException {
        StringBuilder mStringBuilder = new StringBuilder();
        try {
            // 创建文档构建器工厂(采用单例模式)
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            // 创建文档构建器
            DocumentBuilder builder = factory.newDocumentBuilder();
            // 由xml文件创建文档对象
            org.w3c.dom.Document document = builder.parse(xml_file_name);
            // 获得根节点
            org.w3c.dom.Element root = document.getDocumentElement();
            // 根节点名称
            String root_name = root.getNodeName();
            mStringBuilder.append("<" + root_name + ">\n");
            // 获得一级子节点列表
            org.w3c.dom.NodeList first_level_list = root.getChildNodes();
            for (int i = 0; i < first_level_list.getLength(); i++) {
                org.w3c.dom.Node first_level_node = first_level_list.item(i);
                if (first_level_node.getNodeType() == org.w3c.dom.Node.ELEMENT_NODE) {
                    // 判断一级子节点是否有属性
                    if (first_level_node.hasAttributes()) {
                        mStringBuilder.append("  <" + first_level_node.getNodeName() + " "
                                + first_level_node.getAttributes().item(0) + ">\n");
                    } else {
                        mStringBuilder.append("  <" + first_level_node.getNodeName() + ">\n");
                    }
                    // 获得二级子节点列表
                    org.w3c.dom.NodeList second_level_list = first_level_node.getChildNodes();
                    for (int j = 0; j < second_level_list.getLength(); j++) {
                        org.w3c.dom.Node second_level_node = second_level_list.item(j);
                        if (second_level_node.getNodeType() == org.w3c.dom.Node.ELEMENT_NODE) {
                            // 判断二级子节点是否有属性
                            if (second_level_node.hasAttributes()) {
                                mStringBuilder.append("    <" + second_level_node.getNodeName() + " "
                                        + second_level_node.getAttributes().item(0) + ">"
                                        + second_level_node.getTextContent() + "</" + second_level_node.getNodeName()
                                        + ">\n");
                            } else {
                                mStringBuilder.append("    <" + second_level_node.getNodeName() + ">"
                                        + second_level_node.getTextContent() + "</" + second_level_node.getNodeName()
                                        + ">\n");
                            }
                        }
                    }
                    mStringBuilder.append("  </" + first_level_node.getNodeName() + ">\n");
                }
            }
            mStringBuilder.append("</" + root_name + ">");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return mStringBuilder.toString();
    }
}
