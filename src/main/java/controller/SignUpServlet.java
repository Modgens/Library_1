package controller;

import dao.transaction.CreateUserTransaction;
import entity.PersonalInfo;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;


public class SignUpServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher;
        dispatcher = request.getRequestDispatcher("signUp.jsp");

        if(request.getAttribute("error").equals("")){
            //final set
            CreateUserTransaction createUserTransaction = new CreateUserTransaction();
            createUserTransaction.create((PersonalInfo) request.getAttribute("person"), (String) request.getAttribute("email"), "not baned");
            request.setAttribute("status", "success");
        } else {
            request.setAttribute("status", "failed");
        }

        dispatcher.forward(request,response);
    }
}
