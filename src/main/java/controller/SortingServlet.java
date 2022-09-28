package controller;

import dao.implementation.AuthorDaoImpl;
import dao.implementation.BookDaoImpl;
import dao.implementation.GenreDaoImpl;
import dao.implementation.PublisherDaoImpl;
import entity.Book;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.*;


public class SortingServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        String genre = request.getParameter("genre");
        String sortBy = request.getParameter("sort");
        String bookName = request.getParameter("book");
        String authorName = request.getParameter("author");

        BookDaoImpl bookDao = new BookDaoImpl();
        GenreDaoImpl genreDao = new GenreDaoImpl();
        AuthorDaoImpl authorDao = new AuthorDaoImpl();
        PublisherDaoImpl publisherDao = new PublisherDaoImpl();

        List<Book> list = new ArrayList<>(bookDao.getAll());
        Iterator<Book> iterator = list.iterator();

        while (iterator.hasNext()) {
            Book currentBook = iterator.next();
            if(!genre.equals("Genre")&&!genreDao.get(currentBook.getGenreId()).getGenreName().equals(genre)){
                iterator.remove();
                continue;
            }
            if(!bookName.equals("")&&!currentBook.getName().equalsIgnoreCase(bookName)){
                iterator.remove();
                continue;
            }
            if(!authorName.equals("")&&!authorDao.get(currentBook.getAuthorId()).getAuthorName().equalsIgnoreCase(authorName)){
                iterator.remove();
            }
        }
        System.out.println(bookName);
        System.out.println(list);
        switch (sortBy){
            case "Sorted by":
                break;
            case "name":
                list.sort(Comparator.comparing(Book::getName));
                break;
            case "author":
                list.sort(Comparator.comparing(a -> authorDao.get(a.getAuthorId()).getAuthorName()));
                break;
            case "publisher":
                list.sort(Comparator.comparing(p -> publisherDao.get(p.getPublicationId()).getPublisherName()));
                break;
            case "date":
                list.sort(Comparator.comparing(Book::getDateOfPublication));
                break;
        }
        request.setAttribute("list", list);
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(request.getParameter("page"));
        dispatcher.forward(request,response);
    }
}
