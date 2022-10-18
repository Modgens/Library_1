package util;

import dao.implementation.UserOrdersDaoImpl;
import entity.UserOrders;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class Punisher {
    private static Punisher instance;
    UserOrdersDaoImpl userOrdersDao = new UserOrdersDaoImpl();


    public static Punisher getInstance() {
        if(instance == null)
            instance = new Punisher();
        return instance;
    }
    public int usersFineSum(long userId){
        List<UserOrders> ordersList = userOrdersDao.getAllByUserId(userId);
        int sum=0;
        for (UserOrders order : ordersList) {
            if(order.getDateToReturn()!=null)
                sum+=(order.getDateToReturn().getTime() - Calendar.getInstance().getTimeInMillis() < 0 ? 200 : 0); //+ 200 - грн штраф
        }
        return sum;
    }

    public int getByOrderId(long orderId){
        UserOrders order = userOrdersDao.get(orderId);
        if(order.getDateToReturn()==null)
            return 0;
        return order.getDateToReturn().getTime() - Calendar.getInstance().getTimeInMillis() < 0 ? 200 : 0;
    }
}
