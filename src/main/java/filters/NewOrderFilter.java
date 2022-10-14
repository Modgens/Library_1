package filters;

import dao.SubscriptionsDAO;
import dao.implementation.*;
import entity.Book;
import entity.UserOrders;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class NewOrderFilter implements Filter {

    private FilterConfig config;

    public void init(FilterConfig config) throws ServletException {
        this.config=config;
        System.out.println("new-order-filter");
    }

    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        request.setAttribute("error", "");

        //get param
        long userId = Long.parseLong(request.getParameter("user_id"));
        long bookId = Long.parseLong(request.getParameter("book_id"));
        String status = request.getParameter("orderStatus");

        //dao
        UserOrdersDaoImpl userOrdersDao = new UserOrdersDaoImpl();
        BookDaoImpl bookDao = new BookDaoImpl();
        SubscriptionsDaoImpl subscriptionsDao = new SubscriptionsDaoImpl();

        //entity
        Book book = bookDao.get(bookId);
        UserOrders userOrders = new UserOrders();

        //count
        int bookCountInOrder = userOrdersDao.bookCountInUsers(bookId);
        int bookCount = book.getCount();

        //subscribe validation

        if(subscriptionsDao.getFromUserDao(userId) == null){
            request.setAttribute("error", "Sorry, but You should get subscription in your Personal Account page");
            chain.doFilter(request, response);
            return;
        }

        //count validation
        if (bookCount<=bookCountInOrder){
            request.setAttribute("error", "Sorry, but we do not have this book in stock at the moment");
            chain.doFilter(request, response);
            return;
        }

        //user already has one
        if(userOrdersDao.userAlreadyHasThisBook(bookId, userId)){
            request.setAttribute("error", "You have already rented this book");
            chain.doFilter(request, response);
            return;
        }

        //users can't have more than 10 book for one
        if (userOrdersDao.countOrderedOneUser(userId)>=10){
            request.setAttribute("error", "Sorry, but You can't order more than 10 book");
            chain.doFilter(request, response);
            return;
        }

        userOrders.setStatus(status);
        userOrders.setUserId(userId);
        userOrders.setBookId(bookId);
        userOrders.setStatusUa(request.getParameter("orderStatusUa"));

        userOrdersDao.insert(userOrders);

        chain.doFilter(request, response);
    }
}
