package itrexgroup.testtask.dao;

import itrexgroup.testtask.entities.Image;
import itrexgroup.testtask.repositories.ImageRepository;
import itrexgroup.testtask.services.ContextHolder;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ImageDao implements EntityDao<Image>{
	
	@Autowired
	ImageRepository imageRepository;
	
	@Autowired
	ContextHolder context;
	
	@Autowired
	Logger log;

	@Override
	public Image findById(Integer i) {
		return imageRepository.findOne(i);
	}

	@Override
	public List<Image> showAll() {
		List<Image> imageList = imageRepository.findAll();		
		imageList.stream().sorted((a ,b) -> Integer.compare(a.getId(), b.getId()));
		return imageList;
	}

	@Override
	public void removeById(Integer i) {
		imageRepository.delete(i);
	}

	@Override
	public Integer save(Image t) {
		return imageRepository.saveAndFlush(t).getId();
	}

	@Override
	public Integer findAllCount() {
		return (int) imageRepository.count();
	}
	
	public String getThumbPath(Integer id){
		return context.getThumbsPath()+File.separator+findById(id).getName();
	}
	
	public String getImagePath(Integer id){
		return context.getImagePath()+File.separator+findById(id).getName();
	}
	
}
