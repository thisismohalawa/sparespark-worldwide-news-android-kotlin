package sparespark.worldwide.news.ui.settings

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import sparespark.worldwide.news.R

class SettingsView : PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        addPreferencesFromResource(R.xml.preferences)
    }
}