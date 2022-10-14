package controller;

import dao.implementation.*;
import entity.PersonalInfo;
import entity.User;
import entity.UserOrders;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.*;


public class OrderSearchingServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        //get param
        String firstName = request.getParameter("first_name");
        String lastName = request.getParameter("last_name");
        String login = request.getParameter("login");
        String page = request.getParameter("page");

        //get dao
        PersonalInfoDaoImpl personalInfoDao = new PersonalInfoDaoImpl();
        UserDaoImpl userDao = new UserDaoImpl();
        UserOrdersDaoImpl userOrdersDao = new UserOrdersDaoImpl();

        List<UserOrders> list;
        String listName = "order_list";
        if(page.equals("/reading_room.jsp")){
            list = new ArrayList<>(userOrdersDao.getAllForReadingRoom());
            listName = "read_room_order_list";
        } else {
            list = new ArrayList<>(userOrdersDao.getAllForSub());
            listName = "sub_order_list";
        }

        Iterator<UserOrders> iterator = list.iterator();

        while (iterator.hasNext()) {
            UserOrders currentOrder = iterator.next();
            User user = userDao.get((currentOrder.getUserId()));
            PersonalInfo personalInfo = personalInfoDao.get(user.getPersonId());
            if(!firstName.equals("")&&!personalInfo.getFirstName().equals(firstName)){
                iterator.remove();
                continue;
            }
            if(!lastName.equals("")&&!personalInfo.getLastName().equals(lastName)){
                iterator.remove();
                continue;
            }
            if(!login.equals("")&&!personalInfo.getLogin().equals(login)){
                iterator.remove();
            }
        }

        request.getSession().setAttribute(listName, list);
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(request.getParameter("page"));
        dispatcher.forward(request,response);
    }
}
