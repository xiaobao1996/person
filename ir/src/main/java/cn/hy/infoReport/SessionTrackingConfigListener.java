package cn.hy.infoReport;

import org.springframework.boot.web.servlet.ServletContextInitializer;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.SessionCookieConfig;

public class SessionTrackingConfigListener implements ServletContextInitializer {

    @Override
    public void onStartup(ServletContext servletContext)
            throws ServletException {
        SessionCookieConfig sessionCookieConfig = servletContext
                .getSessionCookieConfig();
        sessionCookieConfig.setName("DT-SESSIONID");
    }

}
