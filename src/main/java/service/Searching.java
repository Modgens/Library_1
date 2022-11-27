package service;

import dao.implementation.PersonalInfoDaoImpl;
import dao.implementation.UserDaoImpl;
import dao.implementation.UserOrdersDaoImpl;
import entity.PersonalInfo;
import entity.User;
import entity.UserOrders;
import entity.megaEntity.MegaUser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

public class Searching {
    private static Searching instance;
    static final Logger logger = Logger.getLogger(String.valueOf(Searching.class));

    public static Searching getInstance() {
        if (instance == null)
            instance = new Searching();
        return instance;
    }

    public HashMap<String, List<UserOrders>> orderSearch(String page, UserOrdersDaoImpl userOrdersDao, UserDaoImpl userDao, PersonalInfoDaoImpl personalInfoDao, String firstName, String lastName, String login) {
        if (page == null || userOrdersDao == null || userDao == null || personalInfoDao == null || firstName == null || lastName == null || login == null)
            throw new NullPointerException();logger.warning("some param is null");
        List<UserOrders> list;
        String listName;
        if (page.equals("/reading_room.jsp")) {
            list = new ArrayList<>(userOrdersDao.getAllForReadingRoom());
            listName = "read_room_order_list";
            logger.info("page is /reading_room.jsp");
        } else if (page.equals("/user_orders.jsp")) {
            list = new ArrayList<>(userOrdersDao.getAllForSub());
            listName = "sub_order_list";
            logger.info("page is /user_orders.jsp");
        } else {
            logger.warning("strange page name!");
            throw new IllegalArgumentException();
        }
        Iterator<UserOrders> iterator = list.iterator();
        while (iterator.hasNext()) {
            UserOrders currentOrder = iterator.next();
            User user = userDao.get((currentOrder.getUserId()));
            logger.info("get user with email - " + user.getEmail());
            PersonalInfo personalInfo = personalInfoDao.get(user.getPersonId());
            logger.info("get personal info with login - " + personalInfo.getLogin());
            if (!firstName.equals("") && !personalInfo.getFirstName().equals(firstName)) {
                iterator.remove();
                logger.info("remove order with user's first name - " + personalInfo.getFirstName());
                continue;
            }
            if (!lastName.equals("") && !personalInfo.getLastName().equals(lastName)) {
                iterator.remove();
                logger.info("remove order with user's last name - " + personalInfo.getLastName());
                continue;
            }
            if (!login.equals("") && !personalInfo.getLogin().equals(login)) {
                logger.info("remove order with user's login - " + personalInfo.getLogin());
                iterator.remove();
            }
        }
        HashMap<String, List<UserOrders>> result = new HashMap<>();
        result.put(listName, list);
        return result;
    }

    public List<MegaUser> userSearch(List<MegaUser> list, String firstName, String lastName, String login) {
        if (list == null || firstName == null || lastName == null || login == null)
            throw new NullPointerException();logger.warning("some param is null");

        Iterator<MegaUser> iterator = list.iterator();
        while (iterator.hasNext()) {
            MegaUser currentUser = iterator.next();
            if (!firstName.equals("") && !currentUser.getPersonalInfo().getFirstName().equals(firstName)) {
                iterator.remove();
                logger.info("remove from list user with first name - " + currentUser.getPersonalInfo().getFirstName());
                continue;
            }
            if (!lastName.equals("") && !currentUser.getPersonalInfo().getLastName().equals(lastName)) {
                iterator.remove();
                logger.info("remove from list user with last name - " + currentUser.getPersonalInfo().getLastName());
                continue;
            }
            if (!login.equals("") && !currentUser.getPersonalInfo().getLogin().equals(login)) {
                iterator.remove();
                logger.info("remove from list user with login - " + currentUser.getPersonalInfo().getLogin());
            }
        }
        return list;
    }
}
