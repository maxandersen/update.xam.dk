package dk.xam.spy.handlers;

import dk.xam.spy.Activator;
import dk.xam.spy.preferences.PreferenceConstants;

/**
 * Class to hold settings to be used for a  control.
 * 
 * @author max
 *
 */
public class BalsamiqSettings {
	
	static BalsamiqSettings INSTANCE = new BalsamiqSettings();
	
	// currently just return what is in the preference store to keep it simple.
	public double getFontFactor() {
		return Activator.getDefault().getPreferenceStore().getDouble( PreferenceConstants.P_FONT );
	}

	public double getScaleFactor() {
		return Activator.getDefault().getPreferenceStore().getDouble( PreferenceConstants.P_FONT );
	}

	public static BalsamiqSettings getInstance() {
		return INSTANCE;
	}
}
