<%@ page import="java.util.List" %>
<%@ page import="entity.Book" %>
<%@ page import="entity.Genre" %>
<%@ page import="entity.Librarian" %>
<%@ page import="dao.implementation.*" %>
<%@ taglib uri="/WEB-INF/navbar-tag.tld" prefix="nav" %>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"
         language="java"%>
<%
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

<nav:Navbar message="librarians" role='<%=(String)session.getAttribute("role")%>' name='<%=(String) session.getAttribute("name")%>' lang="eng"/>

<table class="table mt-1">
  <thead class="table-dark">
  <tr>
    <th scope="col">ID</th>
    <th scope="col">First Name</th>
    <th scope="col">Last Name</th>
    <th scope="col">Login</th>
    <th scope="col">Password</th>
    <th scope="col">Delete</th>
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
    <td><button type="button" class="btn btn-outline-danger"><a style="text-decoration: none; color: black" href="${pageContext.request.contextPath}/delete_librarian?librarian_id=<%=librarian.getId()%>">Delete</a></button></td>
  </tr>
  <%}
  }
  %>
  </tbody>
</table>

<div class="position-absolute bottom-0 end-0">
  <button type="button" class="btn btn-danger m-5" >
    <a style="text-decoration: none; color: aliceblue;" href="new_librarian.jsp">
      <h2>+ New Librarian</h2>
    </a>
  </button>
</div>


<%@include file="includes/footer.jsp"%>
</body>
</html>