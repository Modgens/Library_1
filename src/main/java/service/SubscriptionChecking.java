package service;

import dao.implementation.SubscriptionsDaoImpl;
import entity.Subscriptions;

import java.util.Calendar;

public class SubscriptionChecking {
    private static SubscriptionChecking instance;

    public static SubscriptionChecking getInstance() {
        if (instance == null)
            instance = new SubscriptionChecking();
        return instance;
    }
    public void check(long userId){
        SubscriptionsDaoImpl subscriptionsDao = new SubscriptionsDaoImpl();
        Subscriptions subscriptions = subscriptionsDao.getFromUserDao(userId);
        if(subscriptions!=null && subscriptions.getEndDate().getTime() < Calendar.getInstance().getTimeInMillis()){
            subscriptionsDao.delete(subscriptions);
        }
    }
}
