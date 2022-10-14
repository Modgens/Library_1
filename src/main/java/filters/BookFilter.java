package filters;

import dao.implementation.*;
import entity.*;
import org.apache.commons.io.FilenameUtils;

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

public class BookFilter implements Filter {
    private FilterConfig config;

    public void init(FilterConfig config) throws ServletException {
        this.config=config;
        System.out.println("filter-new-book");
    }

    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;

        request.setCharacterEncoding("UTF-8");

        //DAO
        BookDaoImpl bookDao = new BookDaoImpl();
        AuthorDaoImpl authorDao = new AuthorDaoImpl();
        PublisherDaoImpl publisherDao = new PublisherDaoImpl();
        //Entity
        String book_id = request.getParameter("book_id");
        Book bookEntity = book_id==null ? new Book() : bookDao.get(Long.parseLong(book_id));

        //regex
        final String SIMPLE_REGEX = "[^%{}<>]+";
        final String COUNT_REGEX = "\\d+";
        final String IMG_REGEX = "(\\S+(\\.(?i)(png|jpg|gif|bmp|jpeg|PNG|JPG|GIF|BMP|JPEG)$))";

        request.setAttribute("error","");

        //title validation
        String title = request.getParameter("title");
        String titleUa = request.getParameter("titleUa");
        if(title.matches(SIMPLE_REGEX)&&titleUa.matches(SIMPLE_REGEX)){
            bookEntity.setName(title);
            bookEntity.setNameUa(titleUa);
        }else{
            request.setAttribute("error", "Incorrect book title!");
            chain.doFilter(request,response);
            return;
        }

        //year validation
        int year;
        try {
            year = Integer.parseInt(request.getParameter("year"));
        } catch (Exception e){
            request.setAttribute("error", "Incorrect year input!");
            chain.doFilter(request,response);
            return;
        }
        Calendar calendar = new GregorianCalendar();
        int current_Year=calendar.get(Calendar.YEAR);
        if(year>=1800&&year<=current_Year){
            bookEntity.setDateOfPublication(year);
        } else {
            request.setAttribute("error", "Year must be between 1800 year - now !");
            chain.doFilter(request, response);
            return;
        }

        //author validation
        String author=request.getParameter("author");
        String authorUa=request.getParameter("authorUa");

        if (author.matches(SIMPLE_REGEX)&&authorUa.matches(SIMPLE_REGEX)){
            if(authorDao.idFromName(author, authorUa)==0L){
                Author author1 = new Author();
                author1.setAuthorName(author);
                author1.setAuthorNameUa(authorUa);
                authorDao.insert(author1);
                bookEntity.setAuthorId(authorDao.idFromName(author, authorUa));
            } else {
                bookEntity.setAuthorId(authorDao.idFromName(author, authorUa));
            }
        }else{
            request.setAttribute("error", "Incorrect author name!");
            chain.doFilter(request,response);
            return;
        }

        //publisher validation
        String publisher=request.getParameter("publisher");
        String publisherUa=request.getParameter("publisherUa");

        if (publisher.matches(SIMPLE_REGEX)&&publisherUa.matches(SIMPLE_REGEX)){
            if(publisherDao.idFromName(publisher, publisherUa)==0L){
                Publisher publisher1 = new Publisher();
                publisher1.setPublisherName(publisher);
                publisher1.setPublisherNameUa(publisherUa);
                publisherDao.insert(publisher1);
                bookEntity.setPublicationId(publisherDao.idFromName(publisher, publisherUa));
            } else {
                bookEntity.setPublicationId(publisherDao.idFromName(publisher, publisherUa));
            }
        }else{
            request.setAttribute("error", "Incorrect publisher name!");
            chain.doFilter(request,response);
            return;
        }

        //count validation
        int count=0;
        try{
            count = Integer.parseInt(request.getParameter("count"));
        }catch (Exception e){
            request.setAttribute("error", "In count should be only numbers!");
            chain.doFilter(request,response);
            return;
        }
        if((""+count).matches(COUNT_REGEX)){
            bookEntity.setCount(count);
        }else {
            request.setAttribute("error", "In count should be only numbers!");
            chain.doFilter(request,response);
            return;
        }

        //set description
        bookEntity.setDescription(request.getParameter("description"));
        bookEntity.setDescriptionUa(request.getParameter("descriptionUa"));

        //work with img
        String fileName;
        Part filePart = request.getPart("img");
        fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
        if(!fileName.equals("")) {
            if (fileName.matches(IMG_REGEX)) {
                bookEntity.setImgName(fileName);
            } else {
                request.setAttribute("error", "Incorrect image type!");
                chain.doFilter(request, response);
                return;
            }
            InputStream fileContent = filePart.getInputStream();
            File file = new File("C:/Users/Modgen/IdeaProjects/Library_1/src/main/webapp/book_images/" + fileName);
            BufferedImage imBuff = ImageIO.read(fileContent);
            ImageIO.write(imBuff, FilenameUtils.getExtension(fileName), file);
            bookEntity.setImgName(fileName);
        } else if(book_id==null){
            fileName = "book0.jpg";
            bookEntity.setImgName(fileName);
        }


        //final set
        bookEntity.setGenreId(Long.parseLong(request.getParameter("genre")));
        if(book_id==null){
            bookDao.insert(bookEntity);
            request.setAttribute("path", "new_book.jsp");
        } else {
            bookDao.update(bookEntity);
            request.setAttribute("path", "change_book.jsp");
        }
        chain.doFilter(request,response);
    }
}
