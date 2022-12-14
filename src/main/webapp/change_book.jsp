<%@ page import="dao.implementation.BookDaoImpl" %>
<%@ page import="java.util.List" %>
<%@ page import="entity.Book" %>
<%@ page import="dao.implementation.AuthorDaoImpl" %>
<%@ page import="dao.implementation.GenreDaoImpl" %>
<%@ page import="entity.Genre" %>
<%@ page import="dao.implementation.PublisherDaoImpl" %>
<%@ page import="entity.Author" %>
<%@ page import="entity.Publisher" %>
<%@ page import="java.util.ResourceBundle" %>
<%@ page import="java.util.Locale" %>
<%@ taglib uri="/WEB-INF/navbar-tag.tld" prefix="nav" %>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"
         language="java"%>
<%
  ResourceBundle rb = null;
  if(session.getAttribute("rb")==null){
    rb = ResourceBundle.getBundle("Localization/Bundle", new Locale("en", "US"));
  } else {
    rb = (ResourceBundle) session.getAttribute("rb");
  }
  String lang = rb.getString("language");

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

<nav:Navbar message="" role='<%=(String)session.getAttribute("role")%>' name='<%=(String) session.getAttribute("name")%>' lang="<%=lang%>"/>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.1/jquery.min.js"></script>
<div class="container my-5" style="max-width: 65rem;">
  <h1 class="mb-3"><%=rb.getString("changeBook")%></h1>

  <h5 style="color: red"><%=rb.getString("changeOnly")%></h5>

  <form action="newBook" method="post" enctype="multipart/form-data">
    <input type="hidden" name="book_id" value="<%=request.getParameter("book_id")%>">
    <div class="row">
      <div class="mb-3 col-6">
        <label for="titleInput" class="form-label"><%=rb.getString("ebTitleEn")%></label>
        <input value="<%=book.getName()%>" name="title" type="text" class="form-control" id="titleInput" placeholder="Kobzar" required>
      </div>
      <div class="mb-3 col-6">
        <label for="titleInput" class="form-label"><%=rb.getString("ebTitleUa")%></label>
        <input value="<%=book.getNameUa()%>" name="titleUa" type="text" class="form-control" id="titleInput2" placeholder="????????????" required>
      </div>
    </div>

    <label for="SelectGenre" class="form-label"><%=rb.getString("selectG")%></label>
    <select name="genre" id="SelectGenre" class="form-select  mb-3" aria-label="Default select example" required>
      <%
        for (Genre genre : genreDao.getAll()) {
      %>
      <option <%=genre.getId()==book.getGenreId()?"selected":""%> value="<%=genre.getId()%>"><%=genre.getGenreName()%></option>
      <%
        }
      %>
    </select>

    <div class="mb-3">
      <label for="ControlInput" class="form-label"><%=rb.getString("eDate")%></label>
      <input value="<%=book.getDateOfPublication()%>" name="year" type="text" class="form-control" id="ControlInput" placeholder="1956" required>
    </div>

    <div class="row">
      <div class="col-6">
        <label for="AuthorDataList" class="form-label"><%=rb.getString("selectAEn")%></label>
        <input value="<%=authorDao.get(book.getAuthorId()).getAuthorName()%>" name="author" class="form-control mb-3" list="datalistAuthor" id="AuthorDataList" placeholder="Type to search..." autocomplete="off" required>
        <datalist id="datalistAuthor">
          <%
            for (Author author : authorDao.getAll()) {
          %>
          <option value="<%=author.getAuthorName()%>"><%=author.getAuthorName()%></option>
          <%
            }
          %>
        </datalist>
      </div>
      <div class="col-6">
        <label for="AuthorDataList" class="form-label"><%=rb.getString("selectAUa")%></label>
        <input value="<%=authorDao.get(book.getAuthorId()).getAuthorNameUa()%>" name="authorUa" class="form-control mb-3" list="datalistAuthor2" id="AuthorDataList2" placeholder="Type to search..." autocomplete="off" required>
        <datalist id="datalistAuthor2">
          <%
            for (Author author : authorDao.getAll()) {
          %>
          <option value="<%=author.getAuthorNameUa()%>"><%=author.getAuthorNameUa()%></option>
          <%
            }
          %>
        </datalist>
      </div>
    </div>
    <div class="row">
      <div class="col-6">
        <label for="PublicationDataList" class="form-label"><%=rb.getString("selectPEn")%></label>
        <input value="<%=publisherDao.get(book.getPublicationId()).getPublisherName()%>" name="publisher" class="form-control mb-3" list="datalistPublication" id="PublicationDataList" placeholder="Type to search..." autocomplete="off" required>
        <datalist  id="datalistPublication" >
          <%
            for (Publisher publisher : publisherDao.getAll()) {
          %>
          <option value="<%=publisher.getPublisherName()%>"><%=publisher.getPublisherName()%></option>
          <%
            }
          %>
        </datalist>
      </div>
      <div class="col-6">
        <label for="PublicationDataList" class="form-label"><%=rb.getString("selectPUa")%></label>
        <input value="<%=publisherDao.get(book.getPublicationId()).getPublisherNameUa()%>" name="publisherUa" class="form-control mb-3" list="datalistPublication2" id="PublicationDataList2" placeholder="Type to search..." autocomplete="off" required>
        <datalist  id="datalistPublication2" >
          <%
            for (Publisher publisher : publisherDao.getAll()) {
          %>
          <option value="<%=publisher.getPublisherNameUa()%>"><%=publisher.getPublisherNameUa()%></option>
          <%
            }
          %>
        </datalist>
      </div>
    </div>

    <div class="mb-3">
      <label for="CountInput" class="form-label"><%=rb.getString("eC")%></label>
      <input value="<%=book.getCount()%>" name="count" type="text" class="form-control" id="CountInput" placeholder="123" required>
    </div>
    <div class="mb-3">
      <label for="FormControlTextarea" class="form-label"><%=rb.getString("eDEn")%></label>
      <textarea name="description" class="form-control" id="FormControlTextarea" rows="3"><%=book.getDescription()%></textarea>
    </div>
    <div class="mb-3">
      <label for="FormControlTextareaUa" class="form-label"><%=rb.getString("eDUa")%></label>
      <textarea name="descriptionUa" class="form-control" id="FormControlTextareaUa" rows="3"><%=book.getDescriptionUa()%></textarea>
    </div>
    <div class="mb-3">
      <label for="formFile" class="form-label"><%=rb.getString("ui")%></label>
      <input name="img" class="form-control" type="file" id="formFile">
    </div>
    <div class="alert alert-info" role="alert">
      <%=rb.getString("ifImgChange")%>
    </div>
    <div class="col-12">
      <button class="btn btn-primary" type="submit"><%=rb.getString("submit")%></button>
    </div>
  </form>
</div>

<%@include file="includes/footer.jsp"%>
<script src="https://cdn.jsdelivr.net/npm/sweetalert2@7.12.15/dist/sweetalert2.all.min.js"></script>
<link rel='stylesheet' href='https://cdn.jsdelivr.net/npm/sweetalert2@7.12.15/dist/sweetalert2.min.css'>
<script type="text/javascript">
  var status = document.getElementById("status").value;
  if(status=="success") {
    swal("Congrats", "Book Changed Successfully</br>?????????? ?????????????? ????????????????", "success");
  }
  else if(status=="failed"){
    swal("Sorry", "<%=request.getAttribute("error")%>", "error");
  }
</script>
</body>
</html>