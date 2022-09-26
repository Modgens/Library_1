package controller;

import dao.implementation.AuthorDaoImpl;
import dao.implementation.BookDaoImpl;
import dao.implementation.GenreDaoImpl;
import dao.implementation.PublisherDaoImpl;
import entity.Author;
import entity.Book;
import entity.Genre;
import entity.Publisher;

import javax.imageio.ImageIO;
import javax.servlet.*;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.util.*;

@MultipartConfig
public class CreateBookServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        RequestDispatcher dispatcher = request.getRequestDispatcher("new_book.jsp");
        if(request.getAttribute("error").equals("")){
            request.setAttribute("status", "success");

            Book book = (Book)request.getAttribute("book");
            Genre genre = (Genre)request.getAttribute("genre");
            Author author = (Author) request.getAttribute("author");
            Publisher publisher = (Publisher) request.getAttribute("publisher");

            BookDaoImpl bookDao = new BookDaoImpl();
            AuthorDaoImpl authorDao = new AuthorDaoImpl();
            GenreDaoImpl genreDao = new GenreDaoImpl();
            PublisherDaoImpl publisherDao = new PublisherDaoImpl();

            book.setGenreId(genreDao.idFromName(genre.getGenreName()));
            book.setAuthorId(authorDao.idFromName(author.getAuthorName()));
            book.setPublicationId(publisherDao.idFromName(publisher.getPublisherName()));

            bookDao.insert(book);

        }else {
            request.setAttribute("status", "failed");
        }
        dispatcher.forward(request,response);
    }
}
