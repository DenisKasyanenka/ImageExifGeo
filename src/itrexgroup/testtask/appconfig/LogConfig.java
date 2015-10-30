package itrexgroup.testtask.appconfig;

import itrexgroup.testtask.controllers.FileUpload;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LogConfig {

	@Bean
	Logger getLogger() {
		Logger loger = Logger.getLogger(FileUpload.class);
		return loger;
	}
}
