/*
 * 
 * The dialog to create files and folders
 * 
 * */
package org.file.system.service;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class FileFolderMessageDialog extends Dialog {

	protected Object result;
	protected Shell shell;
	private Text text;
	private String type;
	private String value;
	private String textLabel;
	private static final String TITLE_LABEL = "Question";
	private static final String TITLE_MESSAGE = "Error";
	private static final String BUTTON_OK = "Ok";
	private static final String BUTTON_CANCEL = "Cancel";
	private static final String PREFIX_MESSAGE = "Name ";
	private static final String SUFFIX_MESSAGE = " can not be empty";

	public FileFolderMessageDialog(Shell parent, int style, String textLabel,
			String type) {
		super(parent, style);
		this.textLabel = textLabel;
		this.type = type;
		setText(TITLE_LABEL);

	}

	public Object open() {
		createContents();
		shell.open();
		shell.layout();
		Display display = getParent().getDisplay();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		return result;
	}

	private void createContents() {
		shell = new Shell(getParent(), getStyle());
		shell.setSize(350, 150);
		shell.setText(getText());
		Label lblNewLabel = new Label(shell, SWT.NONE);
		lblNewLabel.setBounds(38, 10, 230, 17);
		lblNewLabel.setText(textLabel);
		text = new Text(shell, SWT.BORDER);
		text.setBounds(38, 42, 266, 29);
		Button btnOk = new Button(shell, SWT.NONE);
		btnOk.setBounds(56, 83, 93, 29);
		btnOk.setText(BUTTON_OK);
		btnOk.addSelectionListener(new SelectionAdapter() {

			public void widgetSelected(SelectionEvent event) {
				if (text.getText().equals("")) {
					MessageBox ms = new MessageBox(getParent(),
							SWT.ICON_WARNING | SWT.OK);
					ms.setText(TITLE_MESSAGE);
					ms.setMessage(PREFIX_MESSAGE + type + SUFFIX_MESSAGE);
					ms.open();
				} else {
					value = text.getText();
					shell.close();
				}
			}
		});

		Button btnCancel = new Button(shell, SWT.NONE);
		btnCancel.setBounds(190, 83, 93, 29);
		btnCancel.setText(BUTTON_CANCEL);
		btnCancel.addSelectionListener(new SelectionAdapter() {

			public void widgetSelected(SelectionEvent event) {
				value =null;
				shell.close();
			}

		});

	}

	public String getEnteredText() {
		return value;
	}

}
