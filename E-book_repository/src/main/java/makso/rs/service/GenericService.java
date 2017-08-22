package makso.rs.service;

import java.util.List;

public interface GenericService<T> {

	List<T> getAll();

	T save(T t);

	T findById(long id);
}
