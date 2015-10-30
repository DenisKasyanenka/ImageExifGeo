package itrexgroup.testtask.repositories;

import javax.transaction.Transactional;

import itrexgroup.testtask.entities.ExifInformation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public interface ExifRepository extends JpaRepository<ExifInformation, Integer> {

}
