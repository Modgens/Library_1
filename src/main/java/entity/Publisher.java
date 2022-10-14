package entity;

public class Publisher {

  private long id;
  private String publisherName;
  private String publisherNameUa;


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
