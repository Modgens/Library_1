package service;

import entity.megaEntity.MegaBook;
import filters.NewPersonFilter;

import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

public class BookSorting {
    private static BookSorting instance;
    static final Logger logger = Logger.getLogger(String.valueOf(BookSorting.class));

    public static BookSorting getInstance() {
        if (instance == null)
            instance = new BookSorting();
        return instance;
    }

    public List<MegaBook> sort(String genre, String sortBy, String bookName, String authorName, List<MegaBook> list) {
        if (genre == null || sortBy == null || bookName == null || authorName == null || list == null)
            throw new NullPointerException();logger.warning("some param is null");

        Iterator<MegaBook> iterator = list.iterator();
        while (iterator.hasNext()) {
            MegaBook currentBook = iterator.next();
            if ((!genre.equals("")) && currentBook.getGenre().getId() != Long.parseLong(genre)) {
                logger.info("remove book with genre - " + currentBook.getGenre().getGenreName());
                iterator.remove();
                continue;
            }
            if (!bookName.equals("") && (!currentBook.getName().equalsIgnoreCase(bookName) && !currentBook.getNameUa().equalsIgnoreCase(bookName))) {
                logger.info("remove book with name - " + currentBook.getName());
                iterator.remove();
                continue;
            }
            if (!authorName.equals("") && (!currentBook.getAuthor().getAuthorName().equalsIgnoreCase(authorName) && !currentBook.getAuthor().getAuthorNameUa().equalsIgnoreCase(authorName))) {
                iterator.remove();
                logger.info("remove book with author name - " + currentBook.getAuthor().getAuthorName());
            }
        }
        switch (sortBy) {
            case "":
                break;
            case "name":
                list.sort(Comparator.comparing(MegaBook::getName));
                logger.info("sort by name");
                break;
            case "author":
                list.sort(Comparator.comparing(a -> a.getAuthor().getAuthorName()));
                logger.info("sort by author");
                break;
            case "publisher":
                list.sort(Comparator.comparing(p -> p.getPublisher().getPublisherName()));
                logger.info("sort by publisher");
                break;
            case "date":
                list.sort(Comparator.comparing(MegaBook::getDateOfPublication));
                logger.info("sort by date of publication");
                break;
        }
        return list;
    }
}
