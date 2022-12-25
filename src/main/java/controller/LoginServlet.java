package controller;

import service.SubscriptionChecking;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.logging.Logger;

public class LoginServlet extends HttpServlet {
    static final Logger logger = Logger.getLogger(String.valueOf(LoginServlet.class));
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String status = (String) request.getAttribute("status");
        String role = (String) request.getAttribute("role");
        RequestDispatcher dispatcher;
        if(status.equals("failed")){
            logger.info("failed to login");
            dispatcher = request.getRequestDispatcher("login.jsp");
        }else{
            logger.info("login is success");
            HttpSession session = request.getSession();
            session.setAttribute("role", role);
            logger.info("set role in session - " + role);
            session.setAttribute("name", request.getAttribute("name"));
            logger.info("set name in session - " + request.getAttribute("name"));
            request.setAttribute("status", "");
            if(role.equals("user")){
                long user_id = (long) request.getAttribute("user_id");
                session.setAttribute("user_id", user_id);
                SubscriptionChecking.getInstance().check(user_id);
                logger.info("set user id in session and check subscription");
            }
            dispatcher = request.getRequestDispatcher("catalog.jsp");
        }
        dispatcher.forward(request, response);
    }
}
