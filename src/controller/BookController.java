package controller;

import com.google.gson.Gson;
import dao.BookMapper;
import javabean.Book;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import util.SqlSessionHelper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;

@Controller
public class BookController {
    @RequestMapping("selectByIsbn")
    public void selectByIsbn(HttpServletRequest request, HttpServletResponse response) {
        try {
            SqlSession sqlSession = SqlSessionHelper.getSessionFactory().openSession();
            BookMapper bookMapper = sqlSession.getMapper(BookMapper.class);
            String isbnNumber = request.getParameter("IsbnNumber");
            Book book = bookMapper.selectByIsbn(isbnNumber);
            response.getWriter().write(new Gson().toJson(book));
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
}
