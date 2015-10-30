package itrexgroup.testtask.services;

import itrexgroup.testtask.appconfig.ApplicationConfiguration;
import itrexgroup.testtask.dao.ExifDao;
import itrexgroup.testtask.dao.ImageDao;
import itrexgroup.testtask.entities.ExifInformation;
import itrexgroup.testtask.entities.Image;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.util.Date;

import javax.servlet.ServletContext;

import net.coobird.thumbnailator.Thumbnails;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.drew.imaging.ImageProcessingException;

@Service
public class UploadService {

	@Autowired
	ImageDao imageService;
	
	@Autowired
	ExifDao exifService;

	@Autowired
	Logger log;

	@Autowired
	ExifService exifServise;
	
	@Autowired
	ContextHolder context;
	
	public void uploadImage(byte[] bytes, String name) throws ImageProcessingException, IOException, ParseException{
		String imgPath = context.getImagePath()+File.separator+name;
		String thumbPath = context.getThumbsPath()+File.separator+name;
		BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(new File(imgPath)));
		stream.write(bytes);
		stream.close();
		log.info("сохранен: "+imgPath);
		Image image = new Image(name, new Date());
		log.info("дата и время загрузки файла: "+image.getUploadDate());
		File imageFile = new File(imgPath);
		ExifInformation exifInfo = exifServise.getExif(imageFile);
		Thumbnails.of(imageFile).size(160, 160).toFile(new File(thumbPath));
		log.info("создана миниатюра для файла: 160х160");
		log.info("сохранена миниатюра: 160х160");

		image.setExif(exifInfo);
		exifService.save(exifInfo);
		log.info("сохранена информация EXIF: "+exifInfo.toString());
		
			
		imageService.save(image);
		log.info("сохранено изображение. Дата загрузки: "+image.getUploadDate());
			
		
	}
}
