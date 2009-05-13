package dk.xam.spy.handlers;


import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class Main {
    public static void main(String[] args) {
        Display display = new Display();
        Shell shell = new Shell(display);

        final Text t = new Text(shell,SWT.BORDER);
        t.setBounds(new Rectangle(10,10,200,30));
        System.err.println(t.toDisplay(1, 1));

        Button b = new Button(shell,SWT.PUSH);
        b.setText("Show size");
        b.setBounds(new Rectangle(220,10,100,20));
        b.addSelectionListener(new SelectionAdapter() {

                public void widgetSelected(SelectionEvent e) {
                        System.err.println(t.toDisplay(1, 1)); 
                }

        });

        shell.open();

        while (!shell.isDisposed()) {
                if (!display.readAndDispatch())
                        display.sleep();
        }

        display.dispose();
    }
}