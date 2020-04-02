package org.jasig.cas.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

public class MySessionListener implements HttpSessionListener {

    private Logger logger = LoggerFactory.getLogger(getClass());

    public void sessionCreated(HttpSessionEvent httpSessionEvent) {
        logger.info("新增session:{}", httpSessionEvent.getSession().getId());
        MySessionContext.AddSession(httpSessionEvent.getSession());
    }

    public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {
        HttpSession session = httpSessionEvent.getSession();
        logger.info("删除session:{}", session.getId());
        MySessionContext.DelSession(session);
    }
}