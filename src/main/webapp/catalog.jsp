<%@ page import="dao.implementation.BookDaoImpl" %>
<%@ page import="java.util.List" %>
<%@ page import="entity.BookEntity" %>
<%
  BookDaoImpl bookDaoImpl = new BookDaoImpl();
  List<BookEntity> list = bookDaoImpl.getAll();
%>



<!doctype html>
<html lang="en">

<head>
  <%@include file="includes/header.jsp"%>
  <title>Library</title>
</head>
<body>
<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
  <div class="container">
    <a href="#" class="navbar-brand mb-0 h1">
      <img src="images/freehand-book.svg" width="30px" alt="">
      Library
    </a>
    <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarScroll" aria-controls="navbarScroll" aria-expanded="false" aria-label="Toggle navigation">
      <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="navbarScroll">
      <ul class="navbar-nav me-auto my-2 my-lg-0 navbar-nav-scroll" style="--bs-scroll-height: 100px;">
        <li class="nav-item">
          <a class="nav-link" aria-current="page" href="index.jsp">Home</a>
        </li>
        <li class="nav-item">
          <a class="nav-link active" href="catalog.jsp">Catalog</a>
        </li>
        <li class="nav-item dropdown d-flex">
          <a class="nav-link dropdown-toggle" href="#" role="button" data-bs-toggle="dropdown" aria-expanded="false">
            Language
          </a>
          <ul class="dropdown-menu">
            <li><a class="dropdown-item" href="#"><img src="images/the-ukraine-flag.jpg" width="17px" alt="" style="margin-right: 5px;">Ukraine</a></li>
            <li><hr class="dropdown-divider"></li>
            <li><a class="dropdown-item" href="#"><img src="images/usuk-flag.jpg" width="18px" alt="" style="margin-right: 5px;">English</a></li>
          </ul>
        </li>
      </ul>
      <li class="nav-item dropdown d-flex">
      <a class="nav-link dropdown-toggle" style="color: white;" href="#" role="button" data-bs-toggle="dropdown" aria-expanded="false">
        <img src="images/person-icon.png" width="30px" style="border-radius: 50%;" alt="">
        Name
      </a>
      <ul class="dropdown-menu">
        <li><a class="dropdown-item" href="#">Personal Account</a></li>
        <li><hr class="dropdown-divider"></li>
        <li><a class="dropdown-item" href="#">Sign Out</a></li>
      </ul>
      </li>
    </div>
  </div>
</nav>


<div class="collapse" id="navbarToggleExternalContent">
  <div class="bg-light p-4">
    <form class="d-flex">
      <div class="d-grid gap-2 col-3 mx-3">
        <select class="form-select" aria-label="Default select example">
          <option selected>Genre</option>
          <option value="1">One</option>
          <option value="2">Two</option>
          <option value="3">Three</option>
        </select>
      </div>
      <div class="d-grid gap-2 col-3 mx-2">
        <select class="form-select" aria-label="Default select example">
          <option selected>Sorted by</option>
          <option value="1">One</option>
          <option value="2">Two</option>
          <option value="3">Three</option>
        </select>
      </div>
      <div class="d-grid gap-2 col-3 mx-2">
        <input type="text" class="form-control" id="" aria-describedby="emailHelp" placeholder="Name">
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
            for (BookEntity book : list) {
      %>
  <div class="card my-3">
    <div class="row g-0">
      <div class="col-md-4">
        <img src="book_images/<%=book.getImgName()%>" class="img-fluid" style="min-height: 400px" alt="...">
      </div>
      <div class="col-md-8">
        <div class="card-body">
          <h4 class="card-title"><%=book.getName()%></h4>
          <h6 class="author">Author: <%=book.getAuthor_id()%></h6>
          <h6 class="publication">Publication: <%=book.getPublication()%></h6>
          <h6 class="publication-date">Date Of Publication: <%=book.getDateOfPublication()%></h6>
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