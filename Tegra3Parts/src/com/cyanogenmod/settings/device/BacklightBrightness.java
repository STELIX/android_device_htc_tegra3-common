package com.cyanogenmod.settings.device;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.PreferenceManager;
import java.io.File;

public class BacklightBrightness implements OnPreferenceChangeListener {

    private static final String FILE = "/sys/class/leds/button-backlight/button_brightness";

    public static boolean isSupported() {
        return Utils.fileExists(FILE) && Utils.fileWritable(FILE);
    }

	public static String getValue(Context context) {
		String value = Utils.getFileValue(FILE, "50");
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPrefs.getString(DeviceSettings.KEY_BACKLIGHTBRIGHTNESS, value);
	}
	
    /**
     * Restore button backlight setting from SharedPreferences. (Write to kernel.)
     * @param context       The context to read the SharedPreferences from
     */
    public static void restore(Context context) {
        if (!isSupported()) {
            return;
        }

		String value = getValue(context);
        Utils.writeValue(FILE, value);
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        Utils.writeValue(FILE, (String) newValue);
        return true;
    }
}
