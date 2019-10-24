package intergration.Service.impl;

import intergration.Service.LessonService;
import intergration.entity.Lesson;
import intergration.mapper.LessonMapper;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Jigubigu
 * @version 1.0
 * @date 2019/10/13 21:05
 */
public class LessonServiceImpl implements LessonService {
    private LessonMapper lessonMapper;
    /**
     * 数据库名
     */
    private String[] dataBases = {"test1", "test2"};
    /**
     * 对应数据库的xml文件路径
     */
    private String[] xmlPath = {
            "/xml/database1/lesson.xml",
            "/xml/database2/lesson.xml"
    };

    public LessonServiceImpl() {
        this.lessonMapper = new LessonMapper();
    }

    /**
     * 获取所有数据库中的课程数据
     * @return
     * @throws ParserConfigurationException
     * @throws SAXException
     * @throws IOException
     */
    public List<Lesson> getAllLesson() throws ParserConfigurationException, SAXException, IOException {
        List<Lesson> lessonList = new ArrayList<Lesson>();
        for(int i = 0; i < dataBases.length; i++){
            lessonMapper.setDataBase(dataBases[i]);
            lessonMapper.setXmlPath(xmlPath[i]);
            lessonList.addAll(lessonMapper.selectAllLesson());
        }
        return lessonList;
    }

    /**
     * 通过课程号查询课程信息
     * @param id 课程
     * @return
     * @throws ParserConfigurationException
     * @throws SAXException
     * @throws IOException
     */
    public Lesson getLessonById(String id) throws ParserConfigurationException, SAXException, IOException {
        Lesson lesson = null;
        for(int i = 0; i < dataBases.length; i++){
            lessonMapper.setDataBase(dataBases[i]);
            lessonMapper.setXmlPath(xmlPath[i]);
            lesson = lessonMapper.selectLessonById(id);
            if(lesson.getLessonId() != null && lesson.getLessonId().length() > 0){
                return  lesson;
            }
        }
        return null;
    }

    /**
     * 向数据库中插入课程信息
     * @param lesson 课程实体
     * @param databaseName 数据库名
     * @return
     * @throws ParserConfigurationException
     * @throws SAXException
     * @throws IOException
     */
    public boolean insertLesson(Lesson lesson, String databaseName) throws ParserConfigurationException, SAXException, IOException {
        if (databaseName == null || databaseName.length() <= 0){
            databaseName = dataBases[0];
        }
        for(int i = 0; i < xmlPath.length; i++){
            if(databaseName.equals(dataBases[i])){
                lessonMapper.setXmlPath(xmlPath[i]);
                lessonMapper.setDataBase(databaseName);
                if(lessonMapper.insertLesson(lesson) > 0){
                    return  true;
                }else{
                    throw new RuntimeException("插入课程数据失败");
                }
            }
        }
        return false;
    }

    /**
     * 更新课程信息
     * @param lesson 课程实体
     * @return
     * @throws IOException
     * @throws ParserConfigurationException
     * @throws SAXException
     * @throws IllegalAccessException
     */
    public boolean updateLesson(Lesson lesson) throws IOException, ParserConfigurationException, SAXException, IllegalAccessException {
        if (lesson.getLessonId() == null || lesson.getLessonId().length() <= 0){
            throw new RuntimeException("更新错误：未传入课程id");
        }
        for(int i = 0; i < dataBases.length; i++){
            lessonMapper.setDataBase(dataBases[i]);
            lessonMapper.setXmlPath(xmlPath[i]);
            if(lessonMapper.updateLesson(lesson) > 0){
                return true;
            }
        }
        return false;
    }

    /**
     * 根据课程号删除课程
     * @param id 课程号
     * @return
     * @throws ParserConfigurationException
     * @throws SAXException
     * @throws IOException
     */
    public boolean deleteLessonById(String id) throws ParserConfigurationException, SAXException, IOException {
        if (id == null || id.length() <= 0){
            throw new RuntimeException("删除错误：未传入课程id");
        }
        for(int i = 0; i < dataBases.length; i++){
            lessonMapper.setDataBase(dataBases[i]);
            lessonMapper.setXmlPath(xmlPath[i]);
            if(lessonMapper.deleteLesson(id) > 0){
                return true;
            }
        }
        return false;
    }

}
