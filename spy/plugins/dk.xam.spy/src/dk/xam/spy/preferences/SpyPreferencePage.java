
package dk.xam.spy.preferences;

import dk.xam.spy.Activator;

import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

public class SpyPreferencePage extends FieldEditorPreferencePage implements IWorkbenchPreferencePage
{

	public SpyPreferencePage()
	{
		super( GRID );
		setPreferenceStore( Activator.getDefault().getPreferenceStore() );
	}

	public void createFieldEditors()
	{
		addField( new DoubleFieldEditor( PreferenceConstants.P_FONT, "&Font Factor:", getFieldEditorParent() ) );
		addField( new DoubleFieldEditor( PreferenceConstants.P_SCALE, "&Scale Factor:", getFieldEditorParent() ) );
	}

	public void init( IWorkbench workbench )
	{
	}

}
