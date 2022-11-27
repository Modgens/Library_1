package controller;

import dao.implementation.PersonalInfoDaoImpl;
import dao.implementation.UserDaoImpl;
import dao.implementation.UserOrdersDaoImpl;
import entity.UserOrders;
import service.Searching;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;


public class OrderSearchingServlet extends HttpServlet {
    static final Logger logger = Logger.getLogger(String.valueOf(OrderSearchingServlet.class));
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession();
        request.setCharacterEncoding("UTF-8");
        //get param
        String firstName = request.getParameter("first_name");
        logger.info("get param firstName - " + firstName);
        String lastName = request.getParameter("last_name");
        logger.info("get param lastName - " + lastName);
        String login = request.getParameter("login");
        logger.info("get param login - " + login);
        String page = request.getParameter("page");
        logger.info("get param page - " + page);
        if(page!=null&&page.equals("/user_orders.jsp")){
            if(firstName!=null)
                session.setAttribute("selectedFirstNameOrder", firstName);
            if(lastName!=null)
                session.setAttribute("selectedLastNameOrder", lastName);
            if(login!=null)
                session.setAttribute("selectedLoginOrder", login);
        }
        if(page!=null&&page.equals("/reading_room.jsp")){
            if(firstName!=null)
                session.setAttribute("selectedFirstNameReadRoom", firstName);
            if(lastName!=null)
                session.setAttribute("selectedLastNameReadRoom", lastName);
            if(login!=null)
                session.setAttribute("selectedLoginReadRoom", login);
        }

        //get dao
        PersonalInfoDaoImpl personalInfoDao = new PersonalInfoDaoImpl();
        UserDaoImpl userDao = new UserDaoImpl();
        UserOrdersDaoImpl userOrdersDao = new UserOrdersDaoImpl();

        HashMap<String, List<UserOrders>> map = new HashMap<>(Searching.getInstance().orderSearch(page, userOrdersDao, userDao, personalInfoDao, firstName, lastName, login));
        logger.info("get map with page name for dispatcher as key and list of order as value");
        String key = map.keySet().stream().findFirst().get();
        logger.info("key(page for dispatcher - )" + key);
        request.getSession().setAttribute(key, map.get(key));
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(request.getParameter("page"));
        dispatcher.forward(request, response);
    }
}
