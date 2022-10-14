package controller;

import javax.servlet.*;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.*;
import java.io.IOException;

@MultipartConfig
public class BookServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        RequestDispatcher dispatcher = request.getRequestDispatcher((String) request.getAttribute("path"));
        if(request.getAttribute("error").equals("")){
            request.setAttribute("status", "success");
        }else {
            request.setAttribute("status", "failed");
        }
        dispatcher.forward(request,response);
    }
}
