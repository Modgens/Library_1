package controller;

import dao.implementation.AuthorDaoImpl;
import dao.implementation.BookDaoImpl;
import dao.implementation.GenreDaoImpl;
import dao.implementation.PublisherDaoImpl;
import entity.Author;
import entity.Book;
import entity.Genre;
import entity.Publisher;
import javax.servlet.*;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.*;
import java.io.IOException;

@MultipartConfig
public class ChangeBookServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        RequestDispatcher dispatcher = request.getRequestDispatcher("change_book.jsp");
        if(request.getAttribute("error").equals("")){
            request.setAttribute("status", "success");

            Book book = (Book)request.getAttribute("book");

            BookDaoImpl bookDao = new BookDaoImpl();
            bookDao.update(book);

        }else {
            request.setAttribute("status", "failed");
        }
        dispatcher.forward(request,response);
    }
}
