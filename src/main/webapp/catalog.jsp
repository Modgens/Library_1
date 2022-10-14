<%@ page import="dao.implementation.BookDaoImpl" %>
<%@ page import="java.util.List" %>
<%@ page import="entity.Book" %>
<%@ page import="dao.implementation.AuthorDaoImpl" %>
<%@ page import="dao.implementation.GenreDaoImpl" %>
<%@ page import="entity.Genre" %>
<%@ page import="dao.implementation.PublisherDaoImpl" %>
<%@ page import="java.util.ResourceBundle" %>
<%@ page import="java.util.Locale" %>
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

  BookDaoImpl bookDaoImpl = new BookDaoImpl();
  AuthorDaoImpl authorDao = new AuthorDaoImpl();
  GenreDaoImpl genreDao = new GenreDaoImpl();
  PublisherDaoImpl publisherDao = new PublisherDaoImpl();
  List<Book> list = (List<Book>) session.getAttribute("list");
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
<input type="hidden" id="status" value="<%= request.getAttribute("status")%>">

<nav:Navbar message="catalog" role='<%=(String)session.getAttribute("role")%>' name='<%=(String) session.getAttribute("name")%>' lang='<%=lang%>'/>

<div class="collapse" id="navbarToggleExternalContent">
  <div class="bg-light p-4">
    <form method="post" action="sort" class="d-flex">

      <input type="hidden" name="page" value="/catalog.jsp">

      <div class="d-grid gap-2 col-2 mx-3">
        <select name="genre" class="form-select" aria-label="Default select example">
          <option value="" selected><%=rb.getString("genre")%></option>
          <%
            for (Genre genre : genreDao.getAll()) {
          %>
          <option value="<%=genre.getId()%>"><%=lang.equals("en")?genre.getGenreName():genre.getGenreNameUa()%></option>
            <%
              }
            %>
        </select>
      </div>
      <div class="d-grid gap-2 col-2 mx-2">
        <select name="sort" class="form-select" aria-label="Default select example">
          <option value="" selected><%=rb.getString("sortedBy")%></option>
          <option value="name"><%=rb.getString("name")%></option>
          <option value="author"><%=rb.getString("author")%></option>
          <option value="publisher"><%=rb.getString("publisher")%></option>
          <option value="date"><%=rb.getString("dateOfPublication")%></option>
        </select>
      </div>
      <div class="d-grid gap-2 col-2 mx-2">
        <input name="book" type="text" class="form-control" aria-describedby="emailHelp" placeholder="<%=rb.getString("bName")%>">
      </div>
      <div class="d-grid gap-2 col-2 mx-2">
        <input name="author" type="text" class="form-control" aria-describedby="emailHelp" placeholder="<%=rb.getString("aName")%>">
      </div>
      <div class="d-grid gap-2 col-1 mx-2">
        <button type="submit" class="btn btn-primary my-auto"><%=rb.getString("find")%></button>
      </div>
      <div class="d-grid gap-2 col-1 mx-2">
        <button type="submit" class="btn btn-danger my-auto" <%=list.size()==bookDaoImpl.getAll().size()?"disabled":""%>><%=rb.getString("reset")%></button></a>
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


