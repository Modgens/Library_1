package dao;

import java.sql.SQLException;
import java.util.List;

public interface DAO<T> {
    T get(long id);
    List<T> getAll();
    void insert(T t);
    void update(T t);
    void delete(T t);
}
