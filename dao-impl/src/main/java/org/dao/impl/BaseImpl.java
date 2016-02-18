/*
 * Implementation methods of entities
 * */
package org.dao.impl;

import java.util.List;

import org.dao.IDaoBase;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Restrictions;

public class BaseImpl<T> implements IDaoBase<T> {

	protected Class<T> clazz;
	protected SessionFactory factory;
	protected Session session;

	public BaseImpl() {
		factory = new Configuration().configure().buildSessionFactory();
		session = factory.openSession();
	}
/*
 * Select all records from the database
 * */
	public List<T> selectAll() {
		return session.createCriteria(clazz).list();
	}
	/*
	 * Select all records from the database by id
	 * */
	public List<T> selectAllById(int id) {

		return session.createCriteria(clazz)
				.add(Restrictions.eq("parentFolder", id)).list();
	}
/*
 * Select record from the database by id
 * */

	public T selectById(int id) {
		return session.load(clazz, id);
	}
	/*
	 * Creating a record in the database
	 * */
	public void create(T entity) {
		session.beginTransaction();
		session.save(entity);
		session.getTransaction().commit();

	}
	/*
	 * Delete a record from the database
	 * */
	public void delete(T entity) {
		session.beginTransaction();
		session.delete(entity);
		session.getTransaction().commit();
	}
	/*
	 * Update a record in the database
	 * */
	public T update(T entity) {
		session.beginTransaction();
		session.update(entity);
		session.getTransaction().commit();
		return entity;
	}
	/*
	 * Delete a record from the database by id
	 * */
	public void deleteById(int entityId) {
		T find = selectById(entityId);
		delete(find);
	}

}
