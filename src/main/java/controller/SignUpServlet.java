package controller;


import dao.implementation.UserDaoImpl;
import entity.UserEntity;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;


public class SignUpServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = null;
        UserEntity userEntity = new UserEntity();
        userEntity.setEmail(request.getParameter("email"));
        userEntity.setPassword(request.getParameter("password"));
        userEntity.setFirstName(request.getParameter("first_name"));
        userEntity.setLastName(request.getParameter("last_name"));
        UserDaoImpl userDaoImpl = new UserDaoImpl();
        dispatcher = request.getRequestDispatcher("signUp.jsp");
        //if(userDaoImpl.emailValidate(userEntity.getEmail())){
            request.setAttribute("status", "failed");
        //}else{
            userDaoImpl.insert(userEntity);
            request.setAttribute("status", "success");
        //}
        dispatcher.forward(request,response);
    }
}
