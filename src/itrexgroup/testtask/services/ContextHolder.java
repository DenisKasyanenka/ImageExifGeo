package itrexgroup.testtask.services;

import java.io.File;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ContextHolder {

	@Autowired
    ServletContext context;
	
	@Autowired
	Logger log;
	
	String imagePath;
	String thumbsPath;
	
	@PostConstruct
	private void init(){
	imagePath = context.getRealPath(File.separator+"resources"+ File.separator+ "testimages"); 
	thumbsPath = context.getRealPath(File.separator+"resources"+ File.separator+ "testimages"+File.separator+"thumbs");
	log.info("imagePath = "+imagePath);
	log.info("thumbsPath = "+ thumbsPath);
	}

	public String getImagePath() {
		return imagePath;
	}

	public String getThumbsPath() {
		return thumbsPath;
	}


	
}
