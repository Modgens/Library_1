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

public class ChangeBookFilter implements Filter {
    private FilterConfig config;

    public void init(FilterConfig config) throws ServletException {
        this.config=config;
        System.out.println("filter-change");
    }

    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;

        request.setCharacterEncoding("UTF-8");

        //Entity
        Book bookEntity = new BookDaoImpl().get(Long.parseLong(request.getParameter("book_id")));
        Author authorEntity = null;
        Publisher publisherEntity = null;

        //regex
        final String BOOK_REGEX = "[^\\s]+";
        final String YEAR_REGEX = "\\d{4}";
        final String COUNT_REGEX = "\\d+";
        final String IMG_REGEX = "([^\\s]+(\\.(?i)(png|jpg|gif|bmp|jpeg|PNG|JPG|GIF|BMP|JPEG)$))";
        final String AUTHOR_REGEX = "[а-яА-Яa-zA-Z\\-]+";

        request.setAttribute("error","");

        //title validation
        String title = request.getParameter("title");
        if(!title.equals("")){
            if(title.matches(BOOK_REGEX)){
                bookEntity.setName(title);
            }else{
                request.setAttribute("error", "Incorrect book title!");
            }
        }

        //set genre
        if(!request.getParameter("genre").equals("")){
            bookEntity.setGenreId(new GenreDaoImpl().idFromName(request.getParameter("genre")));
        }

        //year validation
        int year;
        if(!request.getParameter("year").equals("")){
            try {
                year = Integer.parseInt(request.getParameter("year"));
                if((""+year).matches(YEAR_REGEX)){
                    bookEntity.setDateOfPublication(year);
                }else {
                    request.setAttribute("error", "Year must have four number!");
                }
            }catch (Exception e){
                request.setAttribute("error", "Incorrect year input!");
            }

        }

        //author validation
        String author=request.getParameter("newAuthor");

        if(!author.equals("")) {
            AuthorDaoImpl authorDao = new AuthorDaoImpl();
            if (author.matches(AUTHOR_REGEX)) {
                authorEntity = new Author();
                authorEntity.setAuthorName(author);
                authorDao.insert(authorEntity);
                bookEntity.setAuthorId(authorDao.idFromName(author));
            } else {
                author = request.getParameter("author");
                if (!author.equals("")) {
                    if (author.matches(AUTHOR_REGEX)) {
                        bookEntity.setAuthorId(authorDao.idFromName(author));
                    }
                }
            }
        }
        //public validation
        String publisher = request.getParameter("newPublisher");
        if(!publisher.equals("")) {
            PublisherDaoImpl publisherDao = new PublisherDaoImpl();
            if (publisher.matches(AUTHOR_REGEX)) {
                publisherEntity = new Publisher();
                publisherEntity.setPublisherName(publisher);
                publisherDao.insert(publisherEntity);
                bookEntity.setPublicationId(publisherDao.idFromName(publisher));
            } else {
                publisher = request.getParameter("publisher");
                if (!publisher.equals("")) {
                    if (publisher.matches(AUTHOR_REGEX)) {
                        bookEntity.setPublicationId(publisherDao.idFromName(publisher));
                    }
                }
            }
        }

        //count validation
        if(!request.getParameter("count").equals("")){
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
        }


        //set description
        if(!request.getParameter("description").equals("")){
            bookEntity.setDescription(request.getParameter("description"));
        }

        //work with img
        Part filePart = request.getPart("img");
        String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
        if(!fileName.equals("")&&request.getAttribute("error").equals("")){
            if(fileName.matches(IMG_REGEX)){
                bookEntity.setImgName(fileName);
            }else{
                request.setAttribute("error", "Incorrect image type!");
            }
            InputStream fileContent = filePart.getInputStream();
            File file = new File("C:/Users/Modgen/IdeaProjects/Library_1/src/main/webapp/book_images/"+fileName);
            BufferedImage imBuff = ImageIO.read(fileContent);
            System.out.println(imBuff);
            ImageIO.write(imBuff, "png", file);
            bookEntity.setImgName(fileName);
        }

        //final set
        request.setAttribute("book", bookEntity);
        chain.doFilter(request,response);
    }
}
