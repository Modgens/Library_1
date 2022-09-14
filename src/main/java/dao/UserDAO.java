package dao;

import entity.User;

public interface UserDAO extends DAO<User>{
    User findByLogin(String login);
}
