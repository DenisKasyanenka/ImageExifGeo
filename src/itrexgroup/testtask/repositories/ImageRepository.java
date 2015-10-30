package itrexgroup.testtask.repositories;

import itrexgroup.testtask.entities.Image;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public interface ImageRepository extends JpaRepository<Image, Integer> {

	
	
}
