package listeners;

import filters.BookFilter;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.logging.Logger;


public class MySessionListener implements HttpSessionListener {
    static final Logger logger = Logger.getLogger(String.valueOf(MySessionListener.class));

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        logger.info("Session created successful");
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        logger.info("Session deleted successful");
    }
}
