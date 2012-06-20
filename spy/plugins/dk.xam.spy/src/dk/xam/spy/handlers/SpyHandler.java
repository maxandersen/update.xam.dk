package dk.xam.spy.handlers;

import dk.xam.spy.Activator;
import dk.xam.spy.preferences.PreferenceConstants;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.dnd.Clipboard;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchPartReference;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.ui.internal.PartPane;
import org.eclipse.ui.internal.WorkbenchPartReference;

/**
 * Our sample handler extends AbstractHandler, an IHandler base class.
 * 
 * @see org.eclipse.core.commands.IHandler
 * @see org.eclipse.core.commands.AbstractHandler
 */
public class SpyHandler extends AbstractHandler {
	/**
	 * The constructor.
	 */
	public SpyHandler() {
	}

	/**
	 * the command has been executed, so extract extract the needed information
	 * from the application context.
	 */
	public Object execute(ExecutionEvent event) throws ExecutionException {

		Shell shell = HandlerUtil.getActiveShell(event);
		Control root = shell;
		IWorkbenchPart activePart = HandlerUtil.getActivePart(event);
		
		IWorkbenchPage activePage = PlatformUI.getWorkbench()
		.getActiveWorkbenchWindow().getActivePage();
		
		IWorkbenchPartReference activePartReference = activePage.getActivePartReference();
		Control partControl = null;
		if(activePartReference instanceof WorkbenchPartReference) {
			WorkbenchPartReference x = (WorkbenchPartReference)activePartReference;
			if(!x.isDisposed()) {
				PartPane pane = x.getPane();
				Control control = pane.getControl();
				if(control instanceof Composite) {
					partControl = (Composite) control;
				}
			}
		}
		
		if(activePart!=null && partControl!=null) {
			MessageDialog dialog = new MessageDialog(shell, "Copy SWT to Balsamiq", null, "Copy full workbench or only part named '" + activePart.getTitle() + "' ?", MessageDialog.QUESTION, new String[] { "Workbench", "Part"}, 1);
			int open = dialog.open();
			if(open==1) {
				root = partControl;
			}
		}
		
		StringBuilder sb = new StringBuilder();
		sb.append("<mockup version=\"1.0\" skin=\"sketch\">\n"
				+ "  <controls>\n" + "");
		int controls = printChildren(root, 0, sb);
		sb.append("  </controls>\n" + "</mockup>");

		final Clipboard cb = new Clipboard(shell.getDisplay());
		TextTransfer textTransfer = TextTransfer.getInstance();
		cb.setContents(new Object[] { sb.toString() },
				new Transfer[] { textTransfer });

		MessageDialog.openInformation(shell, "Spy", "Found " + controls + " controls (" + Balsamiq.getUnknownControls().size() + " unknown). Paste it into Balsamiq.");
		
		if(!Balsamiq.getUnknownControls().isEmpty()) {
			System.out.println("Unknown controls: " + Balsamiq.getUnknownControls());
			Balsamiq.getUnknownControls().clear();
		} 

		return null;
	}


	private int printChildren(Control c, int count, StringBuilder sb) {
		BalsamiqControl bc = Balsamiq.getControlTypeID(c);
		return bc.process(count, sb, 1);
	}


}
