package entity;

public class Book {
    private long id;
    private String name;
    private String nameUa;
    private long publicationId;
    private int dateOfPublication;
    private int count;
    private String description;
    private String descriptionUa;
    private String imgName;
    private long authorId;
    private long genreId;

    public long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(long authorId) {
        this.authorId = authorId;
    }

    public long getGenreId() {
        return genreId;
    }

    public void setGenreId(long genreId) {
        this.genreId = genreId;
    }

    public String getImgName() {
        return imgName;
    }

    public void setImgName(String imgName) {
        this.imgName = imgName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getPublicationId() {
        return publicationId;
    }

    public void setPublicationId(long publicationId) {
        this.publicationId = publicationId;
    }

    public int getDateOfPublication() {
        return dateOfPublication;
    }

    public void setDateOfPublication(int dateOfPublication) {
        this.dateOfPublication = dateOfPublication;
    }

    public String getNameUa() {
        return nameUa;
    }

    public void setNameUa(String nameUa) {
        this.nameUa = nameUa;
    }

    public String getDescriptionUa() {
        return descriptionUa;
    }

    public void setDescriptionUa(String descriptionUa) {
        this.descriptionUa = descriptionUa;
    }

    @Override
    public String toString() {

        return "Book{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", nameUa='" + nameUa + '\'' +
                ", publication='" + publicationId + '\'' +
                ", dateOfPublication='" + dateOfPublication + '\'' +
                ", count=" + count +
                ", description='" + description + '\'' +
                ", descriptionUa='" + descriptionUa + '\'' +
                ", imgName='" + imgName + '\'' +
                ", author=" + authorId +
                ", genre=" + genreId +
                '}';
    }
}
