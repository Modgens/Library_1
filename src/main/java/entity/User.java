package entity;

public class User {
    private long id;
    private long personalInfoId;
    private String email;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getPersonId() {
        return personalInfoId;
    }

    public void setPersonId(long personalInfo) {
        this.personalInfoId = personalInfo;
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
                ", person=" + personalInfoId +
                ", email='" + email + '\'' +
                '}';
    }
}
