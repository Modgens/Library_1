package controller;

import dao.implementation.BookDaoImpl;
import dao.implementation.UserDaoImpl;
import entity.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class UserBanServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        long user_id = Long.parseLong(request.getParameter("user_id"));

        UserDaoImpl userDao = new UserDaoImpl();
        User user = userDao.get(user_id);
        if(user.getStatus().equals("not baned")){
            user.setStatus("baned");
        } else {
            user.setStatus("not baned");
        }
        userDao.update(user);
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/users.jsp");
        dispatcher.forward(request,response);
    }
}
