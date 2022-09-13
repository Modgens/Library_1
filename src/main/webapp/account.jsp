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
      <button type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav" aria-controls="navbarNav"
        aria-expanded="false" aria-label="Toggle navigation" class="navbar-toggler">
        <div style="color:#fff">Menu</div>
      </button>
      <div class="collapse navbar-collapse" id="navbarNav">
        <ul class="navbar-nav">
          <li class="nav-item active">
            <a href="index.jsp" class="nav-link">Home</a>
          </li>
          <li class="nav-item active">
            <a href="catalog.jsp" class="nav-link">Catalog</a>
          </li>
          <li class="nav-item active">
            <a href="account.jsp" class="nav-link active">Personal account</a>
          </li>
        </ul>
      </div>
      <div class="d-flex align-items-center">
        <img src="images/the-ukraine-flag.jpg" width="17px" alt="">
        <a style="color:#fff; text-decoration: none; margin-left: 3px;" href="#">UA</a>
        <div style="color:#fff; margin:10px;" >|</div>
        <img src="images/usuk-flag.jpg" width="18px" alt="">
        <a style="color:#fff;  text-decoration: none; margin-left: 2px;" href="#">ENG</a>
      </div>
    </div>
  </nav>
  <div class="container" style="text-align:center">
    <h1>Welcome in our Library</h1>
    <div>

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