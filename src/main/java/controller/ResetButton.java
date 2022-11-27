package controller;

import dao.implementation.UserOrdersDaoImpl;
import dao.megaEntity.MegaBookDaoImpl;
import dao.megaEntity.MegaUserDaoImpl;
import entity.megaEntity.MegaBook;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Logger;

public class ResetButton extends HttpServlet {
    static final Logger logger = Logger.getLogger(String.valueOf(ResetButton.class));

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String reset_page = request.getParameter("reset_page");
        logger.info("get param page where need reset - " + reset_page);
        if(reset_page!=null&&reset_page.equals("catalog")){
            request.getSession().setAttribute("catalog_list", MegaBookDaoImpl.getInstance().getAll());
            removeAttributeBook(request);
        }
        if(reset_page!=null&&reset_page.equals("books")){
            request.getSession().setAttribute("books_list", MegaBookDaoImpl.getInstance().getAll());
            removeAttributeBook(request);
        }
        if(reset_page!=null&&reset_page.equals("users")){
            request.getSession().setAttribute("user_list", MegaUserDaoImpl.getInstance().getAll());
            removeAttributeUser(request);
        }
        if(reset_page!=null&&reset_page.equals("user_orders")){
            UserOrdersDaoImpl userOrdersDao = new UserOrdersDaoImpl();
            request.getSession().setAttribute("sub_order_list", userOrdersDao.getAllForSub());
            removeAttributeUserOrder(request);
        }
        if(reset_page!=null&&reset_page.equals("reading_room")){
            UserOrdersDaoImpl userOrdersDao = new UserOrdersDaoImpl();
            request.getSession().setAttribute("read_room_order_list", userOrdersDao.getAllForReadingRoom());
            removeAttributeUserReadingRoom(request);
        }
        RequestDispatcher dispatcher = request.getRequestDispatcher(reset_page + ".jsp");
        dispatcher.forward(request, response);
    }
    protected void removeAttributeBook(HttpServletRequest request){
        request.getSession().removeAttribute("selectedGenre");
        request.getSession().removeAttribute("selectedSorting");
        request.getSession().removeAttribute("selectedBook");
        request.getSession().removeAttribute("selectedAuthor");
    }
    protected void removeAttributeUser(HttpServletRequest request){
        request.getSession().removeAttribute("selectedFirstName");
        request.getSession().removeAttribute("selectedLastName");
        request.getSession().removeAttribute("selectedLogin");
    }
    protected void removeAttributeUserOrder(HttpServletRequest request){
        request.getSession().removeAttribute("selectedFirstNameOrder");
        request.getSession().removeAttribute("selectedLastNameOrder");
        request.getSession().removeAttribute("selectedLoginOrder");
    }
    protected void removeAttributeUserReadingRoom(HttpServletRequest request){
        request.getSession().removeAttribute("selectedFirstNameReadRoom");
        request.getSession().removeAttribute("selectedLastNameReadRoom");
        request.getSession().removeAttribute("selectedLoginReadRoom");
    }
}
