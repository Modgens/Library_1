package controller;

import dao.implementation.*;
import entity.User;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.*;


public class UserSearchingServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        //get param
        String firstName = request.getParameter("first_name");
        String lastName = request.getParameter("last_name");
        String login = request.getParameter("login");

        //get dao
        PersonalInfoDaoImpl personalInfoDao = new PersonalInfoDaoImpl();
        UserDaoImpl userDao = new UserDaoImpl();

        List<User> list = new ArrayList<>(userDao.getAll());
        Iterator<User> iterator = list.iterator();

        while (iterator.hasNext()) {
            User currentUser = iterator.next();
            if(!firstName.equals("")&&!personalInfoDao.get(currentUser.getPersonId()).getFirstName().equals(firstName)){
                iterator.remove();
                continue;
            }
            if(!lastName.equals("")&&!personalInfoDao.get(currentUser.getPersonId()).getLastName().equals(lastName)){
                iterator.remove();
                continue;
            }
            if(!login.equals("")&&!personalInfoDao.get(currentUser.getPersonId()).getLogin().equals(login)){
                iterator.remove();
            }
        }

        request.getSession().setAttribute("user_list", list);
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(request.getParameter("page"));
        dispatcher.forward(request,response);
    }
}
