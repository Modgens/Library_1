package controller;

import dao.transaction.CreateLibrarianTransaction;
import entity.PersonalInfo;

import javax.servlet.*;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.*;
import java.io.IOException;

@MultipartConfig
public class CreateLibrarianServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        RequestDispatcher dispatcher = request.getRequestDispatcher("new_librarian.jsp");

        if(request.getAttribute("error").equals("")){
            //final set
            CreateLibrarianTransaction librarianTransaction = new CreateLibrarianTransaction();
            librarianTransaction.create((PersonalInfo)request.getAttribute("person"));
            request.setAttribute("status", "success");
        } else {
            request.setAttribute("status", "failed");
        }
        dispatcher.forward(request,response);
    }
}
