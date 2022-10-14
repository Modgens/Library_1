package controller;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

public class LanguageServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if(request.getParameter("language").equals("en")){
            request.getSession().setAttribute("rb", ResourceBundle.getBundle("Localization/Bundle", new Locale("en", "US")));
        }
        if(request.getParameter("language").equals("ua")){
            request.getSession().setAttribute("rb", ResourceBundle.getBundle("Localization/Bundle", new Locale("ua", "ua")));
        }
        RequestDispatcher dispatcher = request.getRequestDispatcher(request.getParameter("path"));
        dispatcher.forward(request,response);
    }
}