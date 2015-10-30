package itrexgroup.testtask.controllers;

import itrexgroup.testtask.dao.ImageDao;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping
public class ImageController {

	@Autowired
	ImageDao imageService;

	@RequestMapping(value = "/images/{id}", method = RequestMethod.GET)
	public @ResponseBody byte[] loadImage(HttpServletResponse res,
			@PathVariable("id") Integer id) throws IOException {
		return Files.readAllBytes(new File(imageService.getImagePath(id)).toPath());
	}

	@RequestMapping(value = "/thumbs/{id}", method = RequestMethod.GET)
	public @ResponseBody byte[] loadIthumbnail(HttpServletResponse res,
			@PathVariable("id") Integer id) throws IOException {
		return Files.readAllBytes(new File(imageService.getThumbPath(id)).toPath());
	}
}
