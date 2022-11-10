package entity;


public class Author {

    private long id;
    private String authorName;
    private String authorNameUa;

    public Author(long id, String authorName, String authorNameUa) {
        this.id = id;
        this.authorName = authorName;
        this.authorNameUa = authorNameUa;
    }

    public Author() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getAuthorName() {
    return authorName;
  }

  public void setAuthorName(String authorName) {
    this.authorName = authorName;
  }

  public String getAuthorNameUa() {
    return authorNameUa;
  }

  public void setAuthorNameUa(String authorNameUa) {
    this.authorNameUa = authorNameUa;
  }
}
