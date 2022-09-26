package controller;

import dao.implementation.AuthorDaoImpl;
import dao.implementation.BookDaoImpl;
import dao.implementation.GenreDaoImpl;
import entity.Book;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Demo {
    public static void main(String[] args) {
        String genre = "Poems";
        String bookName = "Кобзар";
        String authorName = "Тарас Шевченко";

        BookDaoImpl bookDao = new BookDaoImpl();
        GenreDaoImpl genreDao = new GenreDaoImpl();
        AuthorDaoImpl authorDao = new AuthorDaoImpl();

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
        for (Book book :
                list) {
            System.out.println(book.getName());
        }
    }
}
