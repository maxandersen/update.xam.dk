
package dk.xam.spy.preferences;

import org.eclipse.jface.preference.StringFieldEditor;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;

public class DoubleFieldEditor extends StringFieldEditor
{

	private double minValidValue = 0;

	private double maxValidValue = Double.MAX_VALUE;

	public DoubleFieldEditor( String name, String labelText, Composite parent )
	{
		this( name, labelText, parent, 10 );
	}

	public DoubleFieldEditor( String name, String labelText, Composite parent, int textLimit )
	{
		init( name, labelText );
		setTextLimit( textLimit );
		setEmptyStringAllowed( false );
		setErrorMessage( "Value must be a Double" );
		createControl( parent );
	}

	protected boolean checkState()
	{
		Text text = getTextControl();

		if ( text == null )
		{
			return false;
		}

		String numberString = text.getText();
		try
		{
			double number = Double.valueOf( numberString ).doubleValue();
			if ( number >= minValidValue && number <= maxValidValue )
			{
				clearErrorMessage();
				return true;
			}

			showErrorMessage();
			return false;

		}
		catch ( NumberFormatException e1 )
		{
			showErrorMessage();
		}

		return false;
	}

	protected void doLoadDefault()
	{
		Text text = getTextControl();
		if ( text != null )
		{
			double value = getPreferenceStore().getDefaultDouble( getPreferenceName() );
			text.setText( "" + value );//$NON-NLS-1$
		}
		valueChanged();
	}

	protected void doLoad()
	{
		Text text = getTextControl();
		if ( text != null )
		{
			double value = getPreferenceStore().getDouble( getPreferenceName() );
			text.setText( "" + value );//$NON-NLS-1$
			oldValue = "" + value; //$NON-NLS-1$
		}

	}

	/*
	 * (non-Javadoc) Method declared on FieldEditor.
	 */
	protected void doStore()
	{
		Text text = getTextControl();
		if ( text != null )
		{
			Double d = new Double( text.getText() );
			getPreferenceStore().setValue( getPreferenceName(), d.doubleValue() );
		}
	}

}
