package controller;

import dao.megaEntity.MegaUserDaoImpl;
import entity.megaEntity.MegaUser;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

public class UserBanServlet extends HttpServlet {
    static final Logger logger = Logger.getLogger(String.valueOf(UserBanServlet.class));

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("users.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        long user_id = Long.parseLong(request.getParameter("user_id"));
        logger.info("get user id - " + user_id);
        List<MegaUser> list = (List<MegaUser>) request.getSession().getAttribute("user_list");
        logger.info("get userList from session with size - " + ((list != null) ? list.size() : "list is null"));
        if (list == null)
            list = MegaUserDaoImpl.getInstance().getAll();
        MegaUser user = MegaUserDaoImpl.getInstance().get(user_id);
        logger.info("get MegaUser with id - " + user.getId() + " and login - " + user.getPersonalInfo().getLogin());
        MegaUser listUser = list.stream().filter(u -> u.getId() == user_id).findFirst().get();
        if (user.getStatus().equals("not baned")) {
            user.setStatus("baned");
            listUser.setStatus("baned");
            logger.info("user is baned now");
        } else {
            user.setStatus("not baned");
            listUser.setStatus("not baned");
            logger.info("user is not baned more");
        }
        MegaUserDaoImpl.update(user);
        logger.info("megaUser updated in db");
        request.getSession().setAttribute("user_list", list);
        response.sendRedirect("users.jsp");
    }
}