<div class="container">
  <%
    int i = 0;
    int end = 14;
    if(!list.isEmpty()){
      if(list.size()<=15){
        end = list.size()-1;
      } else if(request.getAttribute("firstObj")!=null) {
          i = (int) request.getAttribute("firstObj") - 1;
          end = (int) request.getAttribute("lastObj") - 1;
        }
      while (i <= end) {
  %>
  <div class="card my-3">
    <div class="row g-0">
      <div class="col-md-4">
        <img src="book_images/<%=list.get(i).getImgName()%>" class="img-fluid" style="min-height: 400px" alt="...">
      </div>
      <%
        if(lang.equals("en")){
      %>
      <div class="col-md-8">
        <div class="card-body">
          <h4 class="card-title"><a style="text-decoration: none" href="${pageContext.request.contextPath}/book_page?book_id=<%=list.get(i).getId()%>"><%=list.get(i).getName()%></a></h4>
          <h6 class="author">Author: <%=authorDao.get(list.get(i).getAuthorId()).getAuthorName()%></h6>
          <h6 class="author">Genre: <%=genreDao.get(list.get(i).getGenreId()).getGenreName()%></h6>
          <h6 class="publication">Publication: <%=publisherDao.get(list.get(i).getPublicationId()).getPublisherName()%></h6>
          <h6 class="publication-date">Date Of Publication: <%=list.get(i).getDateOfPublication()%> year</h6>
          <%
            if(session.getAttribute("role").equals("user")){
          %>
          <a href="${pageContext.request.contextPath}/newOrder?user_id=<%=session.getAttribute("user_id")%>&book_id=<%=list.get(i).getId()%>&orderStatus=Wait for order&orderStatusUa=Чекає на оформлення" class="btn btn-outline-success ">Order</a>
          <a href="${pageContext.request.contextPath}/newOrder?user_id=<%=session.getAttribute("user_id")%>&book_id=<%=list.get(i).getId()%>&orderStatus=Wait for reading room&orderStatusUa=Чекає для читального залу" class="btn btn-outline-success ">Take in reading room</a>
          <%
            }
          %>
        </div>
      </div>
      <%} else {%>
      <div class="col-md-8">
        <div class="card-body">
          <h4 class="card-title"><a style="text-decoration: none" href="${pageContext.request.contextPath}/book_page?book_id=<%=list.get(i).getId()%>"><%=list.get(i).getNameUa()%></a></h4>
          <h6 class="author">Автор: <%=authorDao.get(list.get(i).getAuthorId()).getAuthorNameUa()%></h6>
          <h6 class="author">Жанр: <%=genreDao.get(list.get(i).getGenreId()).getGenreNameUa()%></h6>
          <h6 class="publication">Публікатор: <%=publisherDao.get(list.get(i).getPublicationId()).getPublisherNameUa()%></h6>
          <h6 class="publication-date">Дата публікації: <%=list.get(i).getDateOfPublication()%> рік</h6>
          <%
            if(session.getAttribute("role").equals("user")){
          %>
          <a href="${pageContext.request.contextPath}/newOrder?user_id=<%=session.getAttribute("user_id")%>&book_id=<%=list.get(i).getId()%>&orderStatus=Wait for order&orderStatusUa=Чекаю на оформлення" class="btn btn-outline-success ">Замовити</a>
          <a href="${pageContext.request.contextPath}/newOrder?user_id=<%=session.getAttribute("user_id")%>&book_id=<%=list.get(i).getId()%>&orderStatus=Wait for reading room&orderStatusUa=Чекаю для читального залу" class="btn btn-outline-success ">Взяти в читальний зал</a>
          <%
            }
          %>
        </div>
      </div>
      <%}%>
    </div>
  </div>
  <%
        i++;
    }
    }
    int current_page = 1;
    if(request.getAttribute("page")!=null)
      current_page = (int) request.getAttribute("page");
  %>

  <%
    if(!list.isEmpty()){
  %>
  <p:pagination count='<%=String.valueOf((list.size()%15==0)?list.size()/15:list.size()/15+1)%>' current_page='<%=String.valueOf(current_page)%>' link="catalog.jsp" list_size='<%=String.valueOf(list.size())%>'/>
  <% } else {%>
  <p2 style="font-size: 45px; font-weight: bold; text-align: center"><%=rb.getString("eList")%></p2>
  <%}%>

</div>
<%@include file="includes/footer.jsp"%>
<script src="https://cdn.jsdelivr.net/npm/sweetalert2@7.12.15/dist/sweetalert2.all.min.js"></script>
<link rel='stylesheet' href='https://cdn.jsdelivr.net/npm/sweetalert2@7.12.15/dist/sweetalert2.min.css'>
<script type="text/javascript">
  var status = document.getElementById("status").value;
  if(status=="success") {
    swal("Congrats", "Book Order Successfully", "success");
  }
  else if(status=="failed"){
    swal("Sorry", "<%=request.getAttribute("error")%>", "error");
  }
</script>
</body>
</html>

