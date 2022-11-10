package entity.megaEntity;

import entity.Book;
import entity.User;

import java.sql.Date;

public class MegaOrder {

    private long id;
    private User user;
    private Book book;
    private Date orderDate;
    private Date dateToReturn;
    private String status;
    private String statusUa;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public Date getDateToReturn() {
        return dateToReturn;
    }

    public void setDateToReturn(Date dateToReturn) {
        this.dateToReturn = dateToReturn;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatusUa() {
        return statusUa;
    }

    public void setStatusUa(String statusUa) {
        this.statusUa = statusUa;
    }
}
