package com.weiminw.travel.persistence;

import java.util.List;
import java.util.Map;

public interface IPersistence<T> {
	public T getPersistenceObject(Class<? extends T> typeCLass,long id);
	public List<T> getPersistenceObjects(String query);
	public List<T> getPersistenceObjects(String query,Map.Entry<String,?>... parameters);
}
