package dao;

import entity.BookEntity;

import java.util.List;

public interface BookDAO extends DAO<BookEntity> {
    List<BookEntity> findByName(String name);
}
