package controller;

import dao.transaction.CreateLibrarianTransaction;
import entity.PersonalInfo;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Logger;

@MultipartConfig
public class CreateLibrarianServlet extends HttpServlet {
    static final Logger logger = Logger.getLogger(String.valueOf(CreateLibrarianServlet.class));
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        RequestDispatcher dispatcher = request.getRequestDispatcher("new_librarian.jsp");
        logger.info("create a dispatcher with url - " + "new_librarian.jsp");

        if(request.getAttribute("error").equals("")) {
            //final set
            CreateLibrarianTransaction.getInstance().create((PersonalInfo) request.getAttribute("person"));
            logger.info("get personalInfo object and use it to make transaction for creating a librarian");
            request.setAttribute("status", "success");
        } else {
            request.setAttribute("status", "failed");
        }
        dispatcher.forward(request,response);
    }
}
