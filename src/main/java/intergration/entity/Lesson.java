package intergration.entity;

/**
 * @author Jigubigu
 * @version 1.0
 * @date 2019/10/13 11:54
 */
public class Lesson {
    /**
     * 课程号
     */
    private String lessonId;
    /**
     * 课程名
     */
    private String lessonName;
    /**
     * 任课教师名
     */
    private String teacherName;
    /**
     * 课时
     */
    private String hours;

    public String getLessonId() {
        return lessonId;
    }

    public void setLessonId(String lessonId) {
        this.lessonId = lessonId;
    }

    public String getLessonName() {
        return lessonName;
    }

    public void setLessonName(String lessonName) {
        this.lessonName = lessonName;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public String getHours() {
        return hours;
    }

    public void setHours(String hours) {
        this.hours = hours;
    }

    @Override
    public String toString() {
        return "Lesson{" +
                "lessonId='" + lessonId + '\'' +
                ", lessonName='" + lessonName + '\'' +
                ", teacherName='" + teacherName + '\'' +
                ", hours='" + hours + '\'' +
                '}';
    }

    public void setPropertyValue(String propertyName, Object propertyValue){
        if("lessonId".equals(propertyName)){
            this.setLessonId((String) propertyValue);
        } else if("lessonName".equals(propertyName)){
            this.setLessonName((String) propertyValue);
        }else if("teacherName".equals(propertyName)){
            this.setTeacherName((String) propertyValue);
        }else{
            this.setHours((String) propertyValue);
        }
    }

    public Lesson() {
    }

    public Lesson(String lessonId, String lessonName, String teacherName, String hours) {
        this.lessonId = lessonId;
        this.lessonName = lessonName;
        this.teacherName = teacherName;
        this.hours = hours;
    }
}
