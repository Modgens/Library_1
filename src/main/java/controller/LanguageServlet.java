package controller;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Logger;

public class LanguageServlet extends HttpServlet {
    static final Logger logger = Logger.getLogger(String.valueOf(LanguageServlet.class));
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if(request.getParameter("language").equals("en")){
            request.getSession().setAttribute("rb", ResourceBundle.getBundle("Localization/Bundle", new Locale("en", "US")));
            logger.info("set lang - en");
        }
        if(request.getParameter("language").equals("ua")){
            request.getSession().setAttribute("rb", ResourceBundle.getBundle("Localization/Bundle", new Locale("ua", "ua")));
            logger.info("set lang - ua");
        }
        RequestDispatcher dispatcher = request.getRequestDispatcher(request.getParameter("path"));
        dispatcher.forward(request,response);
    }
}