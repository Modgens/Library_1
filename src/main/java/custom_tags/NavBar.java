package custom_tags;

import javax.servlet.jsp.tagext.*;
import javax.servlet.jsp.*;
import java.io.*;
import java.util.Locale;
import java.util.ResourceBundle;

public class NavBar extends SimpleTagSupport {
    private String message;
    private String role;
    private String name;
    private String lang;

    public String newLink(String isActive, String name, String jspName){
        return "<li class=\"nav-item\"> <a class=\"nav-link "+isActive+"\" href=\""+jspName+"\">"+name+"</a></li>";
    }

    public void setLang(String lang) {
        this.lang = lang;
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
        if (message != null && role!=null && lang!=null) {
            ResourceBundle rb = null;
            if(lang.equals("en")){
                rb = ResourceBundle.getBundle("Localization/Bundle", new Locale("en", "US"));
            } else {
                rb = ResourceBundle.getBundle("Localization/Bundle", new Locale("ua", "UA"));
            }

            String catalogIsActive="";
            String booksIsActive="";
            String librarianIsActive="";
            String userIsActive="";
            String myBooks="";
            String orders="";
            String userLibrarians="";
            String readingRoom="";

            String line1="";
            String line2="";
            String line3="";

            String personalAccount ="" ;

            switch (message){
                case "catalog":
                    catalogIsActive="active";
                    break;
                case "books":
                    booksIsActive="active";
                    break;
                case "librarians":
                    librarianIsActive="active";
                    break;
                case "users":
                    userIsActive="active";
                    break;
                case "myBooks":
                    myBooks="active";
                    break;
                case "orders":
                    orders="active";
                    break;
                case "users_librarian":
                    userLibrarians="active";
                    break;
                case "reading_room":
                    readingRoom="active";
                    break;

            }

            switch (role){
                case "user":
                    personalAccount = "<li><a class=\"dropdown-item\" href=\"personal_account.jsp\">"+rb.getString("pa")+"</a></li>\n<li><hr class=\"dropdown-divider\"></li>\n";
                    line1=newLink(myBooks, rb.getString("myOrders"), "myOrders.jsp");
                    break;
                case "librarian":
                    line1=newLink(userLibrarians, rb.getString("users"), "users_librarian.jsp");
                    line2=newLink(orders, rb.getString("orders"),"user_orders.jsp");
                    line3=newLink(readingRoom,rb.getString("readingRoom"), "reading_room.jsp");
                    break;
                case "admin":
                    line1=newLink(booksIsActive, rb.getString("books"), "books.jsp");
                    line2=newLink(librarianIsActive, rb.getString("librarians"),"librarians.jsp");
                    line3=newLink(userIsActive, rb.getString("users"), "users.jsp");
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
                    "          <a class=\"nav-link "+catalogIsActive+"\" href=\"atalog.jsp\">"+rb.getString("catalog")+"</a>\n" +
                    "        </li>\n" + line1+line2+line3+
                    "        <li class=\"nav-item dropdown d-flex\">\n" +
                    "          <a class=\"nav-link dropdown-toggle\" href=\"#\" role=\"button\" data-bs-toggle=\"dropdown\" aria-expanded=\"false\">\n" +
                    "            "+rb.getString("lang")+"\n" +
                    "          </a>\n" +
                    "          <ul class=\"dropdown-menu\">\n" +
                    "            <li><a class=\"dropdown-item\" href=\"lang?language=ua&path=catalog.jsp\"><img src=\"images/the-ukraine-flag.jpg\" width=\"17px\" alt=\"\" style=\"margin-right: 5px;\">"+rb.getString("langUa")+"</a></li>\n" +
                    "            <li><hr class=\"dropdown-divider\"></li>\n" +
                    "            <li><a class=\"dropdown-item\" href=\"lang?language=en&path=catalog.jsp\"><img src=\"images/usuk-flag.jpg\" width=\"18px\" alt=\"\" style=\"margin-right: 5px;\">"+rb.getString("langEn")+"</a></li>\n" +
                    "          </ul>\n" +
                    "        </li>\n" +
                    "      </ul>\n" +
                    "      <li class=\"nav-item dropdown d-flex\">\n" +
                    "      <a class=\"nav-link dropdown-toggle\" style=\"color: white;\" href=\"#\" role=\"button\" data-bs-toggle=\"dropdown\" aria-expanded=\"false\">\n" +
                    "        "+name+"\n" +
                    "      </a>\n" +
                    "      <ul class=\"dropdown-menu\">\n" + personalAccount +
                    "        <li><a class=\"dropdown-item\" href=\"login.jsp\">"+rb.getString("signOut")+"</a></li>\n" +
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