package controller;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Logger;

public class PaginationServlet extends HttpServlet {
    static final Logger logger = Logger.getLogger(String.valueOf(PaginationServlet.class));

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int page = Integer.parseInt(request.getParameter("page"));
        logger.info("get param page - " + page);
        String link = request.getParameter("link");
        logger.info("get param link - " + link);
        int size = Integer.parseInt(request.getParameter("size"));
        logger.info("get param size - " + size);
        int firstObj = (page - 1) * 15 + 1;
        logger.info("first Obj - " + firstObj);
        int lastObj = page * 15;
        logger.info("last Obj - " + lastObj);
        if (lastObj > size) {
            lastObj = size;
        }
        request.setAttribute("page", page);
        request.setAttribute("firstObj", firstObj);
        request.setAttribute("lastObj", lastObj);
        RequestDispatcher dispatcher = request.getRequestDispatcher(link);
        dispatcher.forward(request, response);
    }
}
