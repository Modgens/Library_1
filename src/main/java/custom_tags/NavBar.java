package custom_tags;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Logger;

public class NavBar extends SimpleTagSupport {

    static final Logger logger = Logger.getLogger(String.valueOf(NavBar.class));

    private String message;//active light in menu for current page (message is name of current page)
    private String role;
    private String name;
    private String lang;

    public String newLink(String isActive, String name, String jspName) {
        return "<li class=\"nav-item\"> <a class=\"nav-link " + isActive + "\" href=\"" + jspName + "\">" + name + "</a></li>";
    }

    public void setLang(String lang) {
        logger.info("lang - " + lang);
        this.lang = lang;
    }

    public void setName(String name) {
        logger.info("name - " + name);
        this.name = name;
    }

    public void setMessage(String msg) {
        logger.info("message");
        this.message = msg;
    }

    public void setRole(String role) {
        this.role = role;
    }

    StringWriter sw = new StringWriter();

    public void doTag() throws JspException, IOException {
        if (message != null && role != null && lang != null) {
            ResourceBundle rb;
            if (lang.equals("en")) {
                rb = ResourceBundle.getBundle("Localization/Bundle", new Locale("en", "US"));
                logger.info("ResourceBundle with location en now");
            } else {
                rb = ResourceBundle.getBundle("Localization/Bundle", new Locale("ua", "UA"));
                logger.info("ResourceBundle with location ua now");
            }

            String line1 = "";
            String line2 = "";
            String line3 = "";

            String personalAccount = "";

            switch (role) {
                case "user":
                    logger.info("create menu for USER");
                    personalAccount = "<li><a class=\"dropdown-item\" href=\"personal_account.jsp\">" + rb.getString("pa") + "</a></li>\n<li><hr class=\"dropdown-divider\"></li>\n";
                    line1 = newLink((message.equals("myBooks") ? "active" : ""), rb.getString("myOrders"), "myOrders.jsp");
                    break;
                case "librarian":
                    logger.info("create menu for LIBRARIAN");
                    line1 = newLink((message.equals("users_librarian") ? "active" : ""), rb.getString("users"), "users_librarian.jsp");
                    line2 = newLink((message.equals("orders") ? "active" : ""), rb.getString("orders"), "user_orders.jsp");
                    line3 = newLink((message.equals("reading_room") ? "active" : ""), rb.getString("readingRoom"), "reading_room.jsp");
                    break;
                case "admin":
                    logger.info("create menu for ADMIN");
                    line1 = newLink((message.equals("books") ? "active" : ""), rb.getString("books"), "books.jsp");
                    line2 = newLink((message.equals("librarians") ? "active" : ""), rb.getString("librarians"), "librarians.jsp");
                    line3 = newLink((message.equals("users") ? "active" : ""), rb.getString("users"), "users.jsp");
                    break;
                case "guest":
                    logger.info("create menu for GUEST");
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
                    "          <a class=\"nav-link " + (message.equals("catalog") ? "active" : "") + "\" href=\"catalog.jsp\">" + rb.getString("catalog") + "</a>\n" +
                    "        </li>\n" + line1 + line2 + line3 +
                    "        <li class=\"nav-item dropdown d-flex\">\n" +
                    "          <a class=\"nav-link dropdown-toggle\" href=\"#\" role=\"button\" data-bs-toggle=\"dropdown\" aria-expanded=\"false\">\n" +
                    "            " + rb.getString("lang") + "\n" +
                    "          </a>\n" +
                    "          <ul class=\"dropdown-menu\">\n" +
                    "            <li><a class=\"dropdown-item\" href=\"lang?language=ua&path=catalog.jsp\"><img src=\"images/the-ukraine-flag.jpg\" width=\"17px\" alt=\"\" style=\"margin-right: 5px;\">" + rb.getString("langUa") + "</a></li>\n" +
                    "            <li><hr class=\"dropdown-divider\"></li>\n" +
                    "            <li><a class=\"dropdown-item\" href=\"lang?language=en&path=catalog.jsp\"><img src=\"images/usuk-flag.jpg\" width=\"18px\" alt=\"\" style=\"margin-right: 5px;\">" + rb.getString("langEn") + "</a></li>\n" +
                    "          </ul>\n" +
                    "        </li>\n" +
                    "      </ul>\n" +
                    "      <li class=\"nav-item dropdown d-flex\">\n" +
                    "      <a class=\"nav-link dropdown-toggle\" style=\"color: white;\" href=\"#\" role=\"button\" data-bs-toggle=\"dropdown\" aria-expanded=\"false\">\n" +
                    "        " + name + "\n" +
                    "      </a>\n" +
                    "      <ul class=\"dropdown-menu\">\n" + personalAccount +
                    "        <li><a class=\"dropdown-item\" href=\"login.jsp\">" + rb.getString("signOut") + "</a></li>\n" +
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