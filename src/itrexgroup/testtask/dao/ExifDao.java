package itrexgroup.testtask.dao;

import itrexgroup.testtask.entities.ExifInformation;
import itrexgroup.testtask.repositories.ExifRepository;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ExifDao implements EntityDao<ExifInformation> {

	@Autowired
	ExifRepository exifRepository;

	@Autowired
	Logger log;
	
	@Override
	public ExifInformation findById(Integer i) {
		ExifInformation exif = exifRepository.findOne(i);
		log.info("найдена информация по файлу с id "+i+"содержание: "+exif.toString());
		return exif;
	}

	@Override
	public List<ExifInformation> showAll() {
		return exifRepository.findAll();
	}

	@Override
	public void removeById(Integer i) {
		exifRepository.delete(i);
	}

	@Override
	public Integer save(ExifInformation t) {
		log.info("найдена информация: "+t.toString());
		return exifRepository.saveAndFlush(t).getId();
	}

	@Override
	public Integer findAllCount() {
		int count = (int) exifRepository.count();
		log.info("посчитано общее количество загруженных изображений: "+count);
		return count;
	}

}
