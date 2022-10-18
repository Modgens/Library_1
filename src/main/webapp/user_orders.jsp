<%@ page import="java.util.List" %>
<%@ page import="dao.implementation.*" %>
<%@ page import="entity.UserOrders" %>
<%@ page import="java.util.ResourceBundle" %>
<%@ page import="java.util.Locale" %>
<%@ page import="util.Punisher" %>
<%@ taglib uri="/WEB-INF/navbar-tag.tld" prefix="nav" %>
<%@ taglib prefix="p" tagdir="/WEB-INF/tags" %>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%
  ResourceBundle rb = null;
  if(session.getAttribute("rb")==null){
    rb = ResourceBundle.getBundle("Localization/Bundle", new Locale("en", "US"));
  } else {
    rb = (ResourceBundle) session.getAttribute("rb");
  }
  String lang = rb.getString("language");

  UserOrdersDaoImpl userOrdersDao = new UserOrdersDaoImpl();
  request.setAttribute("bookDao", new BookDaoImpl());
  request.setAttribute("authorDao", new AuthorDaoImpl());
  request.setAttribute("genreDao", new GenreDaoImpl());
  request.setAttribute("publisherDao", new PublisherDaoImpl());
  request.setAttribute("userDao",  new UserDaoImpl());
  request.setAttribute("personalInfoDao",  new PersonalInfoDaoImpl());

  List<UserOrders> list =  (List<UserOrders>) session.getAttribute("sub_order_list");
  if(list == null)
    list = userOrdersDao.getAllForSub();
  request.setAttribute("list", list);
  request.setAttribute("lang", lang);

%>

<!doctype html>
<html lang="en">


<head>
  <%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
  <%@include file="includes/header.jsp"%>
  <title>Library</title>
</head>
<body>

<nav:Navbar message="orders" role='<%=(String)session.getAttribute("role")%>' name='<%=(String) session.getAttribute("name")%>' lang="<%=lang%>"/>

<div class="collapse" id="navbarToggleExternalContent">
  <div class="bg-light p-4">
    <form method="post" action="orderSearch" class="d-flex">

      <input type="hidden" name="page" value="/user_orders.jsp">
      <div class="d-grid gap-2 col-3 mx-2">
        <input name="first_name" type="text" class="form-control" aria-describedby="emailHelp" placeholder="<%=rb.getString("fName")%>">
      </div>
      <div class="d-grid gap-2 col-3 mx-2">
        <input name="last_name" type="text" class="form-control" aria-describedby="emailHelp" placeholder="<%=rb.getString("lName")%>">
      </div>
      <div class="d-grid gap-2 col-3 mx-2">
        <input name="login" type="text" class="form-control" aria-describedby="emailHelp" placeholder="<%=rb.getString("login")%>">
      </div>
      <div class="d-grid gap-2 col-1 mx-2">
        <button type="submit" class="btn btn-primary my-auto"><%=rb.getString("find")%></button>
      </div>
      <div class="d-grid gap-2 col-1 mx-2">
        <button type="submit" class="btn btn-danger my-auto" <%=list.size()==userOrdersDao.getAllForSub().size()?"disabled":""%>><%=rb.getString("reset")%></button></a>
      </div>
    </form>
  </div>
</div>
<nav class="navbar navbar-light bg-light">
  <div class="container-fluid">
    <button class="btn btn-outline-success d-grid gap-2 col-3 mx-auto" type="button" data-bs-toggle="collapse" data-bs-target="#navbarToggleExternalContent" aria-controls="navbarToggleExternalContent" aria-expanded="false" aria-label="Toggle navigation">
      <%=rb.getString("searching")%>
    </button>
  </div>
</nav>

<table class="table mt-1">
  <thead class="table-dark">
  <tr>
    <th scope="col"><%=rb.getString("title")%></th>
    <th scope="col"><%=rb.getString("author")%></th>
    <th scope="col"><%=rb.getString("genre")%></th>
    <th scope="col"><%=rb.getString("publisher")%></th>
    <th scope="col"><%=rb.getString("dateOfPublication")%></th>
    <th scope="col"><%=rb.getString("fine")%></th>
    <th scope="col"><%=rb.getString("fName")%></th>
    <th scope="col"><%=rb.getString("lName")%></th>
    <th scope="col"><%=rb.getString("login")%></th>
    <th scope="col"><%=rb.getString("os")%></th>
    <th scope="col"><%=rb.getString("od")%></th>
    <th scope="col"><%=rb.getString("dtr")%></th>
    <th scope="col"><%=rb.getString("action")%></th>
  </tr>

  </thead>
  <tbody>
  <%
    Punisher punisher = Punisher.getInstance();

    int i = 0;
    int end = 14;
    if(!list.isEmpty()){
      if(list.size()<=15){
        end = list.size()-1;
      } else if(request.getAttribute("firstObj")!=null) {
        i = (int) request.getAttribute("firstObj") - 1;
        end = (int) request.getAttribute("lastObj") - 1;
      }
    }
    while (i <= end && !list.isEmpty()) {
      request.setAttribute("i", i);
  %>
  <tr>
    <form action="orderPrecessing" method="post">
      <td>${bookDao.get( list.get(i).bookId).name}</td>
      <td>${authorDao.get(bookDao.get( list.get(i).bookId).authorId).authorName}</td>
      <td>${genreDao.get(bookDao.get( list.get(i).bookId).genreId).genreName}</td>
      <td>${publisherDao.get(bookDao.get( list.get(i).bookId).publicationId).publisherName}</td>
      <td>${bookDao.get( list.get(i).bookId).dateOfPublication}</td>
      <td><%=punisher.getByOrderId(list.get(i).getId())%> ₴</td>
      <td>${personalInfoDao.get(userDao.get( list.get(i).userId).personId).firstName}</td>
      <td>${personalInfoDao.get(userDao.get( list.get(i).userId).personId).lastName}</td>
      <td>${personalInfoDao.get(userDao.get( list.get(i).userId).personId).login}</td>
      <td><%=lang.equals("en")?list.get(i).getStatus():list.get(i).getStatusUa()%></td>
      <c:choose>
        <c:when test="${list.get(i).status eq 'Wait for order'}">
          <td><%=rb.getString("noy")%></td>
          <td>
            <input autocomplete="off" type="text" name="date" class="datepicker form-control mx-1" style="width: 150px" required>
            <input type="hidden" name="order_id" value="${ list.get(i).id}">
          </td>
          <td><input class="btn btn-success" type="submit" value="<%=rb.getString("gab")%>"></td>
        </c:when>
        <c:otherwise>
          <td>${ list.get(i).orderDate}</td>
          <td>${ list.get(i).dateToReturn}</td>
          <td>
            <input type="hidden" name="order_id" value="${ list.get(i).id}">
            <input class="btn btn-danger" type="submit" value="<%=rb.getString("pick")%>">
          </td>
        </c:otherwise>
      </c:choose>
    </form>
  </tr>
  <%
      i++;
    }
    int current_page = 1;
    if(request.getAttribute("page")!=null)
      current_page = (int) request.getAttribute("page");
  %>
  </tbody>
</table>

<%
  if(!list.isEmpty()){
%>
<p:pagination count='<%=String.valueOf((list.size()%15==0)?list.size()/15:list.size()/15+1)%>' current_page='<%=String.valueOf(current_page)%>' link="user_orders.jsp" list_size='<%=String.valueOf(list.size())%>'/>
<%
  }
%>

<%@include file="includes/footer.jsp"%>
<script type="text/javascript">
  $(function() {
    $('input.datepicker').datepicker({
      startDate: '+1d'
    });
  });
</script>
</body>
</html>

