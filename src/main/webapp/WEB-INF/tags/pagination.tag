<%@ tag pageEncoding="UTF-8" %>
<%@ attribute name="count" required="true" rtexprvalue="true" %>
<%@ attribute name="current_page" required="true" rtexprvalue="true" %>
<%@ attribute name="link" required="true" rtexprvalue="true" %>
<%@ attribute name="list_size" required="true" rtexprvalue="true" %>

<%
    int pCount = Integer.parseInt(count);
    int currentPage = Integer.parseInt(current_page);
    String hrefForLink = "pagination?size="+list_size+"&link="+link+"&page=";
%>
<nav aria-label="Page navigation example" style="position: relative; margin: 0 auto; width: 318px;">
    <ul class="pagination">
        <li class="page-item <%=currentPage == 1 ? "disabled" : ""%>"><a class="page-link" href="<%=hrefForLink%><%=currentPage-1%>">Previous</a></li>
        <%
            if(pCount <=5){
                for (int i = 1; i <= pCount; i++) {
        %>
        <li class="page-item <%=currentPage == i ? "active" : ""%>"><a class="page-link" href="<%=hrefForLink%><%=i%>"><%=i%></a></li>
        <%
            }
        } else {
                if(currentPage<=3){
        %>
        <li class="page-item <%=currentPage == 1 ? "active" : ""%>"><a class="page-link" href="<%=hrefForLink%>1">1</a></li>
        <li class="page-item <%=currentPage == 2 ? "active" : ""%>"><a class="page-link" href="<%=hrefForLink%>2">2</a></li>
        <li class="page-item <%=currentPage == 3 ? "active" : ""%>"><a class="page-link" href="<%=hrefForLink%>3">3</a></li>
        <li class="page-item"><a class="page-link" href="<%=hrefForLink%>4">4</a></li>
        <li class="page-item"><a class="page-link" href="<%=hrefForLink%>5">5</a></li>
        <%
        } else if(currentPage>= pCount -2){
        %>
        <li class="page-item"><a class="page-link" href="<%=hrefForLink%><%=pCount-4%>"><%=pCount -4%></a></li>
        <li class="page-item"><a class="page-link" href="<%=hrefForLink%><%=pCount-3%>"><%=pCount -3%></a></li>
        <li class="page-item <%=currentPage == pCount - 2 ? "active" : ""%>"><a class="page-link" href="<%=hrefForLink%><%=pCount-2%>"><%=pCount -2%></a></li>
        <li class="page-item <%=currentPage == pCount - 1 ? "active" : ""%>"><a class="page-link" href="<%=hrefForLink%><%=pCount-1%>"><%=pCount -1%></a></li>
        <li class="page-item <%=currentPage == pCount ? "active" : ""%>"><a class="page-link" href="<%=hrefForLink%><%=pCount%>"><%=pCount%></a></li>
        <%
        }
        else {
        %>
        <li class="page-item"><a class="page-link" href="<%=hrefForLink%><%=currentPage-2%>"><%=currentPage-2%></a></li>
        <li class="page-item"><a class="page-link" href="<%=hrefForLink%><%=currentPage-1%>"><%=currentPage-1%></a></li>
        <li class="page-item active"><a class="page-link" href="<%=hrefForLink%><%=currentPage%>"><%=currentPage%></a></li>
        <li class="page-item"><a class="page-link" href="<%=hrefForLink%><%=currentPage+1%>"><%=currentPage+1%></a></li>
        <li class="page-item"><a class="page-link" href="<%=hrefForLink%><%=currentPage+2%>"><%=currentPage+2%></a></li>
        <%
                }
            }
        %>
        <li class="page-item <%=currentPage == pCount ? "disabled" : ""%>"><a class="page-link" href="<%=hrefForLink%><%=currentPage+1%>">Next</a></li>
    </ul>
</nav>


