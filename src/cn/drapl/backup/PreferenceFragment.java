package cn.drapl.backup;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.preference.ListPreference;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;

import cn.drapl.backup.ui.FileBrowser;
import cn.drapl.backup.ui.LanguageHelper;

/**
 * Created by draplater on 2017/11/23.
 */
public class PreferenceFragment extends PreferenceFragmentCompat
        implements SharedPreferences.OnSharedPreferenceChangeListener {
    @Override
    public void onCreatePreferences(Bundle bundle, String s) {
        addPreferencesFromResource(R.xml.preferences);
//        FileBrowserEditTextPreference backupFolderPref =
//                (FileBrowserEditTextPreference) findPreference(
//                        Constants.PREFS_PATH_BACKUP_DIRECTORY);
//        // det ser ikke ud til at setDefaultValue() virker som den skal
//        if (backupFolderPref.getText() == null) {
//            backupFolderPref.setText(FileCreationHelper.getDefaultBackupDirPath());
//        }
        Preference cryptoMenu = findPreference("cryptoMenu");
        cryptoMenu.setEnabled(Crypto.isAvailable(getContext()));
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // for some reason preferencesactivity doesn't behave like the other
        // activities when setting locale so it can't be done in onCreate
        // http://stackoverflow.com/a/20057397
        ListPreference languages = (ListPreference) findPreference(
                Constants.PREFS_LANGUAGES);
        if (languages != null)
            LanguageHelper.initLanguage(getContext(), languages.getValue());
    }

    @Override
    public void onPause() {
        getPreferenceScreen().getSharedPreferences().
                unregisterOnSharedPreferenceChangeListener(this);
        super.onPause();
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
//            if(key.equals(Constants.PREFS_LANGUAGES))
//            {
//                if(new LanguageHelper().changeLanguage(getContext(), sharedPreferences.getString(
//                        Constants.PREFS_LANGUAGES, Constants.PREFS_LANGUAGES_DEFAULT))) {
//                    Utils.reloadWithParentStack(SettingsFragment.this);
//                }
//            }
    }

    @Override
    public void onResume() {
        super.onResume();
        getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
//        FileBrowserEditTextPreference backupFolderPref =
//                (FileBrowserEditTextPreference) findPreference(
//                        Constants.PREFS_PATH_BACKUP_DIRECTORY);
//        if (FileBrowser.getPath() != null) {
//            backupFolderPref.getEditText().setText(FileBrowser.getPath());
//            FileBrowser.invalidatePath();
//        }
    }
}
