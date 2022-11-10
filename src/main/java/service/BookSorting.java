package service;

import entity.megaEntity.MegaBook;

import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

public class BookSorting {
    private static BookSorting instance;

    public static BookSorting getInstance() {
        if (instance == null)
            instance = new BookSorting();
        return instance;
    }

    public List<MegaBook> sort(String genre, String sortBy, String bookName, String authorName, List<MegaBook> list) {
        if (genre == null || sortBy == null || bookName == null || authorName == null || list == null)
            throw new NullPointerException();

        Iterator<MegaBook> iterator = list.iterator();
        while (iterator.hasNext()) {
            MegaBook currentBook = iterator.next();
            if ((!genre.equals("")) && currentBook.getGenre().getId() != Long.parseLong(genre)) {
                iterator.remove();
                continue;
            }
            if (!bookName.equals("") && (!currentBook.getName().equalsIgnoreCase(bookName) && !currentBook.getNameUa().equalsIgnoreCase(bookName))) {
                iterator.remove();
                continue;
            }
            if (!authorName.equals("") && (!currentBook.getAuthor().getAuthorName().equalsIgnoreCase(authorName) && !currentBook.getAuthor().getAuthorNameUa().equalsIgnoreCase(authorName))) {
                iterator.remove();
            }
        }
        switch (sortBy) {
            case "":
                break;
            case "name":
                list.sort(Comparator.comparing(MegaBook::getName));
                break;
            case "author":
                list.sort(Comparator.comparing(a -> a.getAuthor().getAuthorName()));
                break;
            case "publisher":
                list.sort(Comparator.comparing(p -> p.getPublisher().getPublisherName()));
                break;
            case "date":
                list.sort(Comparator.comparing(MegaBook::getDateOfPublication));
                break;
        }
        return list;
    }
}
