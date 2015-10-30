package itrexgroup.testtask.appconfig;

import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration.Dynamic;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

public class WebAppInitializer implements WebApplicationInitializer {

    @Override
    public void onStartup(final ServletContext servletContext) throws ServletException {
        final WebApplicationContext context = getContext();
        servletContext.addListener(new ContextLoaderListener(context));
        final Dynamic dispatcher = servletContext.addServlet("DispatcherServlet", new DispatcherServlet(context));
        dispatcher.setMultipartConfig(getMultipartConfigElement());
        dispatcher.setLoadOnStartup(1);
        dispatcher.addMapping("/");
    }

    private static AnnotationConfigWebApplicationContext getContext() {
        final AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
        context.register(ApplicationConfiguration.class);
        return context;
    }

    private static MultipartConfigElement getMultipartConfigElement(){
        return new MultipartConfigElement("", 264826094, 264826094, 264826094);
    }
}




