package controller;

import dao.megaEntity.MegaBookDaoImpl;
import entity.megaEntity.MegaBook;
import service.BookSorting;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;


public class BookSortingServlet extends HttpServlet {
    static final Logger logger = Logger.getLogger(String.valueOf(BookSortingServlet.class));
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        //get param
        String genre = request.getParameter("genre");
        logger.info("get param genre with value - " + genre);

        String sortBy = request.getParameter("sort");
        logger.info("get param sort with value - " + sortBy);

        String bookName = request.getParameter("book");
        logger.info("get param book with value - " + bookName);

        String authorName = request.getParameter("author");
        logger.info("get param author with value - " + authorName);

        String page = request.getParameter("page");
        logger.info("get param page with value - " + page);

        List<MegaBook> list = new ArrayList<>(MegaBookDaoImpl.getInstance().getAll());
        logger.info("get list of all book in db");

        request.getSession().setAttribute(page.equals("/catalog.jsp") ? "catalog_list" : "books_list", BookSorting.getInstance().sort(genre, sortBy, bookName, authorName, list));
        logger.info("set Attribute in session with book entity in value");

        RequestDispatcher dispatcher = request.getRequestDispatcher(page);
        dispatcher.forward(request, response);
    }
}
