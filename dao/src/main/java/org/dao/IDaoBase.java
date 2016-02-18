/*
 * The interface defines the methods entities
 */
package org.dao;

import java.util.List;

public interface IDaoBase<T> {

	public List<T> selectAll();

	public List<T> selectAllById(int id);

	public T selectById(final int id);

	public void create(final T entity);

	public void delete(final T t);

	public T update(final T t);

	public void deleteById(final int id);
}
