package controller;

import dao.implementation.SubscriptionsDaoImpl;
import entity.Subscriptions;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Calendar;
import java.util.logging.Logger;

public class GetSubscription extends HttpServlet {
    static final Logger logger = Logger.getLogger(String.valueOf(GetSubscription.class));

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("personal_account.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        SubscriptionsDaoImpl subscriptionsDao = new SubscriptionsDaoImpl();
        long userId = Long.parseLong(request.getParameter("user_id"));
        logger.info("get user id - " + userId);
        Subscriptions subscriptions = new Subscriptions();
        subscriptions.setUserId(userId);
        subscriptions.setStartDate(new java.sql.Date(Calendar.getInstance().getTimeInMillis()));
        subscriptions.setEndDate(new java.sql.Date(Calendar.getInstance().getTimeInMillis() + 2419200000L));
        logger.info("set data and id in sub obj");
        subscriptionsDao.insert(subscriptions);
        logger.info("insert sub obj");
        request.setAttribute("status", "success");
        response.sendRedirect("personal_account.jsp");
    }
}