package controller;

import dao.implementation.BookDaoImpl;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class BookPageServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        long bookId = Long.parseLong(request.getParameter("book_id"));
        BookDaoImpl bookDao = new BookDaoImpl();
        request.setAttribute("book", bookDao.get(bookId));
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/book_page.jsp");
        dispatcher.forward(request,response);
    }
}
