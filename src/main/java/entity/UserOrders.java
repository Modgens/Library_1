package entity;

import java.sql.Date;

public class UserOrders {

  private int id;
  private User user;
  private Book book;
  private Date orderDate;
  private Date dateToReturn;

  public int getId() {
    return id;
  }

  public void setId(int id) {
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

}
