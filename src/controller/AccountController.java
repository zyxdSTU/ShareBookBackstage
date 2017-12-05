package controller;

import com.google.gson.Gson;
import dao.AccountMapper;
import javabean.Account;
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
import java.util.Scanner;

@Controller
public class AccountController {
    @RequestMapping("register")
    public void register(HttpServletRequest request, HttpServletResponse response){
        try {
            SqlSession sqlSession = SqlSessionHelper.getSessionFactory().openSession();
            AccountMapper accountMapper = sqlSession.getMapper(AccountMapper.class);
            BufferedReader reader = request.getReader();
            StringBuilder content = new StringBuilder();
            Scanner sc = new Scanner(reader);
            while (sc.hasNextLine()) {
                content.append(sc.nextLine());
            }
            System.out.println(content.toString());
            Account account = new Gson().fromJson(content.toString(), Account.class);
            accountMapper.insertAccount(account);
            sqlSession.commit();
            sqlSession.close();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping("login")
    public void login(HttpServletRequest request, HttpServletResponse response) throws IOException {
        SqlSession sqlSession = SqlSessionHelper.getSessionFactory().openSession();
        AccountMapper accountMapper = sqlSession.getMapper(AccountMapper.class);
        String phoneNumber = request.getParameter("phoneNumber");
        String password = request.getParameter("password");
        if(accountMapper.verify(phoneNumber).equals(password)) {
            response.getWriter().write("true");
        }else{
            response.getWriter().write("false");
        }
        sqlSession.close();
    }

    @RequestMapping("selectByPhoneNumber")
    public void selectByPhoneNumber(HttpServletRequest request, HttpServletResponse response) throws IOException{
        SqlSession sqlSession = SqlSessionHelper.getSessionFactory().openSession();
        AccountMapper accountMapper = sqlSession.getMapper(AccountMapper.class);
        String phoneNumber = request.getParameter("PhoneNumber");
        Account account = accountMapper.selectByPhoneNumber(phoneNumber);
        response.getWriter().write(new Gson().toJson(account));
    }

    @RequestMapping("uploadImage")
    public void getImage(HttpServletResponse response, HttpServletRequest request) {
        String fileName = request.getParameter("image");
        File imageFile = new File(request.getServletContext().getRealPath("/images"), fileName);
        System.out.println(imageFile.getAbsolutePath());
        /*更新数据库image url*/
        String phoneNumber = fileName.substring(0, 11);
        SqlSession sqlSession = SqlSessionHelper.getSessionFactory().openSession();
        AccountMapper accountMapper = sqlSession.getMapper(AccountMapper.class);
        accountMapper.updateImage(fileName, phoneNumber);
        sqlSession.commit();

        DiskFileItemFactory dff = new DiskFileItemFactory();
        ServletFileUpload sfu = new ServletFileUpload(dff);
        List<FileItem> items = null;
        try{
            items = sfu.parseRequest(request);
        }catch(Exception e){
            e.printStackTrace();
        }
        for(FileItem item:items){
            if(item.isFormField()){
                //普通表单
                System.out.println("error");
                String fieldName = item.getFieldName();
                String fieldValue = item.getString();
            }else{
                try{
                    System.out.println("ok");
                    item.write(imageFile);
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        }
    }

    /*比特流 */
    @RequestMapping("downloadImage")
    public void sendImage(HttpServletResponse response, HttpServletRequest request){
        String fileName = request.getParameter("image");
        InputStream in = null;
        OutputStream out = null;
        byte[] bytes = new byte[1024];
        int len = 0;
        try {
            // 读取文件
            in = new FileInputStream(request.getServletContext().getRealPath("/images") +"/" + fileName);
            // 写入浏览器的输出流
            out = response.getOutputStream();
            while ((len = in.read(bytes)) > 0)
                out.write(bytes, 0, len);
        }catch(Exception e) {
            e.printStackTrace();
        }
    }
}
