/*
 * 
 * Error dialog box with the various events of the program 
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
import org.eclipse.swt.widgets.Shell;

public class ErrorMessageDialog extends Dialog {

	protected Object result;
	protected Shell shell;
	private String textLabel;
	private static final String TITLE_LABEL = "Error";
	private static final String BUTTON_OK = "Ok";

	public ErrorMessageDialog(Shell parent, int style, String text) {
		super(parent, style);
		setText(TITLE_LABEL);
		textLabel = text;
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
		shell.setSize(300, 120);
		shell.setText(getText());
		Label lblNewLabel = new Label(shell, SWT.NONE);
		lblNewLabel.setBounds(38, 10, 290, 17);
		lblNewLabel.setText(textLabel);
		Button btnOk = new Button(shell, SWT.NONE);
		btnOk.setBounds(100, 53, 93, 29);
		btnOk.setText(BUTTON_OK);
		btnOk.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				shell.close();
			}
		});

	}

}
