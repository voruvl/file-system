/*
 * The entity of describing my file system
 * */
package org.domain;

import java.io.Serializable;
import java.util.Arrays;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table
public class MyFileSystem implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private int id; 
	@Column
	private String name;  
	@Column
	private String type; //table field determines the recording as a file or folder
	@Column(name = "parent_folder")
	private int parentFolder; // table field determines the parent folder for the current record
	@Column(name = "data", length = 40000)
	private char[] fileData; //table field determines the contents of a text file

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getParentFolder() {
		return parentFolder;
	}

	public void setParentFolder(int parentFolder) {
		this.parentFolder = parentFolder;
	}

	public char[] getFileData() {
		return fileData;
	}

	public void setFileData(char[] fileData) {
		this.fileData = fileData;
	}

	@Override
	public String toString() {
		return "MyFileSystem [id=" + id + ", name=" + name + ", type=" + type + ", parentFolder=" + parentFolder
				+ ", fileData=" + Arrays.toString(fileData) + "]";
	}

}
