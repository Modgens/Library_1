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
    public void signUp(HttpServletRequest request) {
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
    public void loginService(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String login = request.getParameter("login");
        String password = request.getParameter("password");
        RequestDispatcher dispatcher = null;
        PersonalInfoDaoImpl personalInfoDao = new PersonalInfoDaoImpl();
        PersonalInfo personalInfo = personalInfoDao.findByLoginAndPassword(login, password);
        if(personalInfo.getId()!=0){//if id==0   =>   there is empty entity
            dispatcher = request.getRequestDispatcher("index.jsp");
        }else{
            request.setAttribute("status", "failed");
            dispatcher = request.getRequestDispatcher("login.jsp");
        }
        dispatcher.forward(request,response);
    }
}
