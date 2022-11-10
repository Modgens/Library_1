package controller;

import dao.megaEntity.MegaUserDaoImpl;
import entity.megaEntity.MegaUser;
import service.Searching;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;


public class UserSearchingServlet extends HttpServlet {
    static final Logger logger = Logger.getLogger(String.valueOf(UserSearchingServlet.class));

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        //get param
        String firstName = request.getParameter("first_name");
        logger.info("get param first name - " + firstName);
        String lastName = request.getParameter("last_name");
        logger.info("get param last name - " + lastName);
        String login = request.getParameter("login");
        logger.info("get param login - " + login);

        //get dao
        List<MegaUser> list = new ArrayList<>(MegaUserDaoImpl.getInstance().getAll());
        request.getSession().setAttribute("user_list", Searching.getInstance().userSearch(list, firstName, lastName, login));
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(request.getParameter("page"));
        dispatcher.forward(request, response);
    }
}
