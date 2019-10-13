package intergration.Service;

import com.alibaba.fastjson.JSON;
import intergration.entity.User;
import intergration.mapper.UserMapper;
import intergration.util.DBUtil;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Jigubigu
 * @version 1.0
 * @date 2019/10/10 21:12
 */
public class UserService {
    private UserMapper userMapper;
    /**
     * 数据库名
     */
    private String[] dataBases = {"test1", "test2"};
    /**
     * 对应数据库的xml文件路径
     */
    private String[] xmlPath = {
            "G:\\spring-boot-examples-master\\spring-boot-mybatis\\dataintepration2\\src\\main\\resources\\xml\\database1\\users.xml",
            "G:\\spring-boot-examples-master\\spring-boot-mybatis\\dataintepration2\\src\\main\\resources\\xml\\database2\\users.xml"
    };

    public UserService() {
        this.userMapper = new UserMapper();
    }

    /**
     * 获取所有数据库中的用户数据
     * @return
     * @throws ParserConfigurationException
     * @throws SAXException
     * @throws IOException
     */
    public List<User> getAllUser() throws ParserConfigurationException, SAXException, IOException {
        List<User> userList = new ArrayList<User>();
        for(int i = 0; i < dataBases.length; i++){
            userMapper.setDataBase(dataBases[i]);
            userMapper.setXmlPath(xmlPath[i]);
            userList.addAll(userMapper.selectAllUser());
        }
        return userList;
    }

    /**
     * 通过用户学号查询用户信息
     * @param id 学号
     * @return
     * @throws ParserConfigurationException
     * @throws SAXException
     * @throws IOException
     */
    public User getUserById(String id) throws ParserConfigurationException, SAXException, IOException {
        User user = null;
        for(int i = 0; i < dataBases.length; i++){
            userMapper.setDataBase(dataBases[i]);
            userMapper.setXmlPath(xmlPath[i]);
            user = userMapper.selectUserById(id);
        }
        return user;
    }

    /**
     * 向数据库中插入学生信息
     * @param user 学生实体
     * @param databaseName 数据库名
     * @return
     * @throws ParserConfigurationException
     * @throws SAXException
     * @throws IOException
     */
    public boolean insertUser(User user, String databaseName) throws ParserConfigurationException, SAXException, IOException {
        if (databaseName == null || databaseName.length() <= 0){
            databaseName = dataBases[0];
        }
        for(int i = 0; i < xmlPath.length; i++){
            if(databaseName.equals(dataBases[i])){
                userMapper.setXmlPath(xmlPath[i]);
                userMapper.setDataBase(databaseName);
                if(userMapper.insertUser(user) > 0){
                    return  true;
                }else{
                    throw new RuntimeException("插入数据失败");
                }
            }
        }
        return false;
    }

    /**
     * 更新学生信息
     * @param user 学生实体
     * @return
     * @throws IOException
     * @throws ParserConfigurationException
     * @throws SAXException
     * @throws IllegalAccessException
     */
    public boolean updateUser(User user) throws IOException, ParserConfigurationException, SAXException, IllegalAccessException {
        if (user.getId() == null || user.getId().length() <= 0){
            throw new RuntimeException("更新错误：未传入用户id");
        }
        for(int i = 0; i < dataBases.length; i++){
            userMapper.setDataBase(dataBases[i]);
            userMapper.setXmlPath(xmlPath[i]);
            if(userMapper.updateUser(user) > 0){
                return true;
            }
        }
        return false;
    }

    /**
     * 根据学号删除学生
     * @param id 学号
     * @return
     * @throws ParserConfigurationException
     * @throws SAXException
     * @throws IOException
     */
    public boolean deleteUserById(String id) throws ParserConfigurationException, SAXException, IOException {
        if (id == null || id.length() <= 0){
            throw new RuntimeException("删除错误：未传入用户id");
        }
        for(int i = 0; i < dataBases.length; i++){
            userMapper.setDataBase(dataBases[i]);
            userMapper.setXmlPath(xmlPath[i]);
            if(userMapper.deleteUser(id) > 0){
                return true;
            }
        }
        return false;
    }

}
