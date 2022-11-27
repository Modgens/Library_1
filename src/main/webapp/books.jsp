<%@ page import="dao.implementation.BookDaoImpl" %>
<%@ page import="java.util.List" %>
<%@ page import="entity.Book" %>
<%@ page import="dao.implementation.AuthorDaoImpl" %>
<%@ page import="dao.implementation.GenreDaoImpl" %>
<%@ page import="entity.Genre" %>
<%@ page import="dao.implementation.PublisherDaoImpl" %>
<%@ page import="java.util.ResourceBundle" %>
<%@ page import="java.util.Locale" %>
<%@ page import="dao.megaEntity.MegaBookDaoImpl" %>
<%@ page import="entity.megaEntity.MegaBook" %>
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

    List<MegaBook> list = (List<MegaBook>) session.getAttribute("books_list");
    if(list == null)
        list = MegaBookDaoImpl.getInstance().getAll();
%>

<!doctype html>
<html lang="en">

<head>
    <%@include file="includes/header.jsp"%>
    <title>Library</title>
</head>
<body>

<nav:Navbar message="books" role='<%=(String)session.getAttribute("role")%>' name='<%=(String) session.getAttribute("name")%>' lang="<%=lang%>"/>

<div class="collapse" id="navbarToggleExternalContent">
    <div class="bg-light p-4">
        <form method="post" action="sort" class="d-flex">

            <input type="hidden" name="page" value="/books.jsp">

            <div class="d-grid gap-2 col-2 mx-3">
                <select name="genre" class="form-select" aria-label="Default select example">
                    <option value= "" <%=session.getAttribute("selectedGenre")==null?"selected":""%>><%=rb.getString("genre")%></option>
                    <%
                        for (Genre genre : new GenreDaoImpl().getAll()) {
                    %>
                    <option value="<%=genre.getId()%>" <%=session.getAttribute("selectedGenre")!=null&&genre.getId()==(long)session.getAttribute("selectedGenre")?"selected":""%>><%=lang.equals("en")?genre.getGenreName():genre.getGenreNameUa()%></option>
                    <%
                        }
                    %>
                </select>
            </div>
            <div class="d-grid gap-2 col-2 mx-2">
                <select name="sort" class="form-select" aria-label="Default select example">
                    <option value="" <%=session.getAttribute("selectedSorting")==null?"selected":""%>><%=rb.getString("sortedBy")%></option>
                    <option value="name" <%=session.getAttribute("selectedSorting")!=null&&session.getAttribute("selectedSorting").equals("name")?"selected":""%>><%=rb.getString("name")%></option>
                    <option value="author" <%=session.getAttribute("selectedSorting")!=null&&session.getAttribute("selectedSorting").equals("author")?"selected":""%>><%=rb.getString("author")%></option>
                    <option value="publisher" <%=session.getAttribute("selectedSorting")!=null&&session.getAttribute("selectedSorting").equals("publisher")?"selected":""%>><%=rb.getString("publisher")%></option>
                    <option value="date" <%=session.getAttribute("selectedSorting")!=null&&session.getAttribute("selectedSorting").equals("date")?"selected":""%>><%=rb.getString("dateOfPublication")%></option>
                </select>
            </div>
            <div class="d-grid gap-2 col-2 mx-2">
                <input name="book" type="text" class="form-control" aria-describedby="emailHelp" placeholder="<%=rb.getString("bName")%>" value="<%=session.getAttribute("selectedBook")!=null?session.getAttribute("selectedBook"):""%>">
            </div>
            <div class="d-grid gap-2 col-2 mx-2">
                <input name="author" type="text" class="form-control" aria-describedby="emailHelp" placeholder="<%=rb.getString("aName")%>" value="<%=session.getAttribute("selectedAuthor")!=null?session.getAttribute("selectedAuthor"):""%>">
            </div>
            <div class="d-grid gap-2 col-1 mx-2">
                <button type="submit" class="btn btn-primary my-auto"><%=rb.getString("find")%></button>
            </div>
            <div class="d-grid gap-2 col-1 mx-2">
                <button type="button" class="btn btn-danger my-auto" ><a style="text-decoration: none; color: white" href="${pageContext.request.contextPath}/reset?reset_page=books"><%=rb.getString("reset")%></a></button>
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

