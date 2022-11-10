import entity.Author;
import entity.Genre;
import entity.Publisher;
import entity.megaEntity.MegaBook;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import service.BookSorting;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class BookSortingTest {
    private final BookSorting bookSorting = BookSorting.getInstance();
    List<MegaBook> testList;

    @Before
    public void setUp(){
        testList = new ArrayList<>();
        MegaBook book0 = new MegaBook(0L, "Kobzar", "Кобзар", new  Publisher(0L, "BlaBlaBlaStd", "BlaBlaBlaStd"), 1918, 5, "", "", "", new Author(0L, "Taras Shevchenko", "Тарас Шевченко"), new Genre(1, "", ""));
        MegaBook book1 = new MegaBook(1L, "Black Rada", "Чорна Рада", new  Publisher(0L, "Osnovy", "Основи"), 1918, 5, "", "", "", new Author(0L, "Panteleimon Kulish", "Пантелеймон Куліш"), new Genre(14, "", ""));
        MegaBook book2 = new MegaBook(2L, "Aeneid", "Енеїда", new  Publisher(0L, "Ranok", "Ранок"), 1944, 5, "", "", "", new Author(0L, "Ivan Kotliarevsky", "Іван Котляревський"), new Genre(2, "", ""));
        MegaBook book3 = new MegaBook(3L, "Some Book", "Якась книга", new  Publisher(0L, "Dnipro", "Дніпро"), 2009, 5, "", "", "", new Author(0L, "Somebody", "Хтось"), new Genre(2, "", ""));
        MegaBook book4 = new MegaBook(4L, "A story of past years", "Повість минулих літ", new  Publisher(0L, "Folio", "Фоліо"), 2022, 5, "", "", "", new Author(0L, "Nestor Litopysetsʹ", "Нестор Літописець"), new Genre(7, "", ""));
        MegaBook book5 = new MegaBook(4L, "A story of past years", "Повість минулих літ", new  Publisher(0L, "Folio", "Фоліо"), 2022, 5, "", "", "", new Author(0L, "Taras Shevchenko", "Тарас Шевченко"), new Genre(9, "", ""));

        testList.add(book0);
        testList.add(book1);
        testList.add(book2);
        testList.add(book3);
        testList.add(book4);
        testList.add(book5);
    }
    @Test
    public void testBookSortingNull(){
        boolean isThrow = false;
        try {
            bookSorting.sort(null,null,null,null, null);
        } catch (NullPointerException e){
            isThrow = true;
        }
        Assert.assertTrue(isThrow);
    }
    @Test
    public void testBookSortingEmptyArg(){
        List<MegaBook> sortedList = bookSorting.sort("","","","", new ArrayList<>(testList));
        Assert.assertEquals(sortedList, testList);
    }
    @Test
    public void testBookSortingGenre2(){
        List<MegaBook> sortedList = bookSorting.sort("2","","","", new ArrayList<>(testList));
        Assert.assertEquals(2, sortedList.size());
    }
    @Test
    public void testBookSortingGenre1(){
        List<MegaBook> sortedList = bookSorting.sort("1","","","", new ArrayList<>(testList));
        Assert.assertEquals(1, sortedList.size());
    }
    @Test
    public void testBookSortingGenre14(){
        List<MegaBook> sortedList = bookSorting.sort("14","","","", new ArrayList<>(testList));
        Assert.assertEquals(1, sortedList.size());
        Assert.assertEquals("Black Rada", sortedList.get(0).getName());
    }
    @Test
    public void testBookSortingSortByAuthor(){
        List<MegaBook> sortedList = bookSorting.sort("","author","","", new ArrayList<>(testList));
        testList.sort(Comparator.comparing(a->a.getAuthor().getAuthorName()));
        Assert.assertEquals(testList, sortedList);
    }
    @Test
    public void testBookSortingSortByTitle(){
        List<MegaBook> sortedList = bookSorting.sort("","name","","", new ArrayList<>(testList));
        testList.sort(Comparator.comparing(MegaBook::getName));
        Assert.assertEquals(testList, sortedList);
    }
    @Test
    public void testBookSortingTitleSearch(){
        List<MegaBook> sortedList = bookSorting.sort("","","Kobzar","", new ArrayList<>(testList));
        Assert.assertEquals("Kobzar", sortedList.get(0).getName());
        Assert.assertEquals(1, sortedList.size());
    }
    @Test
    public void testBookSortingAuthorSearch(){
        List<MegaBook> sortedList = bookSorting.sort("","","","Taras Shevchenko", new ArrayList<>(testList));
        Assert.assertEquals("Taras Shevchenko", sortedList.get(0).getAuthor().getAuthorName());
        Assert.assertEquals("Taras Shevchenko", sortedList.get(1).getAuthor().getAuthorName());
        Assert.assertEquals(2, sortedList.size());
    }
    @Test
    public void testBookSortingAuthorSearchAndSortByTitle(){
        List<MegaBook> sortedList = bookSorting.sort("","name","","Taras Shevchenko", new ArrayList<>(testList));
        Assert.assertEquals("Taras Shevchenko", sortedList.get(0).getAuthor().getAuthorName());
        Assert.assertEquals("Taras Shevchenko", sortedList.get(1).getAuthor().getAuthorName());
        Assert.assertEquals("A story of past years", sortedList.get(0).getName());
        Assert.assertEquals("Kobzar", sortedList.get(1).getName());
        Assert.assertEquals(2, sortedList.size());
    }
}
