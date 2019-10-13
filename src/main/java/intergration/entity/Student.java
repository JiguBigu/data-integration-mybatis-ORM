package intergration.entity;

/**
 * @author Jigubigu
 * @version 1.0
 * @date 2019/10/10 18:48
 */
public class Student {
    private static final long serialVersionUID = 1L;
    /**
     * 姓名
     */
    private String studentName;

    /**
     * 学号
     */
    private String id;

    /**
     * 性别
     */
    private String Sex;

    /**
     * 班级
     */
    private String studentClass;



    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSex() {
        return Sex;
    }

    public void setSex(String sex) {
        Sex = sex;
    }

    public String getStudentClass() {
        return studentClass;
    }

    public void setStudentClass(String studentClass) {
        this.studentClass = studentClass;
    }

    @Override
    public String toString() {
        return "Student{" +
                "studentName='" + studentName + '\'' +
                ", id='" + id + '\'' +
                ", Sex='" + Sex + '\'' +
                ", studentClass='" + studentClass + '\'' +
                '}';
    }
}
