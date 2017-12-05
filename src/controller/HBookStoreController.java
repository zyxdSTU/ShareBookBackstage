package controller;

import com.google.gson.Gson;
import dao.BookStoreMapper;
import dao.HBookStoreMapper;
import javabean.HBookStore;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import util.SqlSessionHelper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@Controller
public class HBookStoreController {
    @RequestMapping("selectHBookStore")
    public void selectHBookStore(HttpServletRequest request, HttpServletResponse response) throws IOException {
        SqlSession sqlSession = SqlSessionHelper.getSessionFactory().openSession();
        HBookStoreMapper hBookStoreMapper = sqlSession.getMapper(HBookStoreMapper.class);

        String phoneNumber = request.getParameter("phoneNumber");

        List<HBookStore> list = hBookStoreMapper.selectHBookStore(phoneNumber);

        PrintWriter writer = response.getWriter();
        writer.write(new Gson().toJson(list));

        sqlSession.close();
    }
}
