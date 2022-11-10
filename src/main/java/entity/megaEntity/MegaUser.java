package entity.megaEntity;

public class MegaUser extends Person {
    private long id;
    private String email;
    private String status;

    public MegaUser(long id, String email, String status) {
        this.id = id;
        this.email = email;
        this.status = status;
    }

    public MegaUser() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

}
