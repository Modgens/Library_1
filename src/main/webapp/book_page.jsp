<%@ page import="entity.Book" %>
<%@ page import="dao.implementation.AuthorDaoImpl" %>
<%@ page import="dao.implementation.GenreDaoImpl" %><%--
  Created by IntelliJ IDEA.
  User: Modgen
  Date: 20.09.2022
  Time: 17:05
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib uri="/WEB-INF/navbar-tag.tld" prefix="nav" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    Book book = (Book)request.getAttribute("book");
    AuthorDaoImpl authorDao = new AuthorDaoImpl();
    GenreDaoImpl genreDao = new GenreDaoImpl();
%>
<html>
<head>
    <%@include file="includes/header.jsp"%>
</head>
<body>
<nav:Navbar message="" role='<%=(String) session.getAttribute("role")%>' name='<%=(String) session.getAttribute("name")%>'/>
<div class="container my-5">
    <div class="d-flex">
        <div class="col-5 card">
            <img style="max-height: 640px" src="book_images/<%=book.getImgName()%>" alt="">
        </div>
        <div class="ms-4 col-5 px-5 pt-5">
            <h1><%=book.getName()%></h1>
            <br>
            <div class=""></div>
            <h4>Дата видання: <%=book.getDateOfPublication()%> year</h4>
            <h4>Публікатор: <%=book.getPublicationId()%></h4>
            <h4>Автор: <%=authorDao.get(book.getAuthorId()).getAuthorName()%>></h4>
            <h4>Жанр: <%=genreDao.get(book.getGenreId()).getGenreName()%></h4>
                        <br><br><br><br><br><br><br><br>
                        <a href="#" class="mt-5 p-3 d-grid btn btn-outline-success card-bottom "><h4>Order</h4></a>
        </div>
    </div>
    <div class="card mt-3">
        <div class="card-header text-center">
            <h2 class="mt-3 mx-auto">Опис</h2>
        </div>
        <div class="card-body">
            <%=book.getDescription()%>
        </div>

    </div>
</div>



<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM"
        crossorigin="anonymous"></script>

<!-- Option 2: Separate Popper and Bootstrap JS -->
<!--
  <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js" integrity="sha384-IQsoLXl5PILFhosVNubq5LC7Qb9DXgDA9i+tQ8Zj3iwWAwPtgFTxbJ8NT4GN1R8p" crossorigin="anonymous"></script>
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.min.js" integrity="sha384-cVKIPhGWiC2Al4u+LWgxfKTRIcfu0JTxR+EQDz/bgldoEyl4H0zUF0QKbrJ0EcQF" crossorigin="anonymous"></script>
  -->
</body>
</html>