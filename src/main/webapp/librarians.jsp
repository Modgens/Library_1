<%@ page import="java.util.List" %>
<%@ page import="entity.Librarian" %>
<%@ page import="dao.implementation.*" %>
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

  LibrarianDaoImpl librarianDao = new LibrarianDaoImpl();
  PersonalInfoDaoImpl personalInfoDao = new PersonalInfoDaoImpl();
  List<Librarian> list = (List<Librarian>) request.getAttribute("list");
  if(list == null)
    list = librarianDao.getAll();
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
      for (Librarian librarian : list) {
  %>
  <tr>
    <td><%=librarian.getId()%></td>
    <td><%=personalInfoDao.get(librarian.getPersonId()).getFirstName()%></td>
    <td><%=personalInfoDao.get(librarian.getPersonId()).getLastName()%></td>
    <td><%=personalInfoDao.get(librarian.getPersonId()).getLogin()%></td>
    <td><%=personalInfoDao.get(librarian.getPersonId()).getPassword()%></td>
    <td><button type="button" class="btn btn-outline-danger"><a style="text-decoration: none; color: black" href="${pageContext.request.contextPath}/delete_librarian?librarian_id=<%=librarian.getId()%>"><%=rb.getString("delete")%></a></button></td>
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