package controller;

import dao.megaEntity.MegaBookDaoImpl;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Logger;

public class BookPageServlet extends HttpServlet {
    static final Logger logger = Logger.getLogger(String.valueOf(BookPageServlet.class));
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        long bookId = Long.parseLong(request.getParameter("book_id"));
        logger.info("GET book ID");
        request.setAttribute("book", MegaBookDaoImpl.getInstance().getById(bookId));
        logger.info("set book entity in request attribute");
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/book_page.jsp");
        dispatcher.forward(request, response);
    }
}
