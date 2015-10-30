package itrexgroup.testtask.dao;

import java.util.List;

public interface EntityDao<T> {
	T findById(Integer i);
	List<T> showAll();
	void removeById(Integer i);
	Integer save(T t);
	Integer findAllCount();
}
