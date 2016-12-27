package com.zoe.snow.listener;

import javax.servlet.ServletContextAttributeEvent;
import javax.servlet.ServletContextAttributeListener;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * @author Administrator
 */
public class OnlineUserListener implements HttpSessionListener,
        ServletContextListener, ServletContextAttributeListener {
    private long onlineUserCount = 0;

    public long getOnlineUserCount() {
        return onlineUserCount;
    }

    /* (non-Javadoc)
     * @see javax.servlet.ServletContextAttributeListener#attributeAdded(javax.servlet.ServletContextAttributeEvent)
     */
    @Override
    public void attributeAdded(ServletContextAttributeEvent arg0) {

    }

    /* (non-Javadoc)
     * @see javax.servlet.ServletContextAttributeListener#attributeRemoved(javax.servlet.ServletContextAttributeEvent)
     */
    @Override
    public void attributeRemoved(ServletContextAttributeEvent arg0) {

    }

    /* (non-Javadoc)
     * @see javax.servlet.ServletContextAttributeListener#attributeReplaced(javax.servlet.ServletContextAttributeEvent)
     */
    @Override
    public void attributeReplaced(ServletContextAttributeEvent attributeEvent) {
        System.err.println("...attributeReplaced...");
    }

    /* (non-Javadoc)
     * @see javax.servlet.ServletContextListener#contextDestroyed(javax.servlet.ServletContextEvent)
     */
    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }

    /* (non-Javadoc)
     * @see javax.servlet.ServletContextListener#contextInitialized(javax.servlet.ServletContextEvent)
     */
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {

    }

    /* (non-Javadoc)
     * @see javax.servlet.http.HttpSessionListener#sessionCreated(javax.servlet.http.HttpSessionEvent)
     */
    @Override
    public void sessionCreated(HttpSessionEvent httpSessionEvent) {
        onlineUserCount++;
        toUpdateCount(httpSessionEvent);
    }

    /* (non-Javadoc)
     * @see javax.servlet.http.HttpSessionListener#sessionDestroyed(javax.servlet.http.HttpSessionEvent)
     */
    @Override
    public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {
        onlineUserCount--;
        toUpdateCount(httpSessionEvent);
    }

    private void toUpdateCount(HttpSessionEvent httpSessionEvent) {
        httpSessionEvent.getSession().setAttribute("onlineUserCount", onlineUserCount);
    }
}
