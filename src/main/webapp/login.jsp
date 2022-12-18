<%@ page import="dao.implementation.UserDaoImpl" %>
<%@ page import="java.util.ResourceBundle" %>
<%@ page import="java.util.Locale" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%--
  Created by IntelliJ IDEA.
  User: Modgen
  Date: 27.08.2022
  Time: 20:09
  To change this template use File | Settings | File Templates.
--%>

<%
    if(session.getAttribute("rb")==null){
        session.setAttribute("rb", ResourceBundle.getBundle("Localization/Bundle", new Locale("en", "US")));
    }
%>
<c:set var="role" scope="session" value="guest"/>
<c:set var="name" scope="session" value="Guest"/>

<%@ page contentType="text/html;charset=UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<%@include file="includes/header.jsp"%>
</head>
    <body>
    <input type="hidden" id="status" value="<%=request.getAttribute("status")%>">
        <div class="text-center">
            <form method="post" action="servlet" style="max-width:480px;margin:auto;">
                <h1 class="mb-4 mt-5">${rb.getString("welcome")}</h1>
                <input type="text" class="form-control mb-2" placeholder="${rb.getString("login")}" name="login" maxlength="30" required autofocus>
                <input type="password" class="form-control mb-2" placeholder="${rb.getString("password")}" name="password" required autofocus>
                <input type="submit" value="${rb.getString("signIn")}" class="btn btn-lg btn-primary btn-block" >
            </form>
            <a href="signUp.jsp">
                <button class="btn btn-lg mt-1" style="color: blue;">
                    ${rb.getString("createAnAccount")}
                </button>
            </a>
            <br>
            <a href="catalog.jsp">
                <button class="btn btn-lg mt-1" style="color: green;">
                    ${rb.getString("guestIn")}
                </button>
            </a>
        </div>
    <div style="position: fixed; top: 10px; left: 10px;">
        <a href="lang?language=ua&path=login.jsp"><img width="30px" src="images/the-ukraine-flag.jpg" alt=""></a>
        <a href="lang?language=en&path=login.jsp"><img width="30px" src="images/usuk-flag.jpg" alt=""></a>
    </div>

        <%@include file="includes/footer.jsp"%>
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@7.12.15/dist/sweetalert2.all.min.js"></script>
    <link rel='stylesheet' href='https://cdn.jsdelivr.net/npm/sweetalert2@7.12.15/dist/sweetalert2.min.css'>
    <script type="text/javascript">
        var status = document.getElementById("status").value;
        if(status=="failed") {
            swal("Sorry", "<%=request.getAttribute("error")%>", "error");
        }
    </script>
    </body>
</html>