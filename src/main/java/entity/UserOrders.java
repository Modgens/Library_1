package entity;

import java.sql.Date;

public class UserOrders {

  private long id;
  private long userId;
  private long bookId;
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
