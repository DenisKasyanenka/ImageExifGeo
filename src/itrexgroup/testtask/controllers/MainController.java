package itrexgroup.testtask.controllers;

import itrexgroup.testtask.appconfig.ApplicationConfiguration;
import itrexgroup.testtask.dao.ExifDao;
import itrexgroup.testtask.dao.ImageDao;
import itrexgroup.testtask.services.ContextHolder;
import itrexgroup.testtask.services.UploadService;
import itrexgroup.testtask.services.ExifService;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.Files;
import java.text.ParseException;

import javax.servlet.ServletContext;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.drew.imaging.ImageProcessingException;

@Controller
public class MainController {

	@Autowired
	ImageDao imageDao;

	@Autowired
	Logger log;

	@Autowired
	ExifDao exifDao;

	@Autowired
	ExifService uploadedImageService;

	@Autowired
	private ContextHolder context;

	@Autowired
	UploadService uploadService;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView showIndexPage() throws IOException, ImageProcessingException, ParseException {
		loadInitialImages();
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("index");
		modelAndView.addObject("number", imageDao.findAllCount());
		return modelAndView;
	}
	
	// загрузка в базу начальных картинок (если база пустая)
	private void loadInitialImages() throws IOException, ImageProcessingException, ParseException{
		if (imageDao.findAllCount() == 0) {
			String path = context.getImagePath()+File.separator;
			File folder = new File(path);
			File[] fileArr = folder.listFiles();
			for (int i = 0; i <= fileArr.length - 1; i++) {
				if (!fileArr[i].isDirectory()){
				byte[] bytes = Files.readAllBytes(fileArr[i].toPath());
				uploadService.uploadImage(bytes, fileArr[i].getName());
				}
			}
		}
	}
	
}
