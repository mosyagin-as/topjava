package ru.javawebinar.topjava.web;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class ApplicationContextListener implements ServletContextListener {

    private ApplicationContext applicationContext;

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        applicationContext = new ClassPathXmlApplicationContext("spring/spring-app.xml");
        servletContextEvent.getServletContext().setAttribute("applicationContext", applicationContext);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        ((ConfigurableApplicationContext)applicationContext).close();
    }
}
