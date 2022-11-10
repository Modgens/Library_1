package filters;

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

public class BookFilter implements Filter {
    private FilterConfig config;

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
        request.setAttribute("error", result.get("error"));
        request.setAttribute("path", result.get("path"));

        chain.doFilter(request, response);
    }
}
