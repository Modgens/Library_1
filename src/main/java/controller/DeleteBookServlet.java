package controller;

import dao.implementation.BookDaoImpl;
import entity.Book;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;

public class DeleteBookServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        BookDaoImpl bookDao = new BookDaoImpl();
        Book book = bookDao.get(Long.parseLong(request.getParameter("book_id")));
        if(bookDao.get(book.getId()).getId()!=0L) {
            bookDao.delete(book);
            if (!book.getImgName().equals("book0.jsp")) {
                File f = new File("C:\\Users\\Modgen\\IdeaProjects\\Library_1\\src\\main\\webapp\\book_images\\" + book.getImgName());
                f.delete();
            }
        }

        RequestDispatcher dispatcher = request .getRequestDispatcher("books.jsp");

        dispatcher.forward(request,response);
    }
}