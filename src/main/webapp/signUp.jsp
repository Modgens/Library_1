<%@ page import="java.util.ResourceBundle" %>
<%@ page import="java.util.Locale" %><%

    ResourceBundle rb = null;
    if(session.getAttribute("rb")==null){
        rb = ResourceBundle.getBundle("Localization/Bundle", new Locale("en", "US"));//lang by default
    } else {
        rb = (ResourceBundle) session.getAttribute("rb");
    }

%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <%@include file="includes/header.jsp"%>
</head>
<body>
<input type="hidden" id="status" value="<%= request.getAttribute("status")%>">
    <div class="text-center">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.1/jquery.min.js"></script>
        <form action="signUpServlet" method="post" style="max-width:480px;margin:auto;">
            <h1 class="mb-4 mt-5"><%=rb.getString("registration")%></h1>
            <input type="text" class="form-control mb-2" placeholder="<%=rb.getString("fName")%>" name="first_name" maxlength="30" required autofocus>
            <input type="text" class="form-control mb-2" placeholder="<%=rb.getString("lName")%>" name="last_name" maxlength="30" required autofocus>
            <input type="email" class="form-control mb-2" placeholder="<%=rb.getString("email")%>" name="email" maxlength="30" required autofocus>
            <input type="text" class="form-control mb-2" placeholder="<%=rb.getString("login")%>" name="login" maxlength="30" required autofocus>
            <input type="password" id="password" class="form-control mb-2" placeholder="<%=rb.getString("password")%>" name="password" required autofocus>
            <input type="password" id="confirm_password" class="form-control mb-2" placeholder="<%=rb.getString("rPassword")%>" name="r_password" required autofocus>
            <div id='message' style="text-align: left"></div>
            <input type="submit" class="btn btn-lg btn-primary btn-block" value="<%=rb.getString("signUp")%>">
            <script>$('#password, #confirm_password').on('keyup', function () {
                if ($('#password').val() === $('#confirm_password').val()) {
                    $('#message').html('<%=rb.getString("matching+")%>').css('color', 'green');
                } else
                    $('#message').html('<%=rb.getString("matching-")%>').css('color', 'red');
            });</script>
        </form>

        <a href="login.jsp" style="text-decoration: none;">
            <button class="btn btn-lg mt-1" style="color: blue;"><%=rb.getString("signIn")%></button>
        </a>
    </div>
<div style="position: fixed; top: 10px; left: 10px;">
    <a href="lang?language=ua&path=signUp.jsp"><img width="30px" src="images/the-ukraine-flag.jpg" alt=""></a>
    <a href="lang?language=en&path=signUp.jsp"><img width="30px" src="images/usuk-flag.jpg" alt=""></a>
</div>
<%@include file="includes/footer.jsp"%>
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@7.12.15/dist/sweetalert2.all.min.js"></script>
    <link rel='stylesheet' href='https://cdn.jsdelivr.net/npm/sweetalert2@7.12.15/dist/sweetalert2.min.css'>
    <script type="text/javascript">
        var status = document.getElementById("status").value;
        if(status=="success") {
            swal("Congrats", "Account Created Successfully", "success");
        }
        else if(status=="failed"){
            swal("Sorry", '<%=request.getAttribute("error")%>', "error");
        }
    </script>
</body>
</html>