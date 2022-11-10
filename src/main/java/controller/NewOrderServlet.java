package controller;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Logger;

@MultipartConfig
public class NewOrderServlet extends HttpServlet {
    static final Logger logger = Logger.getLogger(String.valueOf(NewOrderServlet.class));
//    @Override
//    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        request.getRequestDispatcher("catalog.jsp").forward(request,response);
//    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info("get atr error with val - " + request.getAttribute("error"));
        if (request.getAttribute("error").equals("")) {
            logger.info("result of creating new order - success");
            request.setAttribute("status", "success");
        } else {
            logger.info("result of creating new order - failed");
            request.setAttribute("status", "failed");
        }
        request.getRequestDispatcher("catalog.jsp").forward(request, response);
        //response.sendRedirect("catalog.jsp");
    }
}
