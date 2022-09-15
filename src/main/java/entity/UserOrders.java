package entity;

import java.sql.Date;

public class UserOrders {

  private long id;
  private long userId;
  private long bookId;
  private Date orderDate;
  private Date dateToReturn;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public long getUserId() {
    return userId;
  }

  public void setUserId(long userId) {
    this.userId = userId;
  }

  public long getBookId() {
    return bookId;
  }

  public void setBookId(long bookId) {
    this.bookId = bookId;
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
