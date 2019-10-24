package intergration.entity;

import java.io.Serializable;

/**
 * @author Jigubigu
 * @version 1.0
 * @date 2019/10/10 18:48
 */
public class User implements Serializable {
    /**
     * 学号
     */
    private String id;
    /**
     * 姓名
     */
    private String userName;

    /**
     * 性别
     */
    private String userSex;

    /**
     * 班级
     */
    private String className;

    public void setPropertyValue(String propertyName, Object propertyValue){
        if("userName".equals(propertyName)){
            this.setUserName((String) propertyValue);
        }else if("id".equals(propertyName)){
            this.setId((String) propertyValue);
        }else if("userSex".equals(propertyName)){
            this.setUserSex((String) propertyValue);
        }else{
            this.setClassName((String) propertyValue);
        }
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserSex() {
        return userSex;
    }

    public void setUserSex(String userSex) {
        this.userSex = userSex;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    @Override
    public String toString() {
        return "Student{" +
                "userName='" + userName + '\'' +
                ", id='" + id + '\'' +
                ", userSex='" + userSex + '\'' +
                ", className='" + className + '\'' +
                '}';
    }

    public User() {
    }

    public User(String id, String userName, String userSex, String className) {
        this.id = id;
        this.userName = userName;
        this.userSex = userSex;
        this.className = className;
    }
}
