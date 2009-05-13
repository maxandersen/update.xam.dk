package dk.xam.spy.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.dnd.Clipboard;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.handlers.HandlerUtil;

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

		StringBuilder sb = new StringBuilder();
		sb.append("<mockup version=\"1.0\" skin=\"sketch\">\n"
				+ "  <controls>\n" + "");
		int controls = printChildren(shell, 0, sb);
		sb.append("  </controls>\n" + "</mockup>");

		final Clipboard cb = new Clipboard(shell.getDisplay());
		TextTransfer textTransfer = TextTransfer.getInstance();
		cb.setContents(new Object[] { sb.toString() },
				new Transfer[] { textTransfer });

		MessageDialog.openInformation(shell, "Spy", "Found " + controls + " controls. Paste it into Balsamiq.");

		return null;
	}

	
	private int printChildren(Control c, int count, StringBuilder sb) {
		
		
		BalsamiqControl bc = Balsamiq.getControlTypeID(c);
		return bc.process(count, sb, 1);
	}


}
