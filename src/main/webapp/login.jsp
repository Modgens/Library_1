<%@ page import="dao.implementation.UserDaoImpl" %>
<%--
  Created by IntelliJ IDEA.
  User: Modgen
  Date: 27.08.2022
  Time: 20:09
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
<%@include file="includes/header.jsp"%>
</head>
    <body>
    <input type="hidden" id="status" value="<%= request.getAttribute("status")%>">
        <div class="text-center">
            <form method="post" action="servlet" style="max-width:480px;margin:auto;">
                <h1 class="mb-4 mt-5">Welcome To Library</h1>
                <input type="text" class="form-control mb-2" placeholder="Login" name="login" maxlength="30" required autofocus>
                <input type="password" class="form-control mb-2" placeholder="Password" name="password" required autofocus>
                <input type="submit" value="Sign in" class="btn btn-lg btn-primary btn-block" >
            </form>
            <a href="signUp.jsp">
                <button class="btn btn-lg mt-1" style="color: blue;">
                    Create an account
                </button>
            </a>
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