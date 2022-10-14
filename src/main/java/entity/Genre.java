package entity;


public class Genre {

  private long id;
  private String genreName;
  private String genreNameUa;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getGenreName() {
    return genreName;
  }

  public void setGenreName(String genreName) {
    this.genreName = genreName;
  }

  public String getGenreNameUa() {
    return genreNameUa;
  }

  public void setGenreNameUa(String genreNameUa) {
    this.genreNameUa = genreNameUa;
  }
}
