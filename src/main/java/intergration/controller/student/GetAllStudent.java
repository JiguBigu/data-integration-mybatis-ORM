package intergration.controller.student;

import intergration.util.DBUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Jigubigu
 * @version 1.0
 * @date 2019/10/10 19:22
 */
public class GetAllStudent extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
        DBUtil dbUtil = DBUtil.getDbUtil();

    }

}
