package controller;

import entity.User;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;

public class LoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String status = (String) request.getAttribute("status");
        String role = (String) request.getAttribute("role");
        long user_id = (long) request.getAttribute("user_id");
        RequestDispatcher dispatcher;
        if(status.equals("failed")){
            dispatcher = request.getRequestDispatcher("login.jsp");
        }else{
            HttpSession session = request.getSession();
            session.setAttribute("role", role);
            session.setAttribute("name", request.getAttribute("name"));
            if(role.equals("user")){
                session.setAttribute("user_id", user_id);
            }

            dispatcher = request.getRequestDispatcher("index.jsp");
        }
        dispatcher.forward(request, response);
    }
}
