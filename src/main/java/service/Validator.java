package service;

import dao.implementation.*;
import entity.*;
import org.apache.commons.io.FilenameUtils;

import javax.imageio.ImageIO;
import javax.servlet.http.Part;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.logging.Logger;

public class Validator {
    private static Validator instance;
    static final Logger logger = Logger.getLogger(String.valueOf(Validator.class));

    public static Validator getInstance() {
        if (instance == null)
            instance = new Validator();
        return instance;
    }

    public HashMap<String, String> loginValidation(String login, String password, PersonalInfoDaoImpl personalInfoDao, UserDaoImpl userDao, AdminDaoImpl adminDao, LibrarianDaoImpl librarianDao) {
        final String STATUS = "status";
        final String NAME = "name";
        final String ERROR = "error";
        final String ROLE = "role";
        final String USER_ID = "user_id";

        HashMap<String, String> map = new HashMap<>();
        map.put(STATUS, null);
        map.put(NAME, null);
        map.put(ERROR, null);
        map.put(ROLE, null);
        map.put(USER_ID, null);

        PersonalInfo personalInfo = personalInfoDao.findByLoginAndPassword(login, password);
        long infoId = personalInfo.getId();
        if (infoId != 0) {//if id==0   =>   there is empty entity
            if (userDao.hasInfoId(infoId)) {
                if (userDao.getStatusFromPersonInfoID(infoId).equals("baned")) {
                    map.replace(ERROR, "You are baned!</br>Ви забанені!");
                    map.replace(STATUS, "failed");
                    return map;
                }
                map.replace(USER_ID, String.valueOf(userDao.getIdFromPersonInfoId(infoId)));
                map.replace(ROLE, "user");
            }
            if (librarianDao.hasInfoId(infoId)) {
                map.replace(ROLE, "librarian");
            }
            if (adminDao.hasInfoId(infoId)) {
                map.replace(ROLE, "admin");
            }
            map.replace(STATUS, "success");
            map.replace(NAME, personalInfo.getFirstName() + " " + personalInfo.getLastName());
        } else {
            map.replace(STATUS, "failed");
            map.replace(ERROR, "Wrong Username or Password</br>Неправильний логін або пароль");
        }
        return map;
    }

    public HashMap<String, String> bookValidation(Part filePart, BookDaoImpl bookDao, AuthorDaoImpl authorDao, PublisherDaoImpl publisherDao, String book_id, String title, String titleUa, String yearStr, String author, String authorUa, String publisher, String publisherUa, String genre, String countStr, String description, String descriptionUa) throws IOException {

        Book bookEntity = book_id == null ? new Book() : bookDao.get(Long.parseLong(book_id));

        HashMap<String, String> result = new HashMap<>();

        //PATH write
        if (book_id == null) {
            result.put("path", "new_book.jsp");
        } else {
            result.put("path", "change_book.jsp");
        }

        //regex
        final String SIMPLE_REGEX = "[^%{}<>]+";
        final String COUNT_REGEX = "\\d+";
        final String IMG_REGEX = "(\\S+(\\.(?i)(png|jpg|gif|bmp|jpeg|PNG|JPG|GIF|BMP|JPEG)$))";

        result.put("error", "");

        //title validation
        if (title.matches(SIMPLE_REGEX) && titleUa.matches(SIMPLE_REGEX)) {
            bookEntity.setName(title);
            bookEntity.setNameUa(titleUa);
        } else {
            result.replace("error", "Incorrect book title!</br>Неправильний заголовок!");
            return result;
        }

        //year validation
        int year;
        try {
            year = Integer.parseInt(yearStr);
        } catch (Exception e) {
            result.replace("error", "Incorrect year input!</br>Неправильний рік!");
            return result;
        }
        Calendar calendar = new GregorianCalendar();
        int current_Year = calendar.get(Calendar.YEAR);
        if (year >= 1800 && year <= current_Year) {
            bookEntity.setDateOfPublication(year);
        } else {
            result.replace("error", "Year must be between 1800 year - now !</br>Рік має бути від 1800 року - теперішнього!");
            return result;
        }

        //author validation

        if (author.matches(SIMPLE_REGEX) && authorUa.matches(SIMPLE_REGEX)) {
            if (authorDao.idFromName(author, authorUa) == 0L) {
                Author author1 = new Author();
                author1.setAuthorName(author);
                author1.setAuthorNameUa(authorUa);
                authorDao.insert(author1);
                bookEntity.setAuthorId(authorDao.idFromName(author, authorUa));
            } else {
                bookEntity.setAuthorId(authorDao.idFromName(author, authorUa));
            }
        } else {
            result.replace("error", "Incorrect author name!</br>Неправильне ім'я автора");
            return result;
        }

        //publisher validation

        if (publisher.matches(SIMPLE_REGEX) && publisherUa.matches(SIMPLE_REGEX)) {
            if (publisherDao.idFromName(publisher, publisherUa) == 0L) {
                Publisher publisher1 = new Publisher();
                publisher1.setPublisherName(publisher);
                publisher1.setPublisherNameUa(publisherUa);
                publisherDao.insert(publisher1);
                bookEntity.setPublicationId(publisherDao.idFromName(publisher, publisherUa));
            } else {
                bookEntity.setPublicationId(publisherDao.idFromName(publisher, publisherUa));
            }
        } else {
            result.replace("error", "Incorrect publisher name!</br>Неправильна назва публікатора!");
            return result;
        }

        //count validation
        int count = 0;
        try {
            count = Integer.parseInt(countStr);
        } catch (Exception e) {
            result.replace("error", "In count should be only numbers!</br>У кількості книг мають бути лише числа!");
            return result;
        }
        if (("" + count).matches(COUNT_REGEX)) {
            bookEntity.setCount(count);
        } else {
            result.replace("error", "In count should be only numbers!</br>У кількості книг мають бути лише числа!");
            return result;
        }

        //set description
        bookEntity.setDescription(description);
        bookEntity.setDescriptionUa(descriptionUa);

        //work with img
        String fileName;
        fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
        if (!fileName.equals("")) {
            if (!fileName.matches(IMG_REGEX)) {
                result.replace("error", "Incorrect image type!</br>Неправильний тип зображення!");
                return result;
            }
            bookEntity.setImgName(fileName);
            InputStream fileContent = filePart.getInputStream();
            File file = new File("C:/Users/Modgen/IdeaProjects/Library_1/src/main/webapp/book_images/" + fileName);
            BufferedImage imBuff = ImageIO.read(fileContent);
            ImageIO.write(imBuff, FilenameUtils.getExtension(fileName), file);
            bookEntity.setImgName(fileName);
        } else if (book_id == null) {
            fileName = "book0.jpg";
            bookEntity.setImgName(fileName);
        }

        //final set
        bookEntity.setGenreId(Long.parseLong(genre));
        if (book_id == null) {
            bookDao.insert(bookEntity);
        } else {
            bookDao.update(bookEntity);
        }
        return result;
    }

