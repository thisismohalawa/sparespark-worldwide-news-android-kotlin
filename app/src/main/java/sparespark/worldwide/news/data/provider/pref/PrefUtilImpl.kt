package sparespark.worldwide.news.data.provider.pref

import android.content.Context
import sparespark.worldwide.news.core.AppCategory
import sparespark.worldwide.news.core.DEF_COUNTRY
import sparespark.worldwide.news.data.provider.PreferenceProvider


private const val PREFERENCE_LOCATION = "PREFERENCE_LOCATION"
private const val LAST_FETCHED_LOCATION = "LAST_FETCHED_LOCATION"


class PrefUtilImpl(
    context: Context,
) : PreferenceProvider(context), PrefUtil {


    override fun getPreferenceLocation(): String {
        return preferences.getString(PREFERENCE_LOCATION, DEF_COUNTRY).toString()
    }

    /*
    * category
    * */
    override fun setCategoryLastFetchTime(category: String, time: String) {
        val editor = preferences.edit().putString(category, time)
        editor.apply()
    }

    override fun getCategoryLastFetchTime(category: String): String {
        return preferences.getString(category, "").toString()
    }

    override fun clearCategoryLastFetchTime() {
        preferencesEditor.remove(AppCategory.GENERAL).apply()
        preferencesEditor.remove(AppCategory.SPORTS).apply()
        preferencesEditor.remove(AppCategory.HEALTH).apply()
        preferencesEditor.remove(AppCategory.BUSINESS).apply()
        preferencesEditor.remove(AppCategory.ENTERTAINMENT).apply()
    }

    /*
    * last location
    * */
    override fun setLastFetchLocation() {
        val location = getPreferenceLocation()
        val editor = preferences.edit().putString(LAST_FETCHED_LOCATION, location)
        editor.apply()
    }

    override fun getLastFetchLocation(): String {
        return preferences.getString(LAST_FETCHED_LOCATION, DEF_COUNTRY).toString()
    }
}