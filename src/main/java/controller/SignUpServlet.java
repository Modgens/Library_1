package controller;

import dao.transaction.CreateUserTransaction;
import entity.PersonalInfo;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Logger;


public class SignUpServlet extends HttpServlet {
    static final Logger logger = Logger.getLogger(String.valueOf(SignUpServlet.class));

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher;
        dispatcher = request.getRequestDispatcher("signUp.jsp");
        if (request.getAttribute("error").equals("")) {
            //final set
            CreateUserTransaction createUserTransaction = new CreateUserTransaction();
            createUserTransaction.create((PersonalInfo) request.getAttribute("person"), (String) request.getAttribute("email"), "not baned");
            logger.info("make transaction with atr person, person id - " + ((PersonalInfo) request.getAttribute("person")).getId() + " and atr email - " + request.getAttribute("email"));
            request.setAttribute("status", "success");
            logger.info("status - success");
        } else {
            request.setAttribute("status", "failed");
            logger.info("status - failed");
        }
        dispatcher.forward(request, response);
    }
}
