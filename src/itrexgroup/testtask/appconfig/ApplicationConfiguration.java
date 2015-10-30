package itrexgroup.testtask.appconfig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

@EnableWebMvc
@Configuration
@Import({ SpringDataConfig.class, LogConfig.class })
@ComponentScan({ "itrexgroup.testtask.appconfig",
		"itrexgroup.testtask.controllers", "itrexgroup.testtask.dao",
		"itrexgroup.testtask.formaters", "itrexgroup.testtask.services" })
public class ApplicationConfiguration extends WebMvcConfigurerAdapter {

@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/**").addResourceLocations("/resources/");
	}

	@Bean
	ThymeleafViewResolver getThymeleafViewResolver() {
		ThymeleafViewResolver thymeleafViewResolver = new ThymeleafViewResolver();
		thymeleafViewResolver.setTemplateEngine(getTemplateEngine());
		thymeleafViewResolver.setCharacterEncoding("UTF-8");
		thymeleafViewResolver.setOrder(1);
		return thymeleafViewResolver;
	}

	@Bean
	SpringTemplateEngine getTemplateEngine() {
		SpringTemplateEngine engine = new SpringTemplateEngine();
		engine.setTemplateResolver(getTemplateResolver());
		engine.addDialect(new nz.net.ultraq.thymeleaf.LayoutDialect());
		return engine;
	}

	@Bean
	ServletContextTemplateResolver getTemplateResolver() {
		ServletContextTemplateResolver contextTemplateResolver = new ServletContextTemplateResolver();
		contextTemplateResolver.setPrefix("/WEB-INF/thymeleaf/");
		contextTemplateResolver.setSuffix(".html");
		contextTemplateResolver.setTemplateMode("HTML5");
		contextTemplateResolver.setCharacterEncoding("UTF-8");
		contextTemplateResolver.setCacheable(false);
		return contextTemplateResolver;
	}

	@Bean(name = "multipartResolver")
	CommonsMultipartResolver getCommonsMultipartResolver() {
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
		return multipartResolver;
	}
}
