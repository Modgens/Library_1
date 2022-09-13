package controller;

import dao.implementation.PersonDaoImpl;
import dao.implementation.UserDaoImpl;
import entity.PersonEntity;
import entity.UserEntity;

public class Demo {
    public static void main(String[] args) {
        UserDaoImpl user = new UserDaoImpl();
        UserEntity userEntity = new UserEntity();
        userEntity.setPassword("123");
        userEntity.setEmail("modgen@gmail.com");
        userEntity.setFirstName("MDMD");
        userEntity.setLastName("fdfd");
        userEntity.setLogin("qwert");
        user.insert(userEntity);
        PersonDaoImpl person = new PersonDaoImpl();
        person.insert(userEntity);
        System.out.println(person.get(1));
    }
}
