/*
 * Implementation methods for class MyFileSystem
 * */
package org.dao.impl;

import java.util.List;

import org.domain.MyFileSystem;
import org.hibernate.Query;

public class MyFileSystemImpl extends BaseImpl<MyFileSystem> {
	public MyFileSystemImpl() {
		super();
		clazz = MyFileSystem.class;
	}
	/*
	 * The contents of the selected folder from my file system
	 * */
	public List<MyFileSystem> getContent(int parent) {
		Query query = session
				.createSQLQuery(
						"select * from myfilesystem where parent_folder=:parent order by type desc")
				.addEntity(clazz).setParameter("parent", parent);
		return query.list();
	}

	/*
	 * Select id root directory from my file system
	 * */
	public int getIdRoot() {
		Query query = session
				.createSQLQuery("select id from myfilesystem where name='../ROOT'");
		return (Integer) query.list().get(0);
	}

}
