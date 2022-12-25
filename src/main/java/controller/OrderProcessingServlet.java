package controller;

import dao.implementation.UserOrdersDaoImpl;
import entity.UserOrders;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Logger;

@MultipartConfig
public class OrderProcessingServlet extends HttpServlet {
    static final Logger logger = Logger.getLogger(String.valueOf(OrderProcessingServlet.class));
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("reading_room.jsp");
        request.setCharacterEncoding("utf-8");
        long order_id = Long.parseLong(request.getParameter("order_id"));
        logger.info("get param order_id - " + order_id);
        UserOrdersDaoImpl userOrdersDao = new UserOrdersDaoImpl();
        UserOrders userOrders = userOrdersDao.get(order_id);

        List<UserOrders> list = (List<UserOrders>) request.getSession().getAttribute("read_room_order_list");
        if(list==null)
            list=userOrdersDao.getAllForReadingRoom();
        UserOrders listOrder = list.stream().filter(o->o.getId()==order_id).findFirst().get();

        if(userOrders.getStatus().equals("Wait for reading room")){
            userOrders.setStatus("In reading room");
            userOrders.setStatusUa("В читальному залі");
            userOrders.setOrderDate(new java.sql.Date(Calendar.getInstance().getTimeInMillis()));
            userOrders.setDateToReturn(new java.sql.Date(Calendar.getInstance().getTimeInMillis()+86400000));
            listOrder.setOrderDate(userOrders.getOrderDate());
            listOrder.setDateToReturn(userOrders.getDateToReturn());
            listOrder.setStatusUa("В читальному залі");
            listOrder.setStatus("In reading room");
            userOrdersDao.update(userOrders);
            logger.info("update userOrder in db with status - Wait for reading room");
        } else {
            list.remove(listOrder);
            userOrdersDao.delete(userOrders);
            logger.info("delete userOrder in db with id - " + userOrders.getId());
        }
        request.getSession().setAttribute("read_room_order_list", list);
        logger.info("set list of orders in session with count - " + list.size());
        dispatcher.forward(request, resp);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("user_orders.jsp");
        request.setCharacterEncoding("utf-8");

        String date = request.getParameter("date");
        logger.info("get date from par - " + date);
        long order_id = Long.parseLong(request.getParameter("order_id"));
        logger.info("get param order_id - " + order_id);
        UserOrdersDaoImpl userOrdersDao = new UserOrdersDaoImpl();
        UserOrders userOrders = userOrdersDao.get(order_id);

        List<UserOrders> list = (List<UserOrders>) request.getSession().getAttribute("sub_order_list");
        if(list==null)
            list=userOrdersDao.getAllForSub();
        logger.info("get list with size - " + ((list != null) ? list.size() : "null"));
        UserOrders listOrder = list.stream().filter(o->o.getId()==order_id).findFirst().get();
        logger.info("get first obj in list with id - " + listOrder.getId());
        if(userOrders.getStatus().equals("Wait for order")){
            userOrders.setStatus("In order");
            userOrders.setStatusUa("Оформлений");
            userOrders.setOrderDate(new java.sql.Date(Calendar.getInstance().getTimeInMillis()));
            logger.info("set order date - " + userOrders.getOrderDate());
            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
            try {
                userOrders.setDateToReturn(new java.sql.Date(sdf.parse(date).getTime()));
                logger.info("set date to return - " + userOrders.getDateToReturn());
            } catch (ParseException e) {
                logger.warning("failed to parse param date from String to java.util.Date");
                e.printStackTrace();
            }
            listOrder.setStatus(userOrders.getStatus());
            listOrder.setStatusUa(userOrders.getStatusUa());
            listOrder.setOrderDate(userOrders.getOrderDate());
            listOrder.setDateToReturn(userOrders.getDateToReturn());
            userOrdersDao.update(userOrders);
        } else {
            list.remove(listOrder);
            userOrdersDao.delete(userOrders);
            logger.info("remove order from list of order to view and from db with id - " + userOrders.getId());
        }
        request.getSession().setAttribute("sub_order_list", list);
        dispatcher.forward(request,response);
    }
}
