/*
 * Services used by the application
 * */
package org.file.system.service;

import java.util.List;

import org.dao.impl.MyFileSystemImpl;
import org.domain.MyFileSystem;
import org.eclipse.swt.graphics.Image;
import org.file.system.main.MainWindow;

public class Service {

	public void createFileSystem() {
		MyFileSystemImpl mfi = new MyFileSystemImpl();
		List<MyFileSystem> list = mfi.selectAll();
		if (list.size() == 0) {
			createFileFolder("../ROOT", "folder", 0);
		}
	}

	public boolean getRoot(int id) {
		MyFileSystemImpl mfi = new MyFileSystemImpl();
		MyFileSystem mfs = mfi.selectById(id);
		if (mfs.getName().equals("../ROOT")) {
			return true;
		}
		return false;
	}

	public MyFileSystem createFileFolder(String nameFileOrFolder, String type,
			int parentFolder) {
		MyFileSystemImpl mfi = new MyFileSystemImpl();
		MyFileSystem mf = new MyFileSystem();
		mf.setName(nameFileOrFolder);
		mf.setType(type);
		mf.setParentFolder(parentFolder);
		mfi.create(mf);
		return mf;
	}

	public void delete(int id, List<MyFileSystem> list) {
		MyFileSystemImpl mfi = new MyFileSystemImpl();
		mfi.deleteById(id);
		for (MyFileSystem myFileSystem : list) {
			mfi.deleteById(myFileSystem.getId());
		}

	}

	public Image getItemImage(MyFileSystem myFileSystem) {
		Image image;
		if (myFileSystem.getType().equals("folder"))
			image = new Image(null, MainWindow.IMG_FOLDER);
		else
			image = new Image(null, MainWindow.IMG_FILE);
		return image;
	}

	public String getText(int selectId) {
		MyFileSystemImpl mfi = new MyFileSystemImpl();
		char[] textData;
		MyFileSystem mf = mfi.selectById(selectId);
		String text = "";
		textData = mf.getFileData();
		if (textData != null) {
			for (int i = 0; i < textData.length; i++) {
				text += (char) textData[i];
			}
		}
		return text;
	}

	public char[] getTextForSaveFile(String text) {
		char[] textData;
		textData = text.toCharArray();
		return textData;
	}
}
