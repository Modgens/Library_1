<%@ page import="dao.implementation.BookDaoImpl" %>
<%@ page import="java.util.List" %>
<%@ page import="entity.Book" %>
<%@ page import="dao.implementation.AuthorDaoImpl" %>
<%@ page import="dao.implementation.GenreDaoImpl" %>
<%@ page import="entity.Genre" %>
<%@ page import="dao.implementation.PublisherDaoImpl" %>
<%@ page import="entity.Author" %>
<%@ page import="entity.Publisher" %>
<%@ taglib uri="/WEB-INF/navbar-tag.tld" prefix="nav" %>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"
         language="java"%>
<%
  AuthorDaoImpl authorDao = new AuthorDaoImpl();
  GenreDaoImpl genreDao = new GenreDaoImpl();
  PublisherDaoImpl publisherDao = new PublisherDaoImpl();
%>

<!doctype html>
<html lang="en">
<head>
  <%@include file="includes/header.jsp"%>
  <title>Library</title>
</head>
<body>
<input type="hidden" id="status" value="<%= request.getAttribute("status")%>">

<nav:Navbar message="catalog" role='<%=(String)session.getAttribute("role")%>' name='<%=(String) session.getAttribute("name")%>'/>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.1/jquery.min.js"></script>
<div class="container my-5" style="max-width: 30rem;">

  <h1 class="mb-3">Book change</h1>

  <h3 style="color: red">Enter only the data you are going to change:</h3>

  <form action="change_book" method="post" enctype="multipart/form-data">
    <div class="mb-3">
      <label for="titleInput" class="form-label">Enter book title</label>
      <input name="title" type="text" class="form-control" id="titleInput" placeholder="Kobzar">
      <div class="valid-feedback">
        Looks good!
      </div>
    </div>
    <label for="SelectGenre" class="form-label">Select genre</label>
    <select name="genre" id="SelectGenre" class="form-select  mb-3" aria-label="Default select example">
      <%
        for (Genre genre : genreDao.getAll()) {
      %>
      <option value="<%=genre.getGenreName()%>"><%=genre.getGenreName()%></option>
      <%
        }
      %>
    </select>
    <div class="mb-3">
      <label for="ControlInput" class="form-label">Enter year of publication</label>
      <input name="year" type="text" class="form-control" id="ControlInput" placeholder="1956">
    </div>

    <label for="AuthorDataList" class="form-label">Find author: </label>
    <input name="author" class="form-control mb-3" list="datalistAuthor" id="AuthorDataList" placeholder="Type to search...">
    <datalist id="datalistAuthor">
      <%
        for (Author author : authorDao.getAll()) {
      %>
      <option value="<%=author.getAuthorName()%>"><%=author.getAuthorName()%></option>
      <%
        }
      %>
    </datalist>
    <label for="PublicationDataList" class="form-label">Find publication: </label>
    <input name="publisher" class="form-control mb-3" list="datalistPublication" id="PublicationDataList" placeholder="Type to search...">
    <datalist  id="datalistPublication" >
      <%
        for (Publisher publisher : publisherDao.getAll()) {
      %>
      <option value="<%=publisher.getPublisherName()%>"><%=publisher.getPublisherName()%></option>
      <%
        }
      %>
    </datalist>
    <div class="alert alert-info" role="alert">
      If you can't find an author or publisher, you can create one below
    </div>
    <div class="mb-3">
      <label for="CountInput" class="form-label">Enter count of book</label>
      <input name="count" type="text" class="form-control" id="CountInput" placeholder="123">
    </div>
    <div class="mb-3">
      <label for="FormControlTextarea" class="form-label">Enter description</label>
      <textarea name="description" class="form-control" id="FormControlTextarea" rows="3"></textarea>
    </div>
    <div class="mb-3">
      <label for="formFile" class="form-label">Upload img</label>
      <input name="img" class="form-control" type="file" id="formFile">
      <div class="invalid-feedback">
        Please provide a valid zip.
      </div>
    </div>
    <div class="alert alert-info" role="alert">
      If you don't have an image, the book will have an embedded image
    </div>
    <h5>You also can create new author and publisher: </h5>
    <div class="mb-3">
      <label for="newAuthor" class="form-label">Enter author name</label>
      <input name="newAuthor" type="text" class="form-control" id="newAuthor" placeholder="Taras Shevchenko">
      <br>
      <label for="newPublisher" class="form-label">Enter publication name</label>
      <input name="newPublisher" type="text" class="form-control" id="newPublisher" placeholder="BlaBlaBla Studio">
    </div>
    <div class="col-12">
      <button class="btn btn-primary" type="submit">Submit form</button>
    </div>
    <input type="hidden" name="book_id" value="<%=request.getParameter("book_id")%>">
  </form>
</div>

<%@include file="includes/footer.jsp"%>
<script src="https://cdn.jsdelivr.net/npm/sweetalert2@7.12.15/dist/sweetalert2.all.min.js"></script>
<link rel='stylesheet' href='https://cdn.jsdelivr.net/npm/sweetalert2@7.12.15/dist/sweetalert2.min.css'>
<script type="text/javascript">
  var status = document.getElementById("status").value;
  if(status=="success") {
    swal("Congrats", "Book Changed Successfully", "success");
  }
  else if(status=="failed"){
    swal("Sorry", "<%=request.getAttribute("error")%>", "error");
  }
</script>
</body>
</html>