<%@ page import="dao.implementation.*" %>
<%@ page import="entity.User" %>
<%@ page import="entity.PersonalInfo" %>
<%@ page import="entity.Subscriptions" %>
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

  UserDaoImpl userDao = new UserDaoImpl();
  PersonalInfoDaoImpl personalInfoDao = new PersonalInfoDaoImpl();
  User user = userDao.get((long)session.getAttribute("user_id"));
  PersonalInfo personalInfo = personalInfoDao.get(user.getPersonId());
  SubscriptionsDaoImpl subscriptionsDao = new SubscriptionsDaoImpl();
%>

<!doctype html>
<html lang="en">


<head>
  <%@include file="includes/header.jsp"%>
  <title>Library</title>
</head>
<body>
<input type="hidden" id="status" value="<%= request.getAttribute("status")%>">
<nav:Navbar message="" role='<%=(String)session.getAttribute("role")%>' name='<%=(String) session.getAttribute("name")%>' lang="<%=lang%>"/>
<div class="card w-50 mt-3 mx-auto">
  <div class="card-body">
    <h5 class="card-title"><%=personalInfo.getFirstName()+" "+personalInfo.getLastName()%></h5>
    <p class="card-text"><%=rb.getString("login")%>: <%=personalInfo.getLogin()%></p>
    <p class="card-text"><%=rb.getString("email")%>: <%=user.getEmail()%></p>
    <p class="card-text"><%=rb.getString("password")%>: <%=personalInfo.getPassword()%></p>

    <%
      if(subscriptionsDao.getFromUserDao(user.getId())==null){
        %>
    <form action="getSubscription" method="post">
      <input type="hidden" name="user_id" value="<%=user.getId()%>">
      <input type="submit" class="btn btn-outline-success" value="<%=rb.getString("getSub")%>">
    </form>
      <%
      }else{
        %>
    <p><%=rb.getString("subEnd")%> <%=subscriptionsDao.getFromUserDao(user.getId()).getEndDate()%></p>
    <%
      }
    %>


  </div>
</div>

<%@include file="includes/footer.jsp"%>
<script src="https://cdn.jsdelivr.net/npm/sweetalert2@7.12.15/dist/sweetalert2.all.min.js"></script>
<link rel='stylesheet' href='https://cdn.jsdelivr.net/npm/sweetalert2@7.12.15/dist/sweetalert2.min.css'>
<script type="text/javascript">
  var status = document.getElementById("status").value;
  if(status=="success") {
    swal("Congrats", "You Successfully Get Subscription", "success");
  }
</script>
<script src="https://cdn.jsdelivr.net/npm/sweetalert2@7.12.15/dist/sweetalert2.all.min.js"></script>
<link rel='stylesheet' href='https://cdn.jsdelivr.net/npm/sweetalert2@7.12.15/dist/sweetalert2.min.css'>
</body>
</html>