<table class="table ps-20px">
    <thead class="table-dark">
    <tr>
        <th scope="col">ID</th>
        <th scope="col"><%=rb.getString("image")%></th>
        <th scope="col"><%=rb.getString("title")%></th>
        <th scope="col"><%=rb.getString("author")%></th>
        <th scope="col"><%=rb.getString("genre")%></th>
        <th scope="col"><%=rb.getString("dateOfPublication")%></th>
        <th scope="col"><%=rb.getString("publisher")%></th>
        <th scope="col"><%=rb.getString("change")%></th>
        <th scope="col"><%=rb.getString("delete")%></th>
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
        while (i <= end) {
            if(lang.equals("en")){
    %>
    <tr>
        <td><%=list.get(i).getId()%></td>
        <td><img src="book_images/<%=list.get(i).getImgName()%>" height="40px" alt=""></td>
        <td><%=list.get(i).getName()%></td>
        <td><%=list.get(i).getAuthor().getAuthorName()%></td>
        <td><%=list.get(i).getGenre().getGenreName()%></td>
        <td><%=list.get(i).getDateOfPublication()%></td>
        <td><%=list.get(i).getPublisher().getPublisherName()%></td>
        <td><a href="change_book.jsp?book_id=<%=list.get(i).getId()%>"><button type="button" class="btn btn-outline-primary">Змінити</button></a></td>
        <td>
            <form method="post" action="delete_book">
                <input type="hidden" name="book_id" value="<%=list.get(i).getId()%>">
                <input type="submit" class="btn btn-outline-danger" value="Delete">
            </form>
        </td>
    </tr>
    <%} else {%>
    <tr>
        <td><%=list.get(i).getId()%></td>
        <td><img src="book_images/<%=list.get(i).getImgName()%>" height="40px" alt=""></td>
        <td><%=list.get(i).getNameUa()%></td>
        <td><%=list.get(i).getAuthor().getAuthorNameUa()%></td>
        <td><%=list.get(i).getGenre().getGenreNameUa()%></td>
        <td><%=list.get(i).getDateOfPublication()%></td>
        <td><%=list.get(i).getPublisher().getPublisherNameUa()%></td>
        <td><a href="change_book.jsp?book_id=<%=list.get(i).getId()%>"><button type="button" class="btn btn-outline-primary">Змінити</button></a></td>
        <td>
            <form method="post" action="delete_book">
                <input type="hidden" name="book_id" value="<%=list.get(i).getId()%>">
                <input type="submit" class="btn btn-outline-danger" value="Видалити">
            </form>
        </td>
    </tr>
    <%}
        i++;
    }
    }
        int current_page = 1;
        if(request.getAttribute("page")!=null)
            current_page = (int) request.getAttribute("page");

    %>

    </tbody>
</table>

<div class="position-fixed bottom-0 end-0">
    <button type="button" class="btn btn-danger m-5" >
        <a style="text-decoration: none; color: aliceblue;" href="new_book.jsp">
            <h2>+ <%=lang.equals("en")?"New Book":"Нова Книга"%></h2>
        </a>
    </button>
</div>

<%
    if(!list.isEmpty()){
%>
<p:pagination count='<%=String.valueOf((list.size()%15==0)?list.size()/15:list.size()/15+1)%>' current_page='<%=String.valueOf(current_page)%>' link="books.jsp" list_size='<%=String.valueOf(list.size())%>'/>
<% } else {%>
<p2 style="font-size: 45px; font-weight: bold; text-align: center"><%=rb.getString("eList")%></p2>
<%}%>
<br><br><br><br><br>
<%@include file="includes/footer.jsp"%>
</body>
</html>