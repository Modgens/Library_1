package entity;

public class User {
    private int id;
    private PersonalInfo personalInfo;
    private String email;

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", person=" + personalInfo.getLogin() +
                ", email='" + email + '\'' +
                '}';
    }
}
