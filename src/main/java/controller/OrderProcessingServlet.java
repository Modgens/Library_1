package controller;

import dao.implementation.UserOrdersDaoImpl;
import entity.UserOrders;
import javax.servlet.*;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.*;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

@MultipartConfig
public class OrderProcessingServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("reading_room.jsp");
        request.setCharacterEncoding("utf-8");

        long order_id = Long.parseLong(request.getParameter("order_id"));
        UserOrdersDaoImpl userOrdersDao = new UserOrdersDaoImpl();
        UserOrders userOrders = userOrdersDao.get(order_id);

        List<UserOrders> list = (List<UserOrders>) request.getSession().getAttribute("read_room_order_list");
        if(list==null)
            list=userOrdersDao.getAllForReadingRoom();
        UserOrders listOrder = list.stream().filter(o->o.getId()==order_id).findFirst().get();

        if(userOrders.getStatus().equals("Wait for reading room")){
            userOrders.setStatus("In reading room");
            userOrders.setStatusUa("В читальному залі");
            listOrder.setStatusUa("В читальному залі");
            listOrder.setStatus("In reading room");
            userOrdersDao.update(userOrders);
        } else {
            list.remove(listOrder);
            userOrdersDao.delete(userOrders);
        }
        request.getSession().setAttribute("read_room_order_list", list);
        dispatcher.forward(request, resp);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("user_orders.jsp");
        request.setCharacterEncoding("utf-8");

        String date = request.getParameter("date");
        long order_id = Long.parseLong(request.getParameter("order_id"));
        UserOrdersDaoImpl userOrdersDao = new UserOrdersDaoImpl();
        UserOrders userOrders = userOrdersDao.get(order_id);

        List<UserOrders> list = (List<UserOrders>) request.getSession().getAttribute("sub_order_list");
        if(list==null)
            list=userOrdersDao.getAllForSub();
        UserOrders listOrder = list.stream().filter(o->o.getId()==order_id).findFirst().get();

        if(userOrders.getStatus().equals("Wait for order")){
            userOrders.setStatus("In order");
            userOrders.setStatusUa("Оформлений");
            userOrders.setOrderDate(new java.sql.Date(Calendar.getInstance().getTimeInMillis()));

            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            try {
                userOrders.setDateToReturn(new java.sql.Date(sdf.parse(date).getTime()));
            } catch (ParseException e) {
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
        }
        request.getSession().setAttribute("sub_order_list", list);
        dispatcher.forward(request,response);
    }
}
