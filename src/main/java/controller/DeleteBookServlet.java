package controller;

import dao.implementation.BookDaoImpl;
import entity.Book;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DeleteBookServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        BookDaoImpl bookDao = new BookDaoImpl();
        bookDao.deleteById(Long.parseLong(request.getParameter("book_id")));
        RequestDispatcher dispatcher = request .getRequestDispatcher("books.jsp");
        dispatcher.forward(request,response);
    }
}