    public String newOrderValidation(long userId, long bookId, String status, String ordersStatusUa, UserOrdersDaoImpl userOrdersDao, BookDaoImpl bookDao, SubscriptionsDaoImpl subscriptionsDao) {
        //entity
        Book book = bookDao.get(bookId);
        UserOrders userOrders = new UserOrders();

        //count
        int bookCountInOrder = userOrdersDao.bookCountInUsers(bookId);
        int bookCount = book.getCount();

        //subscribe validation
        if (subscriptionsDao.getFromUserDao(userId) == null) {
            return "Sorry, but You should get subscription in your Personal Account</br>Вибачте, але Ви маєте отримати підписку в Особистому кабінеті";
        }

        //count validation
        if (bookCount <= bookCountInOrder) {
            return "Sorry, but we do not have this book in stock at the moment</br>Вибачте, але ми не маємо цієї книги на даний момент";
        }

        //user already has one
        if (userOrdersDao.userAlreadyHasThisBook(bookId, userId)) {
            return "You have already rented this book</br>Ви вже орендували цю книгу";
        }

        //users can't have more than 10 book for one
        if (userOrdersDao.countOrderedOneUser(userId) >= 10) {
            return "Sorry, but You can't order more than 10 book</br>Вибачте, але ви не можете замовити більше 10 книг одночасно";
        }

        userOrders.setStatus(status);
        userOrders.setUserId(userId);
        userOrders.setBookId(bookId);
        userOrders.setStatusUa(ordersStatusUa);


        userOrdersDao.insert(userOrders);
        return null;
    }

    public String newPersonValidate(PersonalInfoDaoImpl personalInfoDao, PersonalInfo personalInfoEntity, String fName, String lName, String login, String password) {
        if (personalInfoDao == null || personalInfoEntity == null || fName == null || lName == null || login == null || password == null)
            throw new NullPointerException();

        //regex
        final String NAME_REGEX = "[а-яА-Яa-zA-Z]+";
        final String PASSWORD_REGEX = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{8,20}$";//Пароль мусить мати як мінімум 1 цифру, 1 букву(Велику та Малу), 1 спец.символ(@#$%) і мати довжину від 8 до 20 символів

        //f_name validation
        if (fName.matches(NAME_REGEX)) {
            personalInfoEntity.setFirstName(fName);
        } else {
            return "Incorrect first name</br>Невірне ім'я";
        }

        //l_name validation
        if (lName.matches(NAME_REGEX)) {
            personalInfoEntity.setLastName(lName);
        } else {
            return "Incorrect last name</br>Невірна фамилія";
        }

        //login validation
        if (personalInfoDao.getId(login) == 0) {
            personalInfoEntity.setLogin(login);
        } else {
            return "This login is already in used</br>Цей логін вже зареєстрований";
        }

        //password validation
        if (password.matches(PASSWORD_REGEX)) {
            personalInfoEntity.setPassword(password);
        } else {
            return "Password must have at least one numeric character, one lowercase character, one uppercase character, one special symbol (among @#$%) and have length between 8 and 20</br>Пароль мусить мати як мінімум 1 цифру, 1 букву(Велику та Малу), 1 спец.символ(@#$%) і мати довжину від 8 до 20 символів";
        }
        return "";
    }
}
