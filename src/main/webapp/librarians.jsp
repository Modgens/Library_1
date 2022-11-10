<%@ page import="java.util.List" %>
<%@ page import="entity.Librarian" %>
<%@ page import="dao.implementation.*" %>
<%@ page import="java.util.ResourceBundle" %>
<%@ page import="java.util.Locale" %>
<%@ page import="entity.megaEntity.MegaLibrarian" %>
<%@ page import="dao.megaEntity.MegaLibrarianDaoImpl" %>
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

  List<MegaLibrarian> list = (List<MegaLibrarian>) request.getAttribute("list");
  if(list == null)
    list = MegaLibrarianDaoImpl.getInstance().getAll();
%>

<!doctype html>
<html lang="en">

<head>
  <%@include file="includes/header.jsp"%>
  <title>Library</title>
</head>
<body>

<nav:Navbar message="librarians" role='<%=(String)session.getAttribute("role")%>' name='<%=(String) session.getAttribute("name")%>' lang="<%=lang%>"/>

<table class="table mt-1">
  <thead class="table-dark">
  <tr>
    <th scope="col">ID</th>
    <th scope="col"><%=rb.getString("fName")%></th>
    <th scope="col"><%=rb.getString("lName")%></th>
    <th scope="col"><%=rb.getString("login")%></th>
    <th scope="col"><%=rb.getString("password")%></th>
    <th scope="col"><%=rb.getString("delete")%></th>
  </tr>
  </thead>
  <tbody>

  <%
    if(!list.isEmpty()){
      for (MegaLibrarian librarian : list) {
  %>
  <tr>
    <td><%=librarian.getId()%></td>
    <td><%=librarian.getPersonalInfo().getFirstName()%></td>
    <td><%=librarian.getPersonalInfo().getLastName()%></td>
    <td><%=librarian.getPersonalInfo().getLogin()%></td>
    <td><%=librarian.getPersonalInfo().getPassword()%></td>
    <form method="post" action="delete_librarian">
      <input type="hidden" name="librarian_id" value="<%=librarian.getId()%>">
      <td><input type="submit" class="btn btn-outline-danger" value="<%=rb.getString("delete")%>"></td>
    </form>
  </tr>
  <%}
  }
  %>
  </tbody>
</table>

<div class="position-absolute bottom-0 end-0">
  <button type="button" class="btn btn-danger m-5" >
    <a style="text-decoration: none; color: aliceblue;" href="new_librarian.jsp">
      <h2><%=rb.getString("newL")%></h2>
    </a>
  </button>
</div>


<%@include file="includes/footer.jsp"%>
</body>
</html>