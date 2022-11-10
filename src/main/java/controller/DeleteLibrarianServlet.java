package controller;

import dao.transaction.LibrarianDeleteTransaction;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Logger;

public class DeleteLibrarianServlet extends HttpServlet {
    static final Logger logger = Logger.getLogger(String.valueOf(DeleteLibrarianServlet.class));

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("librarians.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LibrarianDeleteTransaction deleteDao = new LibrarianDeleteTransaction();
        logger.info("create an transaction to delete librarian in all table");
        deleteDao.deleteById(Long.parseLong(request.getParameter("librarian_id")));
        logger.info("delete librarian with id - " + request.getParameter("librarian_id"));
        response.sendRedirect("librarians.jsp");
    }
}