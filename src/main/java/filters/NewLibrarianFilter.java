package filters;

import dao.implementation.*;
import dao.transaction.CreateLibrarianTransaction;
import entity.*;
import javax.imageio.ImageIO;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.util.Calendar;
import java.util.GregorianCalendar;
import org.apache.commons.io.FilenameUtils;

public class NewLibrarianFilter implements Filter {
    private FilterConfig config;

    public void init(FilterConfig config) throws ServletException {
        this.config=config;
        System.out.println("filter-new-librarian");
    }

    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;

        request.setCharacterEncoding("UTF-8");

        //Entity
        PersonalInfo personalInfoEntity = new PersonalInfo();

        //dao
        PersonalInfoDaoImpl personalInfoDao = new PersonalInfoDaoImpl();
        CreateLibrarianTransaction librarianTransaction = new CreateLibrarianTransaction();

        //regex
        final String NAME_REGEX = "[а-яА-Яa-zA-Z]+";
        final String PASSWORD_REGEX = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{8,20}$";

        request.setAttribute("error","");

        //f_name validation
        String fName = request.getParameter("first_name");
        if(fName.matches(NAME_REGEX)){
            personalInfoEntity.setFirstName(fName);
        } else {
            request.setAttribute("error", "Incorrect first name");
            chain.doFilter(request, response);
            return;
        }

        //l_name validation
        String lName = request.getParameter("last_name");
        if(lName.matches(NAME_REGEX)){
            personalInfoEntity.setLastName(lName);
        } else {
            request.setAttribute("error", "Incorrect last name");
            chain.doFilter(request, response);
            return;
        }

        //login validation
        String login = request.getParameter("login");
        if(personalInfoDao.getId(login)==0){
            personalInfoEntity.setLogin(login);
        } else {
            request.setAttribute("error", "This login is already in used");
            chain.doFilter(request, response);
            return;
        }
        //password validation
        String password = request.getParameter("password");
        if(password.matches(PASSWORD_REGEX)){
            personalInfoEntity.setPassword(password);
        } else {
            request.setAttribute("error", "Password must have at least one numeric character, one lowercase character, one uppercase character, one special symbol among @#$% and have length between 8 and 20");
            chain.doFilter(request, response);
            return;
        }
        //final set
        librarianTransaction.create(personalInfoEntity);

        chain.doFilter(request,response);
    }
}
