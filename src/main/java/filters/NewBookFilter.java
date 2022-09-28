package filters;

import dao.implementation.*;
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

public class NewBookFilter implements Filter {
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

        //Entity
        Book bookEntity = new Book();
        Genre genreEntity = new Genre();
        Author authorEntity = null;
        Publisher publisherEntity = null;

        //dao
        BookDaoImpl bookDao = new BookDaoImpl();
        AuthorDaoImpl authorDao = new AuthorDaoImpl();
        PublisherDaoImpl publisherDao = new PublisherDaoImpl();
        GenreDaoImpl genreDao = new GenreDaoImpl();

        //regex
        final String BOOK_REGEX = "[^%{}<>]+";
        final String IMG_REGEX = "([^\\s]+(\\.(?i)(png|jpg|gif|bmp|jpeg|PNG|JPG|GIF|BMP|JPEG)$))";
        final String AUTHOR_REGEX = "[а-яА-Яa-zA-Z]+";

        request.setAttribute("error","");

        //title validation
        String title = request.getParameter("title");
        if(title.matches(BOOK_REGEX)){
            bookEntity.setName(title);
        } else {
            request.setAttribute("error", "Incorrect book title!");
            chain.doFilter(request,response);
            return;
        }

        //set genre
        genreEntity.setGenreName(request.getParameter("genre"));

        //year validation
        int year=0;
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
        long authorId = authorDao.idFromName(author);
        if(authorId!=0){
            bookEntity.setAuthorId(authorId);
        } else if(author.matches(AUTHOR_REGEX)){
            authorEntity = new Author();
            authorEntity.setAuthorName(author);
        } else {
            request.setAttribute("error", "Incorrect author name!");
            chain.doFilter(request, response);
            return;
        }

        //public validation

        String publisher = request.getParameter("publisher");
        long publisherId = publisherDao.idFromName(publisher);

        if(publisherId!=0){
            bookEntity.setPublicationId(publisherId);
        } else if(publisher.matches(AUTHOR_REGEX)){
            publisherEntity = new Publisher();
            publisherEntity.setPublisherName(publisher);
        } else {
            request.setAttribute("error", "Incorrect publisher name!");
            chain.doFilter(request, response);
            return;
        }

        //count validation
        int count=0;
        try{
            count = Integer.parseInt(request.getParameter("count"));
        }catch (Exception e){
            request.setAttribute("error", "In count should be only numbers!");
            chain.doFilter(request, response);
            return;
        }
        if(count<=1000){
            bookEntity.setCount(count);
        }else {
            request.setAttribute("error", "Count can't be bigger then 1000");
            chain.doFilter(request, response);
            return;
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
                chain.doFilter(request, response);
                return;
            }
            InputStream fileContent = filePart.getInputStream();
            File file = new File("C:/Users/Modgen/IdeaProjects/Library_1/src/main/webapp/book_images/"+fileName);
            BufferedImage imBuff = ImageIO.read(fileContent);
            ImageIO.write(imBuff, FilenameUtils.getExtension(fileName), file);
        }
        bookEntity.setImgName(fileName);

        //final set
        if(authorEntity!=null){
            authorDao.insert(authorEntity);
            bookEntity.setAuthorId(authorDao.idFromName(authorEntity.getAuthorName()));
        }
        if(publisherEntity!=null){
            publisherDao.insert(publisherEntity);
            bookEntity.setPublicationId(publisherDao.idFromName(publisherEntity.getPublisherName()));
        }
        bookEntity.setGenreId(genreDao.idFromName(genreEntity.getGenreName()));
        bookDao.insert(bookEntity);

        chain.doFilter(request,response);
    }
}
