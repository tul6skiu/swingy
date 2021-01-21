package mswingy.model.dao;

import java.util.List;

public interface Dao<T> {
	List<T> getAll();
	void save(T t);
	void update(T t);
	T getForName(String name);
}
