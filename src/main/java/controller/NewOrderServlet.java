package controller;

import javax.servlet.*;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.*;
import java.io.IOException;

@MultipartConfig
public class NewOrderServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("catalog.jsp");
        if(request.getAttribute("error").equals("")){
            request.setAttribute("status", "success");
        }else {
            request.setAttribute("status", "failed");
        }
        dispatcher.forward(request,response);
    }
}
