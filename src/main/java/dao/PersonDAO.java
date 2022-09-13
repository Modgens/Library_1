package dao;

import entity.PersonEntity;

public interface PersonDAO extends DAO<PersonEntity> {
    PersonEntity findByLoginAndPassword(String login, String password);
}
