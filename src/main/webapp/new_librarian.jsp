<%@ taglib uri="/WEB-INF/navbar-tag.tld" prefix="nav" %>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"
         language="java"%>
<%

%>

<!doctype html>
<html lang="en">


<head>
  <%@include file="includes/header.jsp"%>
  <title>Library</title>
</head>
<body>
<input type="hidden" id="status" value="<%= request.getAttribute("status")%>">

<nav:Navbar message="catalog" role='<%=(String)session.getAttribute("role")%>' name='<%=(String) session.getAttribute("name")%>' lang="eng"/>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.1/jquery.min.js"></script>
<div class="container my-5" style="max-width: 30rem;">

  <h5>Enter librarian date:</h5>

  <form action="create_librarian" method="post" style="max-width:480px;margin:auto;">
    <h1 class="mb-4 mt-5">Registration</h1>
    <input type="text" class="form-control mb-2" placeholder="First name" name="first_name" maxlength="30" required autofocus>
    <input type="text" class="form-control mb-2" placeholder="Last name" name="last_name" maxlength="30" required autofocus>
    <input type="text" class="form-control mb-2" placeholder="Login" name="login" maxlength="30" required autofocus>
    <input type="text" id="password" class="form-control mb-2" placeholder="Password" name="password" required autofocus>
    <input type="submit" class="btn btn-lg btn-primary btn-block" value="Create">
  </form>

</div>

<%@include file="includes/footer.jsp"%>
<script src="https://cdn.jsdelivr.net/npm/sweetalert2@7.12.15/dist/sweetalert2.all.min.js"></script>
<link rel='stylesheet' href='https://cdn.jsdelivr.net/npm/sweetalert2@7.12.15/dist/sweetalert2.min.css'>
<script type="text/javascript">
  var status = document.getElementById("status").value;
  if(status=="success") {
    swal("Congrats", "Librarian Created Successfully", "success");
  }
  else if(status=="failed"){
    swal("Sorry", "<%=request.getAttribute("error")%>", "error");
  }
</script>
</body>
</html>