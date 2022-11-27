package filters;

import controller.UserSearchingServlet;
import dao.implementation.AuthorDaoImpl;
import dao.implementation.BookDaoImpl;
import dao.implementation.PublisherDaoImpl;
import service.Validator;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.util.HashMap;
import java.util.logging.Logger;

public class BookFilter implements Filter {
    private FilterConfig config;
    static final Logger logger = Logger.getLogger(String.valueOf(BookFilter.class));

    public void init(FilterConfig config) throws ServletException {
        this.config=config;
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;

        request.setCharacterEncoding("UTF-8");

        //GetParam
        String book_id = request.getParameter("book_id");
        String title = request.getParameter("title");
        String titleUa = request.getParameter("titleUa");
        String yearStr = request.getParameter("year");
        String author = request.getParameter("author");
        String authorUa = request.getParameter("authorUa");
        String publisher = request.getParameter("publisher");
        String publisherUa = request.getParameter("publisherUa");
        String genre = request.getParameter("genre");
        String countStr = request.getParameter("count");
        String description = request.getParameter("description");
        String descriptionUa = request.getParameter("descriptionUa");
        logger.info("get param book_id with value: " + book_id);
        logger.info("get param title with value: " + title);
        logger.info("get param titleUa with value: " + titleUa);
        logger.info("get param yearStr with value: " + yearStr);
        logger.info("get param author with value: " + author);
        logger.info("get param authorUa with value: " + authorUa);
        logger.info("get param publisher with value: " + publisher);
        logger.info("get param publisherUa with value: " + publisherUa);
        logger.info("get param genre with value: " + genre);
        logger.info("get param countStr with value: " + countStr);
        logger.info("get param description with value: " + description);
        logger.info("get param descriptionUa with value: " + descriptionUa);

        Part filePart = request.getPart("img");

        //DAO
        BookDaoImpl bookDao = new BookDaoImpl();
        AuthorDaoImpl authorDao = new AuthorDaoImpl();
        PublisherDaoImpl publisherDao = new PublisherDaoImpl();

        HashMap<String, String> result = Validator.getInstance().bookValidation(
                filePart,
                bookDao,
                authorDao,
                publisherDao,
                book_id,
                title,
                titleUa,
                yearStr,
                author,
                authorUa,
                publisher,
                publisherUa,
                genre,
                countStr,
                description,
                descriptionUa
        );
        logger.info("call methode bookValidation() from Validator class");
        request.setAttribute("error", result.get("error"));
        logger.info("set Attribute error with value : " + result.get("error"));
        request.setAttribute("path", result.get("path"));
        logger.info("set Attribute path with value : " + result.get("path"));

        chain.doFilter(request, response);
    }
}
