<%@ page import="dao.implementation.BookDaoImpl" %>
<%@ page import="java.util.List" %>
<%@ page import="entity.Book" %>
<%@ page import="dao.implementation.AuthorDaoImpl" %>
<%@ page import="dao.implementation.GenreDaoImpl" %>
<%@ page import="entity.Genre" %>
<%@ page import="dao.implementation.PublisherDaoImpl" %>
<%@ taglib uri="/WEB-INF/navbar-tag.tld" prefix="nav" %>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"
         language="java"%>
<%

    BookDaoImpl bookDaoImpl = new BookDaoImpl();
    AuthorDaoImpl authorDao = new AuthorDaoImpl();
    GenreDaoImpl genreDao = new GenreDaoImpl();
    PublisherDaoImpl publisherDao = new PublisherDaoImpl();
    List<Book> list = (List<Book>) request.getAttribute("list");
    if(list == null)
        list = bookDaoImpl.getAll();
%>

<!doctype html>
<html lang="en">


<head>
    <%@include file="includes/header.jsp"%>
    <title>Library</title>
</head>
<body>

<nav:Navbar message="catalog" role='<%=(String)session.getAttribute("role")%>' name='<%=(String) session.getAttribute("name")%>'/>

<div class="collapse" id="navbarToggleExternalContent">
    <div class="bg-light p-4">
        <form method="post" action="sort" class="d-flex">

            <input type="hidden" name="page" value="/books.jsp">

            <div class="d-grid gap-2 col-2 mx-3">
                <select name="genre" class="form-select" aria-label="Default select example">
                    <option selected>Genre</option>
                    <%
                        for (Genre genre : genreDao.getAll()) {
                    %>
                    <option value="<%=genre.getGenreName()%>"><%=genre.getGenreName()%></option>
                    <%
                        }
                    %>
                </select>
            </div>
            <div class="d-grid gap-2 col-2 mx-2">
                <select name="sort" class="form-select" aria-label="Default select example">
                    <option selected>Sorted by</option>
                    <option value="name">Name</option>
                    <option value="author">Author</option>
                    <option value="publisher">Publisher</option>
                    <option value="date">Date of publication</option>
                </select>
            </div>
            <div class="d-grid gap-2 col-3 mx-2">
                <input name="book" type="text" class="form-control" aria-describedby="emailHelp" placeholder="Book name">
            </div>
            <div class="d-grid gap-2 col-3 mx-2">
                <input name="author" type="text" class="form-control" aria-describedby="emailHelp" placeholder="Author name">
            </div>
            <div class="d-grid gap-2 col-1 mx-2">
                <button type="submit" class="btn btn-primary my-auto">Find</button>
            </div>
        </form>
    </div>
</div>
<nav class="navbar navbar-light bg-light">
    <div class="container-fluid">
        <button class="btn btn-outline-success d-grid gap-2 col-3 mx-auto" type="button" data-bs-toggle="collapse" data-bs-target="#navbarToggleExternalContent" aria-controls="navbarToggleExternalContent" aria-expanded="false" aria-label="Toggle navigation">
            Searching
        </button>
    </div>
</nav>

<table class="table ps-20px">
    <thead class="table-dark">
        <tr>
            <th scope="col">ID</th>
            <th scope="col">Image</th>
            <th scope="col">Title</th>
            <th scope="col">Author</th>
            <th scope="col">Genre</th>
            <th scope="col">Date</th>
            <th scope="col">Publisher</th>
            <th scope="col">Delete</th>
            <th scope="col">Change</th>
        </tr>
    </thead>
    <tbody>

    <%
        if(!list.isEmpty()){
            for (Book book : list) {
    %>
        <tr>
            <td><%=book.getId()%></td>
            <td><img src="book_images/<%=book.getImgName()%>" height="40px" alt=""></td>
            <td><%=book.getName()%></td>
            <td><%=authorDao.get(book.getAuthorId()).getAuthorName()%></td>
            <td><%=genreDao.get(book.getGenreId()).getGenreName()%></td>
            <td><%=book.getDateOfPublication()%></td>
            <td><%=publisherDao.get(book.getPublicationId()).getPublisherName()%></td>
            <td><button type="button" class="btn btn-outline-primary"><a style="text-decoration: none; color: black" href="change_book.jsp?book_id=<%=book.getId()%>">Change</a></button></td>
            <td><button type="button" class="btn btn-outline-danger"><a style="text-decoration: none; color: black" href="${pageContext.request.contextPath}/delete_book?book_id=<%=book.getId()%>">Delete</a></button></td>
        </tr>
    <%}
    }
    %>
    </tbody>
</table>

<div class="position-absolute bottom-0 end-0">
    <button type="button" class="btn btn-danger m-5" >
        <a style="text-decoration: none; color: aliceblue;" href="new_book.jsp">
            <h2>+ New Book</h2>
        </a>
    </button>
</div>


<%@include file="includes/footer.jsp"%>
</body>
</html>