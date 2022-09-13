package com.sample;


public class UserOrders {

  private long id;
  private long userId;
  private long bookId;
  private java.sql.Date orderDate;
  private java.sql.Date dateToReturn;


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


  public java.sql.Date getOrderDate() {
    return orderDate;
  }

  public void setOrderDate(java.sql.Date orderDate) {
    this.orderDate = orderDate;
  }


  public java.sql.Date getDateToReturn() {
    return dateToReturn;
  }

  public void setDateToReturn(java.sql.Date dateToReturn) {
    this.dateToReturn = dateToReturn;
  }

}
