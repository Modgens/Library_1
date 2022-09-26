package filters;

import dao.implementation.AdminDaoImpl;
import dao.implementation.LibrarianDaoImpl;
import dao.implementation.PersonalInfoDaoImpl;
import dao.implementation.UserDaoImpl;
import entity.*;

import javax.imageio.ImageIO;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.nio.file.Paths;

public class NewBookFilter implements Filter {
    private FilterConfig config;

    public void init(FilterConfig config) throws ServletException {
        this.config=config;
        System.out.println("filter");
    }

    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;

        request.setCharacterEncoding("UTF-8");

        //Entity
        Book bookEntity = new Book();
        Genre genreEntity = new Genre();
        Author authorEntity = new Author();
        Publisher publisherEntity = new Publisher();

        //regex
        final String BOOK_REGEX = "[а-яА-Яa-zA-Z0-9!?.,\\-—() ]+";
        final String YEAR_REGEX = "\\d{4}";
        final String COUNT_REGEX = "\\d+";
        final String IMG_REGEX = "([^\\s]+(\\.(?i)(png|jpg|gif|bmp|jpeg|PNG|JPG|GIF|BMP|JPEG)$))";
        final String AUTHOR_REGEX = "[а-яА-Яa-zA-Z]+";

        request.setAttribute("error","");

        //title validation
        String title = request.getParameter("title");
        if(title.matches(BOOK_REGEX)){
            bookEntity.setName(title);
        }else{
            request.setAttribute("error", "Incorrect book title!");
        }

        //set genre
        genreEntity.setGenreName(request.getParameter("genre"));

        //year validation
        int year=0;
        try {
            year = Integer.parseInt(request.getParameter("year"));
        }catch (Exception e){
            request.setAttribute("error", "Incorrect year input!");
        }
        if((""+year).matches(YEAR_REGEX)){
            bookEntity.setDateOfPublication(year);
        }else {
            request.setAttribute("error", "Year must have four number!");
        }

        //author validation
        String author=request.getParameter("newAuthor");
        if(author.equals("")){
            author=request.getParameter("author");
        }else if (!author.matches(AUTHOR_REGEX)){
            request.setAttribute("error", "Incorrect author name!");
        }
        String publisher = request.getParameter("newPublisher");
        if(publisher.equals("")){
            publisher=request.getParameter("publisher");
        }
        if(publisher.equals("")||author.equals("")){
            request.setAttribute("error", "You should enter author or publisher!");
        }else{
            publisherEntity.setPublisherName(publisher);
            authorEntity.setAuthorName(author);
        }

        //count validation
        int count=0;
        try{
            count = Integer.parseInt(request.getParameter("count"));
        }catch (Exception e){
            request.setAttribute("error", "In count should be only numbers!");
        }
        if((""+count).matches(COUNT_REGEX)){
            bookEntity.setCount(count);
        }else {
            request.setAttribute("error", "In count should be only numbers!");
        }

        //set description
        bookEntity.setDescription(request.getParameter("description"));

        //work with img
        Part filePart = request.getPart("img");
        String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
        if(!fileName.equals("")){
            if(fileName.matches(IMG_REGEX)){
                bookEntity.setImgName(fileName);
            }else{
                request.setAttribute("error", "Incorrect image type!");
            }
            InputStream fileContent = filePart.getInputStream();
            Image image = ImageIO.read(fileContent);
            File file = new File("C:/Users/Modgen/IdeaProjects/Library_1/src/main/webapp/book_images/"+fileName);
            if(!file.createNewFile()){
                request.setAttribute("error", "The named file already exists");
            }
        }
        bookEntity.setImgName(fileName);

        //final set
        request.setAttribute("book", bookEntity);
        request.setAttribute("genre", genreEntity);
        request.setAttribute("author", authorEntity);
        request.setAttribute("publisher", publisherEntity);

        chain.doFilter(request,response);
    }
}
