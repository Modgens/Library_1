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
  Book book = new BookDaoImpl().get(Long.parseLong(request.getParameter("book_id")));
%>

<!doctype html>
<html lang="en">
<head>
  <%@include file="includes/header.jsp"%>
  <title>Library</title>
</head>
<body>
<input type="hidden" id="status" value="<%= request.getAttribute("status")%>">

<nav:Navbar message="books" role='<%=(String)session.getAttribute("role")%>' name='<%=(String) session.getAttribute("name")%>' lang="eng"/>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.1/jquery.min.js"></script>
<div class="container my-5" style="max-width: 30rem;">

  <h1 class="mb-3">Book change</h1>

  <h3 style="color: red">Enter only the data you are going to change:</h3>

  <form action="change_book" method="post" enctype="multipart/form-data">
    <div class="mb-3">
      <label for="titleInput" class="form-label">Enter book title</label>
      <input name="title" type="text" class="form-control" id="titleInput" placeholder="Kobzar" value="<%=book.getName()%>">
    </div>
    <label for="SelectGenre" class="form-label">Select genre</label>
    <select name="genre" id="SelectGenre" class="form-select  mb-3" aria-label="Default select example">
      <%
        for (Genre genre : genreDao.getAll()) {
          if(book.getGenreId()==genreDao.idFromName(genre.getGenreName())){
            %><option selected value="<%=genre.getGenreName()%>"><%=genre.getGenreName()%></option><%
          } else {
            %><option value="<%=genre.getGenreName()%>"><%=genre.getGenreName()%></option><%
          }
        }
      %>
    </select>
    <div class="mb-3">
      <label for="ControlInput" class="form-label">Enter year of publication</label>
      <input name="year" type="text" class="form-control" id="ControlInput" placeholder="1956" value="<%=book.getDateOfPublication()%>">
    </div>

    <label for="AuthorDataList" class="form-label">Find author: </label>
    <input name="author" class="form-control mb-3" list="datalistAuthor" id="AuthorDataList" placeholder="Type to search..." value="<%=authorDao.get(book.getAuthorId()).getAuthorName()%>">
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
    <input name="publisher" class="form-control mb-3" list="datalistPublication" id="PublicationDataList" placeholder="Type to search..." value="<%=publisherDao.get(book.getPublicationId()).getPublisherName()%>">
    <datalist  id="datalistPublication" >
      <%
        for (Publisher publisher : publisherDao.getAll()) {
      %>
      <option value="<%=publisher.getPublisherName()%>"><%=publisher.getPublisherName()%></option>
      <%
        }
      %>
    </datalist>
    <div class="mb-3">
      <label for="CountInput" class="form-label">Enter count of book</label>
      <input name="count" type="text" class="form-control" id="CountInput" placeholder="123" value="<%=book.getCount()%>">
    </div>
    <div class="mb-3">
      <label for="FormControlTextarea" class="form-label">Enter description</label>
      <textarea name="description" class="form-control" id="FormControlTextarea" rows="3"><%=book.getDescription()%></textarea>
    </div>
    <div class="mb-3">
      <label for="formFile" class="form-label">Upload img</label>
      <input name="img" class="form-control" type="file" id="formFile">
    </div>
    <div class="alert alert-info" role="alert">
      If you don't upload an image, the book will have a previous image
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