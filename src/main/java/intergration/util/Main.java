package intergration.util;

/**
 * @author Jigubigu
 * @version 1.0
 * @date 2019/10/10 19:44
 */
public class Main {
    public static void main(String[] args) {
        DBUtil dbUtil = DBUtil.getDbUtil();
        String sql = "select * from users";
        System.out.println(dbUtil.selectAllStudentNoMapperByDataBase1(sql));
    }
}
