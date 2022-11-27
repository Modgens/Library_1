<%@ page import="java.util.List" %>
<%@ page import="dao.implementation.*" %>
<%@ page import="entity.User" %>
<%@ page import="entity.Subscriptions" %>
<%@ page import="java.util.ResourceBundle" %>
<%@ page import="java.util.Locale" %>
<%@ page import="dao.Punisher" %>
<%@ page import="entity.megaEntity.MegaUser" %>
<%@ page import="dao.megaEntity.MegaUserDaoImpl" %>
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

  MegaUserDaoImpl userDao = MegaUserDaoImpl.getInstance();
  PersonalInfoDaoImpl personalInfoDao = new PersonalInfoDaoImpl();
  SubscriptionsDaoImpl subscriptionsDao = new SubscriptionsDaoImpl();
  UserOrdersDaoImpl userOrdersDao = new UserOrdersDaoImpl();
  List<MegaUser> list =  (List<MegaUser>) session.getAttribute("user_list");
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

<nav:Navbar message="users_librarian" role='<%=(String)session.getAttribute("role")%>' name='<%=(String) session.getAttribute("name")%>' lang="<%=lang%>"/>

<div class="collapse" id="navbarToggleExternalContent">
  <div class="bg-light p-4">
    <form method="post" action="userSearch" class="d-flex">

      <input type="hidden" name="page" value="/users_librarian.jsp">
      <div class="d-grid gap-2 col-3 mx-2">
        <input name="first_name" type="text" class="form-control" aria-describedby="emailHelp" placeholder="<%=rb.getString("fName")%>" value="<%=session.getAttribute("selectedFirstName")!=null?session.getAttribute("selectedFirstName"):""%>">
      </div>
      <div class="d-grid gap-2 col-3 mx-2">
        <input name="last_name" type="text" class="form-control" aria-describedby="emailHelp" placeholder="<%=rb.getString("lName")%>" value="<%=session.getAttribute("selectedLastName")!=null?session.getAttribute("selectedLastName"):""%>">
      </div>
      <div class="d-grid gap-2 col-3 mx-2">
        <input name="login" type="text" class="form-control" aria-describedby="emailHelp" placeholder="<%=rb.getString("login")%>" value="<%=session.getAttribute("selectedLogin")!=null?session.getAttribute("selectedLogin"):""%>">
      </div>
      <div class="d-grid gap-2 col-1 mx-2">
        <button type="submit" class="btn btn-primary my-auto"><%=rb.getString("find")%></button>
      </div>
      <div class="d-grid gap-2 col-1 mx-2">
        <button type="button" class="btn btn-danger my-auto" ><a style="text-decoration: none; color: white" href="${pageContext.request.contextPath}/reset?reset_page=users"><%=rb.getString("reset")%></a></button>
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
    <th scope="col"><%=rb.getString("allFine")%></th>
    <th scope="col"><%=rb.getString("login")%></th>
    <th scope="col"><%=rb.getString("ss")%></th>
    <th scope="col"><%=rb.getString("se")%></th>
    <th scope="col"><%=rb.getString("oc")%></th>
  </tr>
  </thead>
  <tbody>

  <%
    Punisher punisher = Punisher.getInstance();
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
    Subscriptions subscriptions = null;
    while (i <= end && !list.isEmpty()) {
      int count = userOrdersDao.countOrderedOneUser(list.get(i).getId());
      subscriptions = subscriptionsDao.getFromUserDao(list.get(i).getId());
  %>
  <tr>
    <td><%=list.get(i).getId()%></td>
    <td><%=list.get(i).getPersonalInfo().getFirstName()%></td>
    <td><%=list.get(i).getPersonalInfo().getLastName()%></td>
    <td><%=list.get(i).getEmail()%></td>
    <td><%=punisher.usersFineSum(list.get(i).getId())%> ₴</td>
    <td><%=list.get(i).getPersonalInfo().getLogin()%></td>
    <td><%=subscriptions!=null?subscriptions.getStartDate():rb.getString("haven'tSub")%></td>
    <td><%=subscriptions!=null?subscriptions.getEndDate():rb.getString("haven'tSub")%></td>
    <td><%=count%></td>
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
<p:pagination count='<%=String.valueOf((list.size()%15==0)?list.size()/15:list.size()/15+1)%>' current_page='<%=String.valueOf(current_page)%>' link="users_librarian.jsp" list_size='<%=String.valueOf(list.size())%>'/>
<% } else {%>
<p2 style="font-size: 45px; font-weight: bold; text-align: center"><%=rb.getString("eList")%></p2>
<%}%>

<%@include file="includes/footer.jsp"%>
</body>
</html>