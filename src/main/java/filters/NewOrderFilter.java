package filters;

import dao.implementation.BookDaoImpl;
import dao.implementation.SubscriptionsDaoImpl;
import dao.implementation.UserOrdersDaoImpl;
import service.Validator;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Logger;

public class NewOrderFilter implements Filter {

    private FilterConfig config;
    static final Logger logger = Logger.getLogger(String.valueOf(NewOrderFilter.class));

    public void init(FilterConfig config) throws ServletException {
        this.config=config;
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        request.setAttribute("error", "");
        request.setCharacterEncoding("utf-8");

        //get param
        long userId = Long.parseLong(request.getParameter("user_id"));
        long bookId = Long.parseLong(request.getParameter("book_id"));
        String status = request.getParameter("orderStatus");
        String ordersStatusUa = request.getParameter("orderStatusUa");
        logger.info("get param userId with value: " + userId);
        logger.info("get param bookId with value: " + bookId);
        logger.info("get param status with value: " + status);
        logger.info("get param ordersStatusUa with value: " + ordersStatusUa);


        //dao
        UserOrdersDaoImpl userOrdersDao = new UserOrdersDaoImpl();
        BookDaoImpl bookDao = new BookDaoImpl();
        SubscriptionsDaoImpl subscriptionsDao = new SubscriptionsDaoImpl();

        String error = Validator.getInstance().newOrderValidation(userId, bookId, status, ordersStatusUa, userOrdersDao, bookDao, subscriptionsDao);
        logger.info("result of methode Validator.newOrderValidation(...) : " + error);
        if (error != null)
            request.setAttribute("error", error);
        chain.doFilter(request, response);
    }
}
