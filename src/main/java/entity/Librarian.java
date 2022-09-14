package entity;


public class Librarian {

  private int id;
  private PersonalInfo personalInfo;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public PersonalInfo getPerson() {
    return personalInfo;
  }

  public void setPerson(PersonalInfo personalInfo) {
    this.personalInfo = personalInfo;
  }
}
