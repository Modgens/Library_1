package service;

import dao.implementation.PersonalInfoDaoImpl;
import dao.implementation.UserDaoImpl;
import entity.PersonalInfo;
import entity.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RegisterService {
    public static void signUp(HttpServletRequest request) {
        PersonalInfoDaoImpl personalInfoDao = new PersonalInfoDaoImpl();
        String login = request.getParameter("login");

        if (personalInfoDao.findByLogin(login).getId() != 0) {
            request.setAttribute("status", "failed");
        } else {
            User user = new User();
            PersonalInfo personalInfo = new PersonalInfo();
            UserDaoImpl userDao = new UserDaoImpl();

            personalInfo.setPassword(request.getParameter("password"));
            personalInfo.setFirstName(request.getParameter("first_name"));
            personalInfo.setLastName(request.getParameter("last_name"));
            personalInfo.setLogin(login);

            personalInfoDao.insert(personalInfo);//insert new personal info

            user.setEmail(request.getParameter("email"));
            user.setPersonId(personalInfoDao.getId(login));//find id new personal info by login

            userDao.insert(user);//insert new user
            request.setAttribute("status", "success");
        }
    }
}
