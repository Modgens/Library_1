<%@ page import="dao.implementation.BookDaoImpl" %>
<%@ page import="java.util.List" %>
<%@ page import="entity.Book" %>
<%@ page import="dao.implementation.AuthorDaoImpl" %>
<%@ page import="dao.implementation.GenreDaoImpl" %>
<%@ page import="entity.Genre" %>
<%@ page import="dao.implementation.PublisherDaoImpl" %>
<%@ page import="java.util.ResourceBundle" %>
<%@ page import="java.util.Locale" %>
<%@ page import="dao.megaEntity.MegaBookDaoImpl" %>
<%@ page import="entity.megaEntity.MegaBook" %>
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

  List<MegaBook> list = (List<MegaBook>) session.getAttribute("catalog_list");
  if(list == null)
    list = MegaBookDaoImpl.getInstance().getAll();

  if(session.getAttribute("role")==null){
    session.setAttribute("role", "guest");
    session.setAttribute("name", rb.getString("guest"));
  }
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
            for (Genre genre : new GenreDaoImpl().getAll()) {
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
        <button type="submit" class="btn btn-danger my-auto" <%=list.size()==MegaBookDaoImpl.getInstance().getAllSize()?"disabled":""%>><%=rb.getString("reset")%></button></a>
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
          <h6 class="author">Author: <%=list.get(i).getAuthor().getAuthorName()%></h6>
          <h6 class="author">Genre: <%=list.get(i).getGenre().getGenreName()%></h6>
          <h6 class="publication">Publication: <%=list.get(i).getPublisher().getPublisherName()%></h6>
          <h6 class="publication-date">Date Of Publication: <%=list.get(i).getDateOfPublication()%> year</h6>
          <%
            if(session.getAttribute("role").equals("user")){
          %>
          <div class="d-flex">
          <form method="post" action="newOrder">
            <input type="hidden" name="user_id" value="<%=session.getAttribute("user_id")%>">
            <input type="hidden" name="book_id" value="<%=list.get(i).getId()%>">
            <input type="hidden" name="orderStatus" value="Wait for order">
            <input type="hidden" name="orderStatusUa" value="Чекає на оформлення">
            <input type="submit" value="Order" class="btn btn-outline-success">
          </form>
          <form method="post" action="newOrder" class="ms-1">
            <input type="hidden" name="user_id" value="<%=session.getAttribute("user_id")%>">
            <input type="hidden" name="book_id" value="<%=list.get(i).getId()%>">
            <input type="hidden" name="orderStatus" value="Wait for reading room">
            <input type="hidden" name="orderStatusUa" value="Чекає для читального залу">
            <input type="submit" value="Take in reading room" class="btn btn-outline-success">
          </form>
          </div>
          <%
            }
          %>
        </div>
      </div>
      <%} else {%>
      <div class="col-md-8">
        <div class="card-body">
          <h4 class="card-title"><a style="text-decoration: none" href="${pageContext.request.contextPath}/book_page?book_id=<%=list.get(i).getId()%>"><%=list.get(i).getNameUa()%></a></h4>
          <h6 class="author">Автор: <%=list.get(i).getAuthor().getAuthorNameUa()%></h6>
          <h6 class="author">Жанр: <%=list.get(i).getGenre().getGenreNameUa()%></h6>
          <h6 class="publication">Публікатор: <%=list.get(i).getPublisher().getPublisherNameUa()%></h6>
          <h6 class="publication-date">Дата публікації: <%=list.get(i).getDateOfPublication()%> рік</h6>
          <%
            if(session.getAttribute("role").equals("user")){
          %>
          <div class="d-flex">
          <form method="post" action="newOrder">
            <input type="hidden" name="user_id" value="<%=session.getAttribute("user_id")%>">
            <input type="hidden" name="book_id" value="<%=list.get(i).getId()%>">
            <input type="hidden" name="orderStatus" value="Wait for order">
            <input type="hidden" name="orderStatusUa" value="Чекає на оформлення">
            <input type="submit" value="Замовити" class="btn btn-outline-success">
          </form>
          <form method="post" action="newOrder" class="ms-1">
            <input type="hidden" name="user_id" value="<%=session.getAttribute("user_id")%>">
            <input type="hidden" name="book_id" value="<%=list.get(i).getId()%>">
            <input type="hidden" name="orderStatus" value="Wait for reading room">
            <input type="hidden" name="orderStatusUa" value="Чекає для читального залу">
            <input type="submit" value="Взяти в читальний зал" class="btn btn-outline-success">
          </form>
          </div>
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

