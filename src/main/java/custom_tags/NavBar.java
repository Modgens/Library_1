package custom_tags;

import javax.servlet.jsp.tagext.*;
import javax.servlet.jsp.*;
import java.io.*;

public class NavBar extends SimpleTagSupport {
    private String message;
    private String role;
    private String name;

    public String newLink(String isActive, String name, String jspName){
        return "<li class=\"nav-item\"> <a class=\"nav-link "+isActive+"\" href=\""+jspName+"\">"+name+"</a></li>";
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMessage(String msg) {
        this.message = msg;
    }

    public void setRole(String role) {
        this.role = role;
    }

    StringWriter sw = new StringWriter();
    public void doTag() throws JspException, IOException {
        if (message != null && role!=null) {
            String catalogIsActive="";
            String homeIsActive="";

            String line1="";
            String line2="";
            String line3="";

            if(message.equals("catalog")){
                catalogIsActive="active";
            }
            if(message.equals("home")){
                homeIsActive="active";
            }
            switch (role){
                case "user":
                    break;
                case "librarian":

                    line1=newLink("","Reading Room", "readingRoom.jsp");
                    line2=newLink("", "Orders","orders.jsp");
                    line3=newLink("", "Order Book", "bookOrder.jsp");
                    break;
                case "admin":
                    line1=newLink("", "Books", "new_book.jsp");
                    line2=newLink("", "Librarians","librarian.jsp");
                    line3=newLink("","Users", "users.jsp");
                    break;
            }

            JspWriter out = getJspContext().getOut();
            out.println("<nav class=\"navbar navbar-expand-lg navbar-dark bg-dark\">\n" +
                    "  <div class=\"container\">\n" +
                    "    <a href=\"#\" class=\"navbar-brand mb-0 h1\">\n" +
                    "      <img src=\"images/freehand-book.svg\" width=\"30px\" alt=\"\">\n" +
                    "      Library\n" +
                    "    </a>\n" +
                    "    <button class=\"navbar-toggler\" type=\"button\" data-bs-toggle=\"collapse\" data-bs-target=\"#navbarScroll\" aria-controls=\"navbarScroll\" aria-expanded=\"false\" aria-label=\"Toggle navigation\">\n" +
                    "      <span class=\"navbar-toggler-icon\"></span>\n" +
                    "    </button>\n" +
                    "    <div class=\"collapse navbar-collapse\" id=\"navbarScroll\">\n" +
                    "      <ul class=\"navbar-nav me-auto my-2 my-lg-0 navbar-nav-scroll\" style=\"--bs-scroll-height: 100px;\">\n" +
                    "        <li class=\"nav-item\">\n" +
                    "          <a class=\"nav-link "+homeIsActive+"\" aria-current=\"page\" href=\"index.jsp\">Home</a>\n" +
                    "        </li>\n" +
                    "        <li class=\"nav-item\">\n" +
                    "          <a class=\"nav-link "+catalogIsActive+"\" href=\"catalog.jsp\">Catalog</a>\n" +
                    "        </li>\n" + line1+line2+line3+
                    "        <li class=\"nav-item dropdown d-flex\">\n" +
                    "          <a class=\"nav-link dropdown-toggle\" href=\"#\" role=\"button\" data-bs-toggle=\"dropdown\" aria-expanded=\"false\">\n" +
                    "            Language\n" +
                    "          </a>\n" +
                    "          <ul class=\"dropdown-menu\">\n" +
                    "            <li><a class=\"dropdown-item\" href=\"#\"><img src=\"images/the-ukraine-flag.jpg\" width=\"17px\" alt=\"\" style=\"margin-right: 5px;\">Ukraine</a></li>\n" +
                    "            <li><hr class=\"dropdown-divider\"></li>\n" +
                    "            <li><a class=\"dropdown-item\" href=\"#\"><img src=\"images/usuk-flag.jpg\" width=\"18px\" alt=\"\" style=\"margin-right: 5px;\">English</a></li>\n" +
                    "          </ul>\n" +
                    "        </li>\n" +
                    "      </ul>\n" +
                    "      <li class=\"nav-item dropdown d-flex\">\n" +
                    "      <a class=\"nav-link dropdown-toggle\" style=\"color: white;\" href=\"#\" role=\"button\" data-bs-toggle=\"dropdown\" aria-expanded=\"false\">\n" +
                    "        "+name+"\n" +
                    "      </a>\n" +
                    "      <ul class=\"dropdown-menu\">\n" +
                    "        <li><a class=\"dropdown-item\" href=\"#\">Personal Account</a></li>\n" +
                    "        <li><hr class=\"dropdown-divider\"></li>\n" +
                    "        <li><a class=\"dropdown-item\" href=\"#\">Sign Out</a></li>\n" +
                    "      </ul>\n" +
                    "    </div>\n" +
                    "  </div>\n" +
                    "</nav>");
        } else {
            getJspBody().invoke(sw);
            getJspContext().getOut().println(sw.toString());
        }
    }
}