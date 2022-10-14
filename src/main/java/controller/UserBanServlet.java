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
import java.util.List;

public class UserBanServlet extends HttpServlet {
    int i = 0;
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        long user_id = Long.parseLong(request.getParameter("user_id"));
        List<User> list = (List<User>) request.getSession().getAttribute("user_list");
        if(list==null)
            list=new UserDaoImpl().getAll();
        UserDaoImpl userDao = new UserDaoImpl();
        User user = userDao.get(user_id);
        User listUser = list.stream().filter(u->u.getId()==user_id).findFirst().get();
        if(user.getStatus().equals("not baned")){
            user.setStatus("baned");
            listUser.setStatus("baned");
        } else {
            user.setStatus("not baned");
            listUser.setStatus("not baned");
        }
        userDao.update(user);
        request.getSession().setAttribute("user_list", list);
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/users.jsp");
        dispatcher.forward(request,response);
    }
}
