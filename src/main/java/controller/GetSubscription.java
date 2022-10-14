package controller;

import dao.implementation.BookDaoImpl;
import dao.implementation.SubscriptionsDaoImpl;
import entity.Book;
import entity.Subscriptions;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.util.Calendar;

public class GetSubscription extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        SubscriptionsDaoImpl subscriptionsDao = new SubscriptionsDaoImpl();
        long userId = Long.parseLong(request.getParameter("user_id"));
        Subscriptions subscriptions = new Subscriptions();

        subscriptions.setUserId(userId);
        subscriptions.setStartDate(new java.sql.Date(Calendar.getInstance().getTimeInMillis()));
        subscriptions.setEndDate(new java.sql.Date(Calendar.getInstance().getTimeInMillis()+2419200000L));
        subscriptionsDao.insert(subscriptions);
        request.setAttribute("status", "success");

        RequestDispatcher dispatcher = request .getRequestDispatcher("personal_account.jsp");
        dispatcher.forward(request,response);
    }
}