package entity.megaEntity;

import entity.Author;
import entity.Genre;
import entity.Publisher;

public class MegaBook {
    private long id;
    private String name;
    private String nameUa;
    private Publisher publisher;
    private int dateOfPublication;
    private int count;
    private String description;
    private String descriptionUa;
    private String imgName;
    private Author author;
    private Genre genre;

    public MegaBook(long id, String name, String nameUa, Publisher publisher, int dateOfPublication, int count, String description, String descriptionUa, String imgName, Author author, Genre genre) {
        this.id = id;
        this.name = name;
        this.nameUa = nameUa;
        this.publisher = publisher;
        this.dateOfPublication = dateOfPublication;
        this.count = count;
        this.description = description;
        this.descriptionUa = descriptionUa;
        this.imgName = imgName;
        this.author = author;
        this.genre = genre;
    }

    public MegaBook() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNameUa() {
        return nameUa;
    }

    public void setNameUa(String nameUa) {
        this.nameUa = nameUa;
    }

    public Publisher getPublisher() {
        return publisher;
    }

    public void setPublisher(Publisher publisher) {
        this.publisher = publisher;
    }

    public int getDateOfPublication() {
        return dateOfPublication;
    }

    public void setDateOfPublication(int dateOfPublication) {
        this.dateOfPublication = dateOfPublication;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescriptionUa() {
        return descriptionUa;
    }

    public void setDescriptionUa(String descriptionUa) {
        this.descriptionUa = descriptionUa;
    }

    public String getImgName() {
        return imgName;
    }

    public void setImgName(String imgName) {
        this.imgName = imgName;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }
}
