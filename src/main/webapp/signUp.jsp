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
            <h1 class="mb-4 mt-5">Registration</h1>
            <input type="text" class="form-control mb-2" placeholder="First name" name="first_name" maxlength="30" required autofocus>
            <input type="text" class="form-control mb-2" placeholder="Last name" name="last_name" maxlength="30" required autofocus>
            <input type="email" class="form-control mb-2" placeholder="Email" name="email" maxlength="30" required autofocus>
            <input type="password" id="password" class="form-control mb-2" placeholder="Password" name="password" required autofocus>
            <input type="password" id="confirm_password" class="form-control mb-2" placeholder="Repeat password" name="r_password" required autofocus>
            <div id='message' style="text-align: left"></div>
            <input type="submit" class="btn btn-lg btn-primary btn-block" value="Sign up">
            <script>$('#password, #confirm_password').on('keyup', function () {
                if ($('#password').val() == $('#confirm_password').val()) {
                    $('#message').html('Matching').css('color', 'green');
                } else
                    $('#message').html('Not Matching').css('color', 'red');
            });</script>
        </form>

        <a href="login.jsp" style="text-decoration: none;">
            <button class="btn btn-lg mt-1" style="color: blue;">Sign in</button>
        </a>
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
            swal("Sorry", "This Email Is Already Registered", "error");
        }
    </script>
</body>
</html>