package entity;

public class Publisher {

    private long id;
    private String publisherName;
    private String publisherNameUa;

    public Publisher() {
    }

    public Publisher(long id, String publisherName, String publisherNameUa) {
        this.id = id;
        this.publisherName = publisherName;
        this.publisherNameUa = publisherNameUa;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPublisherName() {
    return publisherName;
  }

  public void setPublisherName(String publisherName) {
    this.publisherName = publisherName;
  }

  public String getPublisherNameUa() {
    return publisherNameUa;
  }

  public void setPublisherNameUa(String publisherNameUa) {
    this.publisherNameUa = publisherNameUa;
  }
}
