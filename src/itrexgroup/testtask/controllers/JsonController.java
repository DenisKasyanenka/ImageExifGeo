package itrexgroup.testtask.controllers;

import itrexgroup.testtask.dao.ExifDao;
import itrexgroup.testtask.dao.ImageDao;
import itrexgroup.testtask.entities.ExifInformation;
import itrexgroup.testtask.entities.Image;
import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;

@Controller
public class JsonController {

	@Autowired
	ImageDao imageDao;
	
	@Autowired
	Logger log;
	 
	@Autowired
	ExifDao exifDao;
	
	@RequestMapping(value = "/exif/{id}", headers = "Accept=application/json", method = RequestMethod.GET, produces = { "application/json; charset=UTF-8" })
	public @ResponseBody String getExifInfo(@PathVariable("id") Integer id) {
		ExifInformation exif = exifDao.findById(id);
		log.info(JSON.toJSONString(exif));
		return JSON.toJSONString(exif);
	}
	
	@RequestMapping(value = "/exif/{id}", method = RequestMethod.PUT, headers = "Accept=application/json", produces = { "application/json; charset=UTF-8" }, consumes = "application/json")
	public @ResponseBody String returnExif(@PathVariable("id") Integer id, @RequestBody String json) {
		ExifInformation exif = JSON.parseObject(json, ExifInformation.class);
		exifDao.save(exif);
		log.info("добавлено описание: " + "\"" + exif.getDescription() + "\"");
		return "index";
	}

	@RequestMapping(value = "/images", headers = "Accept=application/json", method = RequestMethod.GET, produces = { "application/json; charset=UTF-8" })
	public @ResponseBody String getImagesJson() {
		List<Image> allImages = imageDao.showAll();
		return JSON.toJSONString(allImages);
	}
	
}
