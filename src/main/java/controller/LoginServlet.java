package controller;

import dao.implementation.PersonalInfoDaoImpl;
import entity.PersonalInfo;
import entity.User;
import dao.implementation.UserDaoImpl;
import service.RegisterService;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;

public class LoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        new RegisterService().loginService(request, response);
    }
}
