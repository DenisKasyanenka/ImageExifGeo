package itrexgroup.testtask.controllers;

import itrexgroup.testtask.dao.ExifDao;
import itrexgroup.testtask.dao.ImageDao;
import itrexgroup.testtask.services.UploadService;

import java.io.IOException;
import java.text.ParseException;

import javax.servlet.ServletContext;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.drew.imaging.ImageProcessingException;

@Controller
public class FileUpload {

	@Autowired
	ServletContext context;

	@Autowired
	Logger log;

	@Autowired
	ImageDao imageService;

	@Autowired
	ExifDao exifService;

	@Autowired
	UploadService uploadService;

	@RequestMapping(value = "/images", method = RequestMethod.POST)
	public @ResponseBody String uploadFile(@RequestParam("name") String name,
			@RequestParam("file") MultipartFile file) throws IOException,
			ImageProcessingException, ParseException {
		if (!file.isEmpty()) {
			byte[] bytes = file.getBytes();
			// против выкрутасов Хрома
			if (name.contains("C:\\fakepath\\")) {
				name = name.replace("C:\\fakepath\\", "");
			}
			uploadService.uploadImage(bytes, name);
		}
		return "#";
	}
}
