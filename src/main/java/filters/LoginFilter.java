package filters;

import dao.implementation.AdminDaoImpl;
import dao.implementation.LibrarianDaoImpl;
import dao.implementation.PersonalInfoDaoImpl;
import dao.implementation.UserDaoImpl;
import entity.PersonalInfo;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LoginFilter implements Filter {

    private FilterConfig config;

    public void init(FilterConfig config) throws ServletException {
        this.config=config;
        System.out.println("login-filter");
    }

    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        String login = request.getParameter("login");
        String password = request.getParameter("password");

        PersonalInfoDaoImpl personalInfoDao = new PersonalInfoDaoImpl();
        UserDaoImpl userDao = new UserDaoImpl();
        AdminDaoImpl adminDao = new AdminDaoImpl();
        LibrarianDaoImpl librarianDao = new LibrarianDaoImpl();

        PersonalInfo personalInfo = personalInfoDao.findByLoginAndPassword(login, password);
        long infoId = personalInfo.getId();
        if(infoId!=0){//if id==0   =>   there is empty entity
            if(userDao.hasInfoId(infoId)){
                if(userDao.getStatusFromPersonInfoID(infoId).equals("baned")){
                    request.setAttribute("error", "You are baned! | Ви забанені!");
                    request.setAttribute("status", "failed");
                    chain.doFilter(request, response);
                    return;
                }
                request.setAttribute("user_id", userDao.getIdFromPersonInfoId(infoId));
                request.setAttribute("role", "user");
            }
            if(librarianDao.hasInfoId(infoId)){
                request.setAttribute("role", "librarian");
            }
            if(adminDao.hasInfoId(infoId)){
                request.setAttribute("role", "admin");
            }
            request.setAttribute("status", "success");
            request.setAttribute("name", personalInfo.getFirstName() + " " + personalInfo.getLastName());
        }else{
            request.setAttribute("status", "failed");
            request.setAttribute("error", "Wrong Username or Password | Не правильний логін або пароль");
        }
        chain.doFilter(request, response);
    }
}
