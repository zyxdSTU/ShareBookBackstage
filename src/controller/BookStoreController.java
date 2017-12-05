package controller;

import com.google.gson.Gson;
import dao.BookMapper;
import dao.BookStoreMapper;
import dao.HBookStoreMapper;
import javabean.Book;
import javabean.BookStore;
import javabean.HBookStore;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import util.SqlSessionHelper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.ws.RequestWrapper;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Scanner;

@Controller
public class BookStoreController {
    @RequestMapping("selectAllBook")
    public void selectAllBook(HttpServletRequest request, HttpServletResponse response) throws IOException {
        SqlSession  sqlSession = SqlSessionHelper.getSessionFactory().openSession();
        BookStoreMapper bookStoreMapper = sqlSession.getMapper(BookStoreMapper.class);
        List<String> bookStores = bookStoreMapper.selectAllBook();
        response.getWriter().write(new Gson().toJson(bookStores));
        sqlSession.close();
    }

    @RequestMapping("groundBook")
    public void groundBook(HttpServletRequest request, HttpServletResponse response) throws IOException {
        SqlSession sqlSession = SqlSessionHelper.getSessionFactory().openSession();
        BookStoreMapper bookStoreMapper = sqlSession.getMapper(BookStoreMapper.class);
        BookMapper bookMapper = sqlSession.getMapper(BookMapper.class);
        HBookStoreMapper hBookStoreMapper = sqlSession.getMapper(HBookStoreMapper.class);

        Scanner scanner  = new Scanner(request.getReader());
        StringBuilder content = new StringBuilder();
        while(scanner.hasNextLine()) {
            content.append(scanner.nextLine());
        }
        System.out.println(content.toString());
        Book book = new Gson().fromJson(content.toString(), Book.class);

        Book bookTemp = bookMapper.selectByIsbn(book.getIsbnNumber());
        if(bookTemp == null) {
            bookMapper.insertBook(book);
        }

        String phoneNumber = request.getParameter("phoneNumber");
        String btime = request.getParameter("btime");

        BookStore bookStore = new BookStore();
        bookStore.setPhoneNumber(phoneNumber);
        bookStore.setIsbnNumber(book.getIsbnNumber());
        bookStore.setBtime(btime);
        bookStore.setFlag(1);

        bookStoreMapper.insertBookStore(bookStore);

        /*如果是下架书上架，执行这步*/
        hBookStoreMapper.deleteHBookStore(phoneNumber, book.getIsbnNumber());

        sqlSession.commit(); sqlSession.close();
    }

    @RequestMapping("undercarriageBook")
    public void undercarriageBook(HttpServletRequest request, HttpServletResponse response) {
        SqlSession sqlSession = SqlSessionHelper.getSessionFactory().openSession();
        BookStoreMapper bookStoreMapper = sqlSession.getMapper(BookStoreMapper.class);
        HBookStoreMapper hBookStoreMapper = sqlSession.getMapper(HBookStoreMapper.class);

        String phoneNumber = request.getParameter("phoneNumber");
        String isbnNumber = request.getParameter("isbnNumber");
        String btime = request.getParameter("btime");

        /*从共享书籍库中删除*/
        bookStoreMapper.deleteBookStore(phoneNumber, isbnNumber);

        /*添加进历史书籍库*/
        hBookStoreMapper.insertHBookStore(new HBookStore(phoneNumber, isbnNumber, btime));

        sqlSession.commit(); sqlSession.close();
    }

    @RequestMapping("selectBookStore")
    public void selectBookStore(HttpServletRequest request, HttpServletResponse response) throws  IOException{
        SqlSession sqlSession = SqlSessionHelper.getSessionFactory().openSession();
        BookStoreMapper bookStoreMapper = sqlSession.getMapper(BookStoreMapper.class);

        String phoneNumber = request.getParameter("phoneNumber");

        List<BookStore> list = bookStoreMapper.selectBookStore(phoneNumber);

        response.getWriter().write(new Gson().toJson(list));

        sqlSession.close();
    }

    /*有一个选择 所有者(两个） 第三方 借书者 有一个接口*/
    @RequestMapping("chooseRole")
    public void chooseRole(HttpServletRequest request, HttpServletResponse response) throws  IOException{
        SqlSession sqlSession = SqlSessionHelper.getSessionFactory().openSession();
        BookStoreMapper bookStoreMapper = sqlSession.getMapper(BookStoreMapper.class);
        String phoneNumber = request.getParameter("phoneNumber");
        String isbnNumber = request.getParameter("isbnNumber");
        Integer flag = bookStoreMapper.bookStoreChooseRole(isbnNumber, phoneNumber);
        PrintWriter writer = response.getWriter();
        if(flag == null) {
            writer.write("three");
        } else if(flag == 1) {
            writer.write("one");
        }else if(flag == 0) {
            writer.write("two");
        }
        sqlSession.close();
    }

    @RequestMapping("selectOwners")
    public void selectOwners(HttpServletRequest request, HttpServletResponse response) throws Exception{
        SqlSession sqlSession = SqlSessionHelper.getSessionFactory().openSession();
        BookStoreMapper bookStoreMapper = sqlSession.getMapper(BookStoreMapper.class);
        String isbnNumber = request.getParameter("isbnNumber");
        List<String> phoneNumberList = bookStoreMapper.selectOwners(isbnNumber);
        response.getWriter().write(new Gson().toJson(phoneNumberList));
        sqlSession.close();
    }
}
