<%@ page import="java.util.List" %>
<%@ page import="dao.implementation.*" %>
<%@ page import="entity.*" %>
<%@ page import="java.util.ResourceBundle" %>
<%@ page import="java.util.Locale" %>
<%@ taglib uri="/WEB-INF/navbar-tag.tld" prefix="nav" %>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%
  ResourceBundle rb = null;
  if(session.getAttribute("rb")==null){
    rb = ResourceBundle.getBundle("Localization/Bundle", new Locale("en", "US"));
  } else {
    rb = (ResourceBundle) session.getAttribute("rb");
  }
  String lang = rb.getString("language");

  BookDaoImpl bookDaoImpl = new BookDaoImpl();
  AuthorDaoImpl authorDao = new AuthorDaoImpl();
  GenreDaoImpl genreDao = new GenreDaoImpl();
  PublisherDaoImpl publisherDao = new PublisherDaoImpl();
  UserOrdersDaoImpl userOrdersDao = new UserOrdersDaoImpl();
  UserDaoImpl userDao = new UserDaoImpl();
  List<UserOrders> list = userOrdersDao.getAllByUserId((long)session.getAttribute("user_id"));
%>
<!doctype html>
<html lang="en">

<head>
  <%@include file="includes/header.jsp"%>
  <title>Library</title>
</head>
<body>

<nav:Navbar message="myBooks" role='<%=(String)session.getAttribute("role")%>' name='<%=(String) session.getAttribute("name")%>' lang="<%=lang%>"/>

<table class="table ps-20px mt-1">
  <thead class="table-dark">
  <tr>
    <th scope="col"><%=rb.getString("image")%></th>
    <th scope="col"><%=rb.getString("title")%></th>
    <th scope="col"><%=rb.getString("author")%></th>
    <th scope="col"><%=rb.getString("genre")%></th>
    <th scope="col"><%=rb.getString("dateOfPublication")%></th>
    <th scope="col"><%=rb.getString("publisher")%></th>
    <th scope="col"><%=rb.getString("orderDate")%></th>
    <th scope="col"><%=rb.getString("dateToReturn")%></th>
    <th scope="col"><%=rb.getString("status")%></th>
  </tr>
  </thead>
  <tbody>

  <%
    if(!list.isEmpty()){
      for (UserOrders userOrders : list) {
        Book book = bookDaoImpl.get(userOrders.getBookId());
        String orderDate = lang.equals("en")?"Not Order Yet": "Ще не оформлений";
        String dateToReturn = lang.equals("en")?"Not Order Yet": "Ще не оформлений";
        String status = userOrders.getStatus();
        if(status.equals("In order")||status.equals("In reading room")){
          orderDate = userOrders.getOrderDate().toString();
          dateToReturn = userOrders.getDateToReturn().toString();
        }
        if(lang.equals("en")){
  %>
  <tr>
    <td><img src="book_images/<%=book.getImgName()%>" height="40px" alt=""></td>
    <td><%=book.getName()%></td>
    <td><%=authorDao.get(book.getAuthorId()).getAuthorName()%></td>
    <td><%=genreDao.get(book.getGenreId()).getGenreName()%></td>
    <td><%=book.getDateOfPublication()%></td>
    <td><%=publisherDao.get(book.getPublicationId()).getPublisherName()%></td>
    <td><%=orderDate%></td>
    <td><%=dateToReturn%></td>
    <td><%=userOrders.getStatus()%></td>
  </tr>
  <%} else {%>
  <tr>
    <td><img src="book_images/<%=book.getImgName()%>" height="40px" alt=""></td>
    <td><%=book.getNameUa()%></td>
    <td><%=authorDao.get(book.getAuthorId()).getAuthorNameUa()%></td>
    <td><%=genreDao.get(book.getGenreId()).getGenreNameUa()%></td>
    <td><%=book.getDateOfPublication()%></td>
    <td><%=publisherDao.get(book.getPublicationId()).getPublisherNameUa()%></td>
    <td><%=orderDate%></td>
    <td><%=dateToReturn%></td>
    <td><%=userOrders.getStatusUa()%></td>
  </tr>
  <%
        }
      }
    }
  %>
  </tbody>
</table>

<%@include file="includes/footer.jsp"%>
</body>
</html>