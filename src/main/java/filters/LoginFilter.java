package filters;

import dao.implementation.AdminDaoImpl;
import dao.implementation.LibrarianDaoImpl;
import dao.implementation.PersonalInfoDaoImpl;
import dao.implementation.UserDaoImpl;
import service.Validator;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.logging.Logger;

public class LoginFilter implements Filter {

    private FilterConfig config;
    static final Logger logger = Logger.getLogger(String.valueOf(LoginFilter.class));

    public void init(FilterConfig config) {
        this.config = config;
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        String login = request.getParameter("login");
        logger.info("get param login with value: " + login);
        String password = request.getParameter("password");
        logger.info("get param password with value: " + password);

        HashMap<String, String> map = Validator.getInstance().loginValidation(login, password, new PersonalInfoDaoImpl(), new UserDaoImpl(), new AdminDaoImpl(), new LibrarianDaoImpl());
        logger.info("call methode loginValidation() from Validator class");

        final String STATUS = "status";
        final String NAME = "name";
        final String ERROR = "error";
        final String ROLE = "role";
        final String USER_ID = "user_id";

        request.setAttribute(STATUS, map.get(STATUS));
        request.setAttribute(NAME, map.get(NAME));
        request.setAttribute(ERROR, map.get(ERROR));
        request.setAttribute(ROLE, map.get(ROLE));
        logger.info("set attribute status with value : " + map.get(STATUS));
        logger.info("set attribute name with value : " + map.get(NAME));
        logger.info("set attribute error with value : " + map.get(ERROR));
        logger.info("set attribute role with value : " + map.get(ROLE));
        if (map.get(USER_ID) != null)
            request.setAttribute(USER_ID, Long.parseLong(map.get(USER_ID)));

        chain.doFilter(request, response);
    }
}
