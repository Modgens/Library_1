package controller;

import entity.UserEntity;
import dao.implementation.UserDaoImpl;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;

public class LoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        RequestDispatcher dispatcher = null;

        UserEntity userEntity = new UserEntity();
        userEntity.setEmail(email);
        userEntity.setPassword(password);

        UserDaoImpl userDaoImpl = new UserDaoImpl();
        //userDaoImpl.validate(userEntity);

        //if(userDaoImpl.validate(userEntity)){
            dispatcher = request.getRequestDispatcher("index.jsp");
        //}else{
            request.setAttribute("status", "failed");
            dispatcher = request.getRequestDispatcher("login.jsp");
        //}
        dispatcher.forward(request,response);
    }
}
