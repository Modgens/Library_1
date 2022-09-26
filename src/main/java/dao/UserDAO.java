package dao;

import entity.User;

public interface UserDAO extends DAO<User>{
    boolean hasInfoId(long id);
}
