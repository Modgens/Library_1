<%@ page import="java.util.ResourceBundle" %>
<%@ page import="java.util.Locale" %>
<%@ taglib uri="/WEB-INF/navbar-tag.tld" prefix="nav" %>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%
  ResourceBundle rb = null;
  if(session.getAttribute("rb")==null){
    rb = ResourceBundle.getBundle("Localization/Bundle", new Locale("en", "US"));
  } else {
    rb = (ResourceBundle) session.getAttribute("rb");
  }
  String lang = rb.getString("language");
%>

<!doctype html>
<html lang="en">


<head>
  <%@include file="includes/header.jsp"%>
  <title>Library</title>
</head>
<body>
<input type="hidden" id="status" value="<%= request.getAttribute("status")%>">

<nav:Navbar message="catalog" role='<%=(String)session.getAttribute("role")%>' name='<%=(String) session.getAttribute("name")%>' lang="<%=lang%>"/>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.1/jquery.min.js"></script>
<div class="container my-5" style="max-width: 30rem;">

  <form action="create_librarian" method="post" style="max-width:480px;margin:auto;">
    <h1 class="mb-4 mt-5"><%=rb.getString("eld")%></h1>
    <input type="text" class="form-control mb-2" placeholder="<%=rb.getString("fName")%>" name="first_name" maxlength="30" required autofocus>
    <input type="text" class="form-control mb-2" placeholder="<%=rb.getString("lName")%>" name="last_name" maxlength="30" required autofocus>
    <input type="text" class="form-control mb-2" placeholder="<%=rb.getString("login")%>" name="login" maxlength="30" required autofocus>
    <input type="text" id="password" class="form-control mb-2" placeholder="<%=rb.getString("password")%>" name="password" required autofocus>
    <input type="submit" class="btn btn-primary" value="<%=rb.getString("create")%>">
  </form>
</div>

<%@include file="includes/footer.jsp"%>
<script src="https://cdn.jsdelivr.net/npm/sweetalert2@7.12.15/dist/sweetalert2.all.min.js"></script>
<link rel='stylesheet' href='https://cdn.jsdelivr.net/npm/sweetalert2@7.12.15/dist/sweetalert2.min.css'>
<script type="text/javascript">
  var status = document.getElementById("status").value;
  if(status=="success") {
    swal("Congrats", "Librarian Created Successfully | Бібліотекарь успішно створенний", "success");
  }
  else if(status=="failed"){
    swal("Sorry", "<%=request.getAttribute("error")%>", "error");
  }
</script>
</body>
</html>