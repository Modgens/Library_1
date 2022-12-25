package controller;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Logger;

@MultipartConfig
public class NewBookServlet extends HttpServlet {
    static final Logger logger = Logger.getLogger(String.valueOf(NewBookServlet.class));
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        RequestDispatcher dispatcher = request.getRequestDispatcher((String) request.getAttribute("path"));
        logger.info("get path from request and create dispatcher with path - " + request.getAttribute("path"));
        if(request.getAttribute("error").equals("")){
            request.setAttribute("status", "success");
            logger.info("set attribute status with value - SUCCESS");
        }else {
            request.setAttribute("status", "failed");
            logger.info("set attribute status with value - FAILED");
        }
        dispatcher.forward(request,response);
    }
}
