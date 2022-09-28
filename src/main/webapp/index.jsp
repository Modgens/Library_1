<%@ taglib uri="/WEB-INF/navbar-tag.tld" prefix="nav" %>
<!doctype html>
<html lang="en">

<head>
  <%@include file="includes/header.jsp"%>
  <title>Library</title>
</head>
<body>

<nav:Navbar message="home" role='<%=(String)session.getAttribute("role")%>' name='<%=(String) session.getAttribute("name")%>'/>
<h1><%=session.getAttribute("role")%></h1>
<h2><%=session.getId()%></h2>

    </div>
  </div>
<%@include file="includes/footer.jsp"%>
</body>
</html>