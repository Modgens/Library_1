package controller;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;

public class LoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String status = (String) request.getAttribute("status");
        String role = (String) request.getAttribute("role");
        RequestDispatcher dispatcher;
        if(status.equals("failed")){
            dispatcher = request.getRequestDispatcher("login.jsp");
        }else{
            HttpSession session = request.getSession();
            session.setAttribute("role", role);
            session.setAttribute("name", request.getAttribute("name"));
            request.setAttribute("status", "");
            if(role.equals("user")){
                long user_id = (long) request.getAttribute("user_id");
                session.setAttribute("user_id", user_id);
            }
            dispatcher = request.getRequestDispatcher("catalog.jsp");
        }
        dispatcher.forward(request, response);
    }
}
