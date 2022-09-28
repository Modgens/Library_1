<%@ page import="java.util.List" %>
<%@ page import="dao.implementation.*" %>
<%@ page import="entity.User" %>
<%@ taglib uri="/WEB-INF/navbar-tag.tld" prefix="nav" %>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"
         language="java"%>
<%
  UserDaoImpl userDao = new UserDaoImpl();
  PersonalInfoDaoImpl personalInfoDao = new PersonalInfoDaoImpl();
  List<User> list = (List<User>) request.getAttribute("list");
  if(list == null)
    list = userDao.getAll();
%>

<!doctype html>
<html lang="en">


<head>
  <%@include file="includes/header.jsp"%>
  <title>Library</title>
</head>
<body>

<nav:Navbar message="users" role='<%=(String)session.getAttribute("role")%>' name='<%=(String) session.getAttribute("name")%>' lang="eng"/>

<table class="table mt-1">
  <thead class="table-dark">
  <tr>
    <th scope="col">ID</th>
    <th scope="col">First Name</th>
    <th scope="col">Last Name</th>
    <th scope="col">Email</th>
    <th scope="col">Login</th>
    <th scope="col">Password</th>
    <th scope="col">Status</th>
  </tr>
  </thead>
  <tbody>

  <%
    if(!list.isEmpty()){
      for (User user : list) {
  %>
  <tr>
    <td><%=user.getId()%></td>
    <td><%=personalInfoDao.get(user.getPersonId()).getFirstName()%></td>
    <td><%=personalInfoDao.get(user.getPersonId()).getLastName()%></td>
    <td><%=user.getEmail()%></td>
    <td><%=personalInfoDao.get(user.getPersonId()).getLogin()%></td>
    <td><%=personalInfoDao.get(user.getPersonId()).getPassword()%></td>
    <%
      String buttonColor = "";
      String method = "";
      if(user.getStatus().equals("baned")){
        buttonColor = "success";
        method = "unban";
      } else {
        buttonColor = "danger";
        method = "ban";
      }
    %>
    <td><button type="button" class="btn btn-outline-<%=buttonColor%>"><a style="text-decoration: none; color: black" href="${pageContext.request.contextPath}/user_ban?user_id=<%=user.getId()%>"><%=method%></a></button></td>
  </tr>
  <%}
  }
  %>
  </tbody>
</table>

<%@include file="includes/footer.jsp"%>
</body>
</html>