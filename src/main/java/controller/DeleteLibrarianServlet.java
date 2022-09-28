package controller;

import dao.implementation.LibrarianDaoImpl;
import dao.transaction.LibrarianDeleteTransaction;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DeleteLibrarianServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LibrarianDeleteTransaction deleteDao= new LibrarianDeleteTransaction();
        deleteDao.deleteById(Long.parseLong(request.getParameter("librarian_id")));
        RequestDispatcher dispatcher = request .getRequestDispatcher("librarians.jsp");
        dispatcher.forward(request,response);
    }
}