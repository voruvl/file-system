/*
 * Main class 
 * */
package org.file.system.main;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import org.dao.impl.MyFileSystemImpl;
import org.domain.MyFileSystem;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Sash;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import org.file.system.service.ErrorMessageDialog;
import org.file.system.service.FileFolderMessageDialog;
import org.file.system.service.Service;

public class MainWindow extends Shell {
	public static final String IMG_NEW_FOLDER = "src/main/resources/create-new-folder.png";
	public static final String IMG_NEW_FILE = "src/main/resources/create-new-file.png";
	public static final String IMG_FOLDER = "src/main/resources/folder.png";
	public static final String IMG_FILE = "src/main/resources/file.png";
	public static final String IMG_SAVE_FILE = "src/main/resources/save-file.png";
	public static final String IMG_CUT = "src/main/resources/cut.png";
	public static final String IMG_COPY = "src/main/resources/copy.png";
	public static final String IMG_PASTE = "src/main/resources/paste.png";
	public static final String IMG_DELETE = "src/main/resources/delete.png";
	public static final String IMG_EXITE = "src/main/resources/exit.png";
	public static final String IMG_BROWSER = "src/main/resources/browser.png";
	public static final String IMG_LANG = "src/main/resources/language.png";
	public static final String IMG_UK_FLAG = "src/main/resources/uk-flag.png";
	public static final String IMG_RU_FLAG = "src/main/resources/ru-flag.png";
	public static final int BUTTON_CUT = 1;
	public static final int BUTTON_COPY = 2;
	public static final String TITLE = "File manager";
	private String prefixDialog;
	private String newFile;
	private String newFolder;
	private String selectFileFolder;
	private String selectFolder;
	private String errorDeleteRoot;
	private String errorCopyRoot;
	private String errorCutRoot;
	private Tree tree;
	private Text text;
	private TreeItem itemSelect;
	private ResourceBundle bundle;
	private Locale ruLocale;
	private Locale enLocale;
	private Menu menu;
	private MenuItem menuFile;
	private MenuItem menuEdit;
	private MenuItem menuHelp;
	private MenuItem menuCreateDirectory;
	private MenuItem menuCreateFile;
	private MenuItem menuSaveFile;
	private MenuItem menuDelete;
	private MenuItem menuExite;
	private MenuItem menuCut;
	private MenuItem menuCopy;
	private MenuItem menuPaste;
	private MenuItem menuLanguage;
	private MenuItem menuEnLanguage;
	private MenuItem menuRuLanguage;
	private ToolItem createDirButton;
	private ToolItem createFileButton;
	private ToolItem saveButton;
	private ToolItem cutButton;
	private ToolItem copyButton;
	private ToolItem pasteButton;
	private ToolItem deleteButton;
	private Shell shell;
	private MyFileSystem itemCutCopy;
	private MyFileSystemImpl mfsi = new MyFileSystemImpl();
	private Service service = new Service();
	private List<MyFileSystem> listFiles;
	private int selectId;
	private int typeButton;
	private boolean enableCutCopy;

