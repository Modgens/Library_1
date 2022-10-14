<%@ page import="java.util.List" %>
<%@ page import="dao.implementation.*" %>
<%@ page import="entity.User" %>
<%@ page import="java.util.ResourceBundle" %>
<%@ page import="java.util.Locale" %>
<%@ taglib uri="/WEB-INF/navbar-tag.tld" prefix="nav" %>
<%@ taglib prefix="p" tagdir="/WEB-INF/tags" %>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%
  ResourceBundle rb = null;
  if(session.getAttribute("rb")==null){
    rb = ResourceBundle.getBundle("Localization/Bundle", new Locale("en", "US"));
  } else {
    rb = (ResourceBundle) session.getAttribute("rb");
  }
  String lang = rb.getString("language");

  UserDaoImpl userDao = new UserDaoImpl();
  PersonalInfoDaoImpl personalInfoDao = new PersonalInfoDaoImpl();
  List<User> list = (List<User>) session.getAttribute("user_list");
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

<nav:Navbar message="users" role='<%=(String)session.getAttribute("role")%>' name='<%=(String) session.getAttribute("name")%>' lang="<%=lang%>"/>

<div class="collapse" id="navbarToggleExternalContent">
  <div class="bg-light p-4">
    <form method="post" action="userSearch" class="d-flex">

      <input type="hidden" name="page" value="/users.jsp">
      <div class="d-grid gap-2 col-3 mx-2">
        <input name="first_name" type="text" class="form-control" aria-describedby="emailHelp" placeholder="<%=rb.getString("fName")%>">
      </div>
      <div class="d-grid gap-2 col-3 mx-2">
        <input name="last_name" type="text" class="form-control" aria-describedby="emailHelp" placeholder="<%=rb.getString("lName")%>">
      </div>
      <div class="d-grid gap-2 col-3 mx-2">
        <input name="login" type="text" class="form-control" aria-describedby="emailHelp" placeholder="<%=rb.getString("login")%>">
      </div>
      <div class="d-grid gap-2 col-1 mx-2">
        <button type="submit" class="btn btn-primary my-auto"><%=rb.getString("find")%></button>
      </div>
      <div class="d-grid gap-2 col-1 mx-2">
        <button type="submit" class="btn btn-danger my-auto" <%=list.size()==userDao.getAll().size()?"disabled":""%>><%=rb.getString("reset")%></button></a>
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

<table class="table mt-1">
  <thead class="table-dark">
  <tr>
    <th scope="col">ID</th>
    <th scope="col"><%=rb.getString("fName")%></th>
    <th scope="col"><%=rb.getString("lName")%></th>
    <th scope="col"><%=rb.getString("email")%></th>
    <th scope="col"><%=rb.getString("login")%></th>
    <th scope="col"><%=rb.getString("password")%></th>
    <th scope="col"><%=rb.getString("status")%></th>
  </tr>
  </thead>
  <tbody>

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
    }
    while (i <= end && !list.isEmpty()) {
  %>
  <tr>
    <td><%=list.get(i).getId()%></td>
    <td><%=personalInfoDao.get(list.get(i).getPersonId()).getFirstName()%></td>
    <td><%=personalInfoDao.get(list.get(i).getPersonId()).getLastName()%></td>
    <td><%=list.get(i).getEmail()%></td>
    <td><%=personalInfoDao.get(list.get(i).getPersonId()).getLogin()%></td>
    <td><%=personalInfoDao.get(list.get(i).getPersonId()).getPassword()%></td>
    <td><button type="button" class="btn btn-outline-<%=list.get(i).getStatus().equals("baned")?"success":"danger"%>"><a style="text-decoration: none; color: black" href="${pageContext.request.contextPath}/user_ban?user_id=<%=list.get(i).getId()%>"><%=list.get(i).getStatus().equals("baned")?rb.getString("unban"):rb.getString("ban")%></a></button></td>
  </tr>
  <%
      i++;
    }
    int current_page = 1;
    if(request.getAttribute("page")!=null)
      current_page = (int) request.getAttribute("page");
  %>

  </tbody>
</table>
<%
  if(!list.isEmpty()){
%>
<p:pagination count='<%=String.valueOf((list.size()%15==0)?list.size()/15:list.size()/15+1)%>' current_page='<%=String.valueOf(current_page)%>' link="users.jsp" list_size='<%=String.valueOf(list.size())%>'/>
<%
  }
%>


<%@include file="includes/footer.jsp"%>
</body>
</html>