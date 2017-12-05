package dao;

import javabean.Book;

public interface BookMapper {
    public Book selectByIsbn(String isbnNumber);
    public void insertBook(Book book);
}
