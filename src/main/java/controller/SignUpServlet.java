package controller;


import service.RegisterService;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;


public class SignUpServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher;
        dispatcher = request.getRequestDispatcher("signUp.jsp");
        RegisterService.signUp(request);
        dispatcher.forward(request,response);
    }
}
