package entity;

public class User {
    private long id;
    private long personalInfoId;
    private String email;
    private String status;

    public User(long id, long personalInfoId, String email, String status) {
        this.id = id;
        this.personalInfoId = personalInfoId;
        this.email = email;
        this.status = status;
    }

    public User() {
    }

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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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
