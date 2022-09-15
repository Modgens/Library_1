package dao;

import entity.PersonalInfo;

public interface PersonalInfoDAO extends DAO<PersonalInfo> {
    PersonalInfo findByLoginAndPassword(String login, String password);
    PersonalInfo findByLogin(String login);
    long getId(String login);
}
