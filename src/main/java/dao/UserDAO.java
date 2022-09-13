package dao;

import entity.UserEntity;

public interface UserDAO extends DAO<UserEntity>{
    UserEntity findByName(String name);
}
