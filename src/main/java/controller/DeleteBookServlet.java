package controller;

import dao.implementation.BookDaoImpl;
import entity.Book;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

public class DeleteBookServlet extends HttpServlet {
    static final Logger logger = Logger.getLogger(String.valueOf(DeleteBookServlet.class));

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("books.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        BookDaoImpl bookDao = new BookDaoImpl();
        Book book = bookDao.get(Long.parseLong(request.getParameter("book_id")));
        logger.info("get book from dao by id");
        if (bookDao.get(book.getId()).getId() != 0L) {
            bookDao.delete(book);
            logger.info("delete book in dao");
            if (!book.getImgName().equals("book0.jpg")) {
                File f = new File("C:\\Users\\Modgen\\IdeaProjects\\Library_1\\src\\main\\webapp\\book_images\\" + book.getImgName());
                if (f.delete()) {
                    logger.info("successful deleted book img with name" + book.getImgName());
                } else {
                    logger.warning("CAN'T DELETE THE IMG WITH NAME - " + book.getImgName());
                }
            }
        }
        response.sendRedirect("books.jsp");
    }
}