	public static void main(String args[]) {

		try {
			Display display = Display.getDefault();
			MainWindow shell = new MainWindow(display);
			shell.open();
			shell.layout();

			while (!shell.isDisposed()) {
				if (!display.readAndDispatch()) {
					display.sleep();
				}
			}
			System.exit(0);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void createContents() {
		setText(TITLE);
		setSize(950, 700);
		setImage(new Image(null, MainWindow.IMG_BROWSER));
	}

	@Override
	protected void checkSubclass() {

	}

	public MainWindow(Display display) {

		super(display, SWT.SHELL_TRIM);
		shell = getShell();
		setLayout(new FormLayout());
		final Sash sash = new Sash(shell, SWT.VERTICAL);
		FormData data = new FormData();
		FormData formData = new FormData();
		ToolBar toolBar = new ToolBar(getShell(), SWT.FLAT | SWT.WRAP
				| SWT.RIGHT);
		data.top = new FormAttachment(8, 0);
		data.bottom = new FormAttachment(100, 0);
		data.left = new FormAttachment(35, 0);
		sash.setLayoutData(data);
		toolBar.setLayoutData(formData);
		createDirButton = createButton(toolBar, MainWindow.IMG_NEW_FOLDER,
				new CreateListener("folder"));
		createFileButton = createButton(toolBar, MainWindow.IMG_NEW_FILE,
				new CreateListener("file"));
		saveButton = createButton(toolBar, MainWindow.IMG_SAVE_FILE,
				new SaveFileListener());
		@SuppressWarnings("unused")
		ToolItem separator = new ToolItem(toolBar, SWT.SEPARATOR);
		cutButton = createButton(toolBar, MainWindow.IMG_CUT, new CutListener());
		copyButton = createButton(toolBar, MainWindow.IMG_COPY,
				new CopyListener());
		pasteButton = createButton(toolBar, MainWindow.IMG_PASTE,
				new PasteListener());
		@SuppressWarnings("unused")
		ToolItem separator1 = new ToolItem(toolBar, SWT.SEPARATOR);
		deleteButton = createButton(toolBar, MainWindow.IMG_DELETE,
				new DeleteListener());
		tree = new Tree(this, SWT.BORDER);
		data = new FormData();
		data.top = new FormAttachment(8, 0);
		data.bottom = new FormAttachment(99, 0);
		data.left = new FormAttachment(1, 0);
		data.right = new FormAttachment(sash, 0);
		tree.setLayoutData(data);
		text = new Text(this, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL
				| SWT.CANCEL | SWT.MULTI);
		data = new FormData();
		data.top = new FormAttachment(8, 1);
		data.bottom = new FormAttachment(99, 0);
		data.left = new FormAttachment(sash, 0);
		data.right = new FormAttachment(100, 0);
		text.setLayoutData(data);
		sash.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				((FormData) sash.getLayoutData()).left = new FormAttachment(0,
						event.x);
				sash.getParent().layout();
			}
		});
		enLocale = new Locale("en", "EN");
		ruLocale = new Locale("ru", "RU");

		createMenu();
		setLanguge(enLocale);
		service.createFileSystem();
		addRootItemToTree();
		tree.addListener(SWT.Expand, new Listener() {
			public void handleEvent(Event event) {
				TreeItem itemExpand = (TreeItem) event.item;
				MyFileSystemImpl mfi = new MyFileSystemImpl();
				TreeItem[] child = itemExpand.getItems();
				for (int i = 0; i < child.length; i++) {
					child[i].removeAll();
				}
				for (int i = 0; i < child.length; i++) {
					List<MyFileSystem> childFs = mfi
							.getContent(((MyFileSystem) (child[i].getData()))
									.getId());
					for (MyFileSystem myFileSystem : childFs) {
						TreeItem treeItem = new TreeItem(child[i], SWT.NONE);
						treeItem.setText(myFileSystem.getName());
						treeItem.setImage(service.getItemImage(myFileSystem));
						treeItem.setData(myFileSystem);
						treeItem.setExpanded(true);
					}
				}
			}

		});
		tree.addListener(SWT.Selection, new Listener() {

			public void handleEvent(Event event) {
				Service serviceLocal = new Service();
				itemSelect = (TreeItem) event.item;
				MyFileSystem selectedTreeItem = (MyFileSystem) itemSelect
						.getData();
				selectId = selectedTreeItem.getId();
				text.setText(serviceLocal.getText(selectId));
				if (selectedTreeItem.getType().equals("file")) {
					text.setEnabled(true);
					menuCreateDirectory.setEnabled(false);
					menuCreateFile.setEnabled(false);
					menuPaste.setEnabled(false);
					pasteButton.setEnabled(false);
					createDirButton.setEnabled(false);
					createFileButton.setEnabled(false);
				} else {
					text.setEnabled(false);
					menuCreateDirectory.setEnabled(true);
					menuCreateFile.setEnabled(true);
					menuPaste.setEnabled(true);
					pasteButton.setEnabled(true);
					createDirButton.setEnabled(true);
					createFileButton.setEnabled(true);
				}
			}
		});

	}

	private ToolItem createButton(ToolBar toolBar, String nameImageFile,
			Listener listener) {
		ToolItem result;
		result = new ToolItem(toolBar, SWT.PUSH);
		result.setImage(new Image(null, nameImageFile));
		result.addListener(SWT.Selection, listener);
		return result;
	}

	private void createMenu() {
		menu = new Menu(this, SWT.BAR);
		setMenuBar(menu);
		menuFile = new MenuItem(menu, SWT.CASCADE);
		menuEdit = new MenuItem(menu, SWT.CASCADE);

		Menu menu_1 = new Menu(menuFile);
		Menu menu_2 = new Menu(menuEdit);

		menuFile.setMenu(menu_1);
		menuCreateDirectory = createMenuItem(menu_1, MainWindow.IMG_NEW_FOLDER,
				new CreateListener("folder"));
		menuCreateFile = createMenuItem(menu_1, MainWindow.IMG_NEW_FILE,
				new CreateListener("file"));
		menuSaveFile = createMenuItem(menu_1, MainWindow.IMG_SAVE_FILE,
				new SaveFileListener());
		menuDelete = createMenuItem(menu_1, MainWindow.IMG_DELETE,
				new DeleteListener());
		new MenuItem(menu_1, SWT.SEPARATOR);
		menuExite = createMenuItem(menu_1, MainWindow.IMG_EXITE,
				new ExitListener());

		menuCut = createMenuItem(menu_2, MainWindow.IMG_CUT, new CutListener());
		menuCopy = createMenuItem(menu_2, MainWindow.IMG_COPY,
				new CopyListener());
		new MenuItem(menu_2, SWT.SEPARATOR);
		menuPaste = createMenuItem(menu_2, MainWindow.IMG_PASTE,
				new PasteListener());
		menuEdit.setMenu(menu_2);
		menuHelp = new MenuItem(menu, SWT.CASCADE);
		Menu menu_3 = new Menu(menuHelp);
		menuHelp.setMenu(menu_3);
		menuLanguage = new MenuItem(menu_3, SWT.CASCADE);
		menuLanguage.setImage(new Image(null, MainWindow.IMG_LANG));
		Menu menu_4 = new Menu(menuLanguage);
		menuLanguage.setMenu(menu_4);
		menuEnLanguage = createMenuItem(menu_4, MainWindow.IMG_UK_FLAG,
				new LocaleListener(enLocale));
		menuRuLanguage = createMenuItem(menu_4, MainWindow.IMG_RU_FLAG,
				new LocaleListener(ruLocale));

		createContents();

	}

	private MenuItem createMenuItem(Menu menu, String nameImageFile,
			Listener listener) {
		MenuItem result = new MenuItem(menu, SWT.NONE);
		result.setImage(new Image(null, nameImageFile));
		result.addListener(SWT.Selection, listener);
		return result;
	}

	/* Add root folder in tree file system */
	private void addRootItemToTree() {

		MyFileSystemImpl mfi = new MyFileSystemImpl();
		int idRoot = mfi.getIdRoot();
		MyFileSystem fsRoot = mfi.selectById(idRoot);
		TreeItem treeItem = new TreeItem(tree, SWT.NONE);
		treeItem.setData(fsRoot);
		treeItem.setText(fsRoot.getName());
		treeItem.setImage(service.getItemImage(fsRoot));
		addChildItem(fsRoot, treeItem);

	}

	/* Add child items in parent folder */
	private void addChildItem(MyFileSystem fsParent, TreeItem itemParent) {
		MyFileSystemImpl mfi = new MyFileSystemImpl();
		List<MyFileSystem> list = mfi.getContent(fsParent.getId());
		for (MyFileSystem myFileSystem : list) {
			TreeItem fs = new TreeItem(itemParent, SWT.NONE);
			fs.setText(myFileSystem.getName());
			fs.setImage(service.getItemImage(myFileSystem));
			fs.setData(myFileSystem);
			List<MyFileSystem> innerList = mfi.getContent(myFileSystem.getId());
			for (MyFileSystem myFileSystem2 : innerList) {
				TreeItem innerFs = new TreeItem(fs, SWT.NONE);
				innerFs.setText(myFileSystem2.getName());
				innerFs.setImage(service.getItemImage(myFileSystem2));
				innerFs.setData(myFileSystem2);
			}
		}
	}

	public void getListFiles(int idParent) {

		List<MyFileSystem> list = new ArrayList<MyFileSystem>();
		MyFileSystemImpl mfsi = new MyFileSystemImpl();
		list = mfsi.getContent(idParent);
		for (MyFileSystem myFileSystem : list) {
			if (myFileSystem.getType().equals("folder")) {
				getListFiles(myFileSystem.getId());
			}
			listFiles.add(myFileSystem);
		}

	}

	/* insert the copied elements method */
	public void pasteCopy(int oldId, int newId) {
		MyFileSystemImpl mfsi1 = new MyFileSystemImpl();
		for (MyFileSystem myFileSystem : listFiles) {
			if (myFileSystem.getParentFolder() == oldId) {
				myFileSystem.setParentFolder(newId);
				int oldIdIn = myFileSystem.getId();
				mfsi1.create(myFileSystem);
				int newIdIn = myFileSystem.getId();
				for (MyFileSystem myFileSystem1 : listFiles) {
					if (myFileSystem1.getParentFolder() == oldIdIn) {
						pasteCopy(oldIdIn, newIdIn);
					}
				}

			}

		}
	}

	/* The event handler create file or folder the selected item */
	class CreateListener implements Listener {
		String type;

		public CreateListener(String type) {
			this.type = type;

		}

		public void handleEvent(Event event) {
			try {
				String newItem;
				if (type.equals("file")) {
					newItem = newFile;
				} else {
					newItem = newFolder;
				}
				FileFolderMessageDialog messageDialog = new FileFolderMessageDialog(
						getShell(), SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL,
						prefixDialog + newItem, type);
				messageDialog.open();
				if (messageDialog.getEnteredText() != null) {
					MyFileSystem mfs = service.createFileFolder(
							messageDialog.getEnteredText(), type, selectId);
					TreeItem item = new TreeItem(itemSelect, SWT.NONE);
					item.setText(messageDialog.getEnteredText());
					if (type.equals("file"))
						item.setImage(new Image(null, MainWindow.IMG_FILE));
					else
						item.setImage(new Image(null, MainWindow.IMG_FOLDER));
					item.setData(mfs);
				}
				itemSelect.setExpanded(true);
			} catch (Exception e) {
				ErrorMessageDialog errorMessageDialog = new ErrorMessageDialog(
						shell, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL,
						selectFolder);
				errorMessageDialog.open();
			}
		}
	}

	/* The event handler cut the selected item */
	class CutListener implements Listener {

		public void handleEvent(Event event) {
			try {
				typeButton = BUTTON_CUT;
				itemCutCopy = (MyFileSystem) itemSelect.getData();
				if (service.getRoot(itemCutCopy.getId())) {
					ErrorMessageDialog errorDialog = new ErrorMessageDialog(
							shell, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL,
							errorCutRoot);
					errorDialog.open();
					enableCutCopy = false;
				} else {
					itemCutCopy = mfsi.selectById(itemCutCopy.getId());
					itemSelect.dispose();
					enableCutCopy = true;
				}

			} catch (Exception e) {
				ErrorMessageDialog errorMessageDialog = new ErrorMessageDialog(
						shell, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL,
						selectFileFolder);
				errorMessageDialog.open();
			}
		}
	}

	/* The event handler copy the selected item */
	class CopyListener implements Listener {
		public void handleEvent(Event event) {
			try {
				listFiles = new ArrayList<MyFileSystem>();
				typeButton = BUTTON_COPY;
				itemCutCopy = (MyFileSystem) itemSelect.getData();
				itemCutCopy = mfsi.selectById(itemCutCopy.getId());
				if (service.getRoot(itemCutCopy.getId())) {
					ErrorMessageDialog errorDialog = new ErrorMessageDialog(
							shell, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL,
							errorCopyRoot);
					errorDialog.open();
					enableCutCopy = false;
				} else {
					getListFiles(itemCutCopy.getId());
					enableCutCopy = true;
				}
			} catch (Exception e) {
				ErrorMessageDialog errorMessageDialog = new ErrorMessageDialog(
						shell, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL,
						selectFileFolder);
				errorMessageDialog.open();
			}
		}
	}

	/* The event handler paste the selected item */
	class PasteListener implements Listener {
		public void handleEvent(Event event) {
			try {
				if (enableCutCopy) {
					int idParent = ((MyFileSystem) itemSelect.getData())
							.getId();
					itemSelect.removeAll();
					itemCutCopy.setParentFolder(idParent);
					if (typeButton == BUTTON_CUT) {
						mfsi.update(itemCutCopy);
					} else if (typeButton == BUTTON_COPY) {
						MyFileSystemImpl mfsi1 = new MyFileSystemImpl();
						MyFileSystem mfsCopy = new MyFileSystem();
						mfsCopy.setName(itemCutCopy.getName());
						mfsCopy.setParentFolder(itemCutCopy.getParentFolder());
						mfsCopy.setType(itemCutCopy.getType());
						mfsCopy.setFileData(itemCutCopy.getFileData());
						int oldId = itemCutCopy.getId();
						mfsi1.create(mfsCopy);
						int newId = mfsCopy.getId();
						pasteCopy(oldId, newId);

					}
					addChildItem((MyFileSystem) itemSelect.getData(),
							itemSelect);
					itemSelect.setExpanded(true);
				}
			} catch (Exception e) {
				ErrorMessageDialog errorMessageDialog = new ErrorMessageDialog(
						shell, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL,
						selectFileFolder);
				errorMessageDialog.open();
			}
		}
	}

	/* The event handler save file the selected item */
	class SaveFileListener implements Listener {

		public void handleEvent(Event arg0) {
			try {
				Service service = new Service();
				MyFileSystemImpl mfsi = new MyFileSystemImpl();
				TreeItem selectItem = tree.getSelection()[0];
				MyFileSystem mfs = mfsi.selectById(((MyFileSystem) selectItem
						.getData()).getId());
				mfs.setFileData(service.getTextForSaveFile(text.getText()));
				mfsi.update(mfs);
			} catch (Exception e) {
				ErrorMessageDialog errorMessageDialog = new ErrorMessageDialog(
						shell, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL,
						selectFileFolder);
				errorMessageDialog.open();

			}
		}
	}

	/* The event handler delete the selected item */
	class DeleteListener implements Listener {
		public void handleEvent(Event arg0) {
			try {
				listFiles = new ArrayList<MyFileSystem>();
				Service service = new Service();
				TreeItem selectItem = tree.getSelection()[0];
				MyFileSystem mfs = ((MyFileSystem) selectItem.getData());
				if (service.getRoot(mfs.getId())) {
					ErrorMessageDialog errorDialog = new ErrorMessageDialog(
							shell, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL,
							errorDeleteRoot);
					errorDialog.open();
				} else {
					getListFiles(mfs.getId());
					service.delete(mfs.getId(), listFiles);
					selectItem.dispose();
				}

			} catch (Exception e) {
				ErrorMessageDialog errorMessageDialog = new ErrorMessageDialog(
						shell, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL,
						selectFileFolder);
				errorMessageDialog.open();
			}
		}
	}

	class ExitListener implements Listener {
		public void handleEvent(Event arg0) {
			System.exit(0);
		}
	}

	/* The event handler locale */
	class LocaleListener implements Listener {
		Locale locale;

		public LocaleListener(Locale locale) {
			super();
			this.locale = locale;
		}

		public void handleEvent(Event arg0) {
			setLanguge(locale);
		}
	}

	public void setLanguge(Locale locale) {
		bundle = ResourceBundle.getBundle("message", locale);
		menuFile.setText(bundle.getString("menu_file"));
		menuEdit.setText(bundle.getString("menu_edit"));
		menuHelp.setText(bundle.getString("menu_help"));
		menuLanguage.setText(bundle.getString("menu_languge"));
		menuCopy.setText(bundle.getString("menu_copy"));
		menuCut.setText(bundle.getString("menu_cut"));
		menuPaste.setText(bundle.getString("menu_paste"));
		menuSaveFile.setText(bundle.getString("menu_save"));
		menuDelete.setText(bundle.getString("menu_delete"));
		menuExite.setText(bundle.getString("menu_exit"));
		menuCreateFile.setText(bundle.getString("menu_create_file"));
		menuCreateDirectory.setText(bundle.getString("menu_create_folder"));

		copyButton.setToolTipText(bundle.getString("menu_copy"));
		cutButton.setToolTipText(bundle.getString("menu_cut"));
		pasteButton.setToolTipText(bundle.getString("menu_paste"));
		saveButton.setToolTipText(bundle.getString("menu_save"));
		deleteButton.setToolTipText(bundle.getString("menu_delete"));
		createFileButton.setToolTipText(bundle.getString("menu_create_file"));
		createDirButton.setToolTipText(bundle.getString("menu_create_folder"));

		prefixDialog = bundle.getString("prefix_dialog");
		newFile = bundle.getString("new_file");
		newFolder = bundle.getString("new_folder");
		selectFileFolder = bundle.getString("select_file_folder");
		selectFolder = bundle.getString("select_folder");
		errorDeleteRoot = bundle.getString("error_delete_root");
		errorCopyRoot = bundle.getString("error_copy_root");
		errorCutRoot = bundle.getString("error_cut_root");

	}
}