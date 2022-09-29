<%@ page import="dao.implementation.*" %>
<%@ page import="entity.User" %>
<%@ page import="entity.PersonalInfo" %>
<%@ taglib uri="/WEB-INF/navbar-tag.tld" prefix="nav" %>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%
  UserDaoImpl userDao = new UserDaoImpl();
  PersonalInfoDaoImpl personalInfoDao = new PersonalInfoDaoImpl();
  User user = userDao.get((long)session.getAttribute("user_id"));
  PersonalInfo personalInfo = personalInfoDao.get(user.getPersonId());
%>

<!doctype html>
<html lang="en">


<head>
  <%@include file="includes/header.jsp"%>
  <title>Library</title>
</head>
<body>
<input type="hidden" id="status" value="<%= request.getAttribute("status")%>">

<nav:Navbar message="" role='<%=(String)session.getAttribute("role")%>' name='<%=(String) session.getAttribute("name")%>' lang="eng"/>

<div class="card w-50 mt-3 mx-auto">
  <div class="card-body">
    <h5 class="card-title"><%=personalInfo.getFirstName()+" "+personalInfo.getLastName()%></h5>
    <p class="card-text"><%=personalInfo.getLogin()%></p>
    <p class="card-text"><%=user.getEmail()%></p>
    <p class="card-text"><%=personalInfo.getPassword()%></p>
    <a href="${pageContext.request.contextPath}/getSubscription?book_id=<%=user.getId()%>"></a>

  </div>
</div>




<%@include file="includes/footer.jsp"%>
<script src="https://cdn.jsdelivr.net/npm/sweetalert2@7.12.15/dist/sweetalert2.all.min.js"></script>
<link rel='stylesheet' href='https://cdn.jsdelivr.net/npm/sweetalert2@7.12.15/dist/sweetalert2.min.css'>
</body>
</html>