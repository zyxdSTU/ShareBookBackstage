package dao;

import javabean.BookStore;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BookStoreMapper {
    public List<String> selectAllBook();
    public void insertBookStore(BookStore bookStore);
    public void deleteBookStore(@Param("phoneNumber") String phoneNumber, @Param("isbnNumber") String isbnNumber);
    public List<BookStore> selectBookStore(String phoneNumber);
    public List<String> selectOwners(String isbnNumber);
    public Integer bookStoreChooseRole(@Param("isbnNumber") String isbnNumber, @Param("phoneNumber") String phoneNumber);
}
