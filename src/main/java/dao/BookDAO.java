package dao;

import entity.Book;

import java.util.List;

public interface BookDAO extends DAO<Book> {
    List<Book> findByName(String name);
}
