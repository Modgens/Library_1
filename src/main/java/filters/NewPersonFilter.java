package filters;

import dao.implementation.PersonalInfoDaoImpl;
import entity.PersonalInfo;
import service.Validator;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class NewPersonFilter implements Filter {
    private FilterConfig config;

    public void init(FilterConfig config) throws ServletException {
        this.config=config;
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;

        request.setCharacterEncoding("UTF-8");
        //Param
        String fName = request.getParameter("first_name");
        String lName = request.getParameter("last_name");
        String login = request.getParameter("login");
        String password = request.getParameter("password");
        String email = request.getParameter("email");

        //Entity
        PersonalInfo personalInfoEntity = new PersonalInfo();

        //dao
        PersonalInfoDaoImpl personalInfoDao = new PersonalInfoDaoImpl();

        String error = Validator.getInstance().newPersonValidate(personalInfoDao, personalInfoEntity, fName, lName, login, password);

        request.setAttribute("error", error);
        if (error.equals("")) {
            if (email != null) {
                request.setAttribute("email", email);
            }
            request.setAttribute("person", personalInfoEntity);
        }
        chain.doFilter(request, response);
    }
}
