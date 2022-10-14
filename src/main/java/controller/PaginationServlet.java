package controller;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class PaginationServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int page = Integer.parseInt(request.getParameter("page"));
        String link = request.getParameter("link");
        int size = Integer.parseInt(request.getParameter("size"));
        int firstObj = (page-1)*15+1;
        int lastObj = page*15;
        if(lastObj>size){
            lastObj=size;
        }
        request.setAttribute("page", page);
        request.setAttribute("firstObj", firstObj);
        request.setAttribute("lastObj", lastObj);
        RequestDispatcher dispatcher = request.getRequestDispatcher(link);
        dispatcher.forward(request, response);
    }
}
