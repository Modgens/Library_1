<%@ page import="dao.implementation.BookDaoImpl" %>
<%@ page import="java.util.List" %>
<%@ page import="entity.Book" %>
<%@ page import="dao.implementation.AuthorDaoImpl" %>
<%@ page import="dao.implementation.GenreDaoImpl" %>
<%@ page import="entity.Genre" %>
<%@ page import="dao.implementation.PublisherDaoImpl" %>
<%@ taglib uri="/WEB-INF/navbar-tag.tld" prefix="nav" %>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"
         language="java"%>
<%

  BookDaoImpl bookDaoImpl = new BookDaoImpl();
  AuthorDaoImpl authorDao = new AuthorDaoImpl();
  GenreDaoImpl genreDao = new GenreDaoImpl();
  PublisherDaoImpl publisherDao = new PublisherDaoImpl();
  List<Book> list = (List<Book>) request.getAttribute("list");
  if(list == null)
          list = bookDaoImpl.getAll();
%>

<!doctype html>
<html lang="en">


<head>
  <%@include file="includes/header.jsp"%>
  <title>Library</title>
</head>
<body>

<nav:Navbar message="catalog" role='<%=(String)session.getAttribute("role")%>' name='<%=(String) session.getAttribute("name")%>'/>

<div class="collapse" id="navbarToggleExternalContent">
  <div class="bg-light p-4">
    <form method="post" action="sort" class="d-flex">
      <div class="d-grid gap-2 col-2 mx-3">
        <select name="genre" class="form-select" aria-label="Default select example">
          <option selected>Genre</option>
          <%
            for (Genre genre : genreDao.getAll()) {
          %>
          <option value="<%=genre.getGenreName()%>"><%=genre.getGenreName()%></option>
            <%
              }
            %>
        </select>
      </div>
      <div class="d-grid gap-2 col-2 mx-2">
        <select name="sort" class="form-select" aria-label="Default select example">
          <option selected>Sorted by</option>
          <option value="name">Name</option>
          <option value="author">Author</option>
          <option value="publisher">Publisher</option>
          <option value="date">Date of publication</option>
        </select>
      </div>
      <div class="d-grid gap-2 col-3 mx-2">
        <input name="book" type="text" class="form-control" aria-describedby="emailHelp" placeholder="Book name">
      </div>
      <div class="d-grid gap-2 col-3 mx-2">
        <input name="author" type="text" class="form-control" aria-describedby="emailHelp" placeholder="Author name">
      </div>
      <div class="d-grid gap-2 col-1 mx-2">
        <button type="submit" class="btn btn-primary my-auto">Find</button>
      </div>
    </form>
  </div>
</div>
<nav class="navbar navbar-light bg-light">
  <div class="container-fluid">
    <button class="btn btn-outline-success d-grid gap-2 col-3 mx-auto" type="button" data-bs-toggle="collapse" data-bs-target="#navbarToggleExternalContent" aria-controls="navbarToggleExternalContent" aria-expanded="false" aria-label="Toggle navigation">
      Searching
    </button>
  </div>
</nav>


<div class="container">
      <%
          if(!list.isEmpty()){
            for (Book book : list) {
      %>
  <div class="card my-3">
    <div class="row g-0">
      <div class="col-md-4">
        <img src="book_images/<%=book.getImgName()%>" class="img-fluid" style="min-height: 400px" alt="...">
      </div>
      <div class="col-md-8">
        <div class="card-body">
          <h4 class="card-title"><a style="text-decoration: none" href="${pageContext.request.contextPath}/book_page?book_id=<%=book.getId()%>" ><%=book.getName()%></a></h4>
          <h6 class="author">Author: <%=authorDao.get(book.getAuthorId()).getAuthorName()%></h6>
          <h6 class="author">Genre: <%=genreDao.get(book.getGenreId()).getGenreName()%></h6>
          <h6 class="publication">Publication: <%=publisherDao.get(book.getPublicationId()).getPublisherName()%></h6>
          <h6 class="publication-date">Date Of Publication: <%=book.getDateOfPublication()%> year</h6>
          <p class="card-text"><%=book.getDescription()%></p>
          <a href="#" class="btn btn-outline-success ">Order</a>
        </div>
      </div>
    </div>
  </div>
            <%}
          }
            %>

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