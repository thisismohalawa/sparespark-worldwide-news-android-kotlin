package sparespark.worldwide.news.data.provider.pref

interface PrefUtil {

    // preference
    fun getPreferenceLocation(): String

    // time fetching
    fun setCategoryLastFetchTime(category: String, time: String)
    fun getCategoryLastFetchTime(category: String): String
    fun clearCategoryLastFetchTime()

    //  last location
    fun setLastFetchLocation()
    fun getLastFetchLocation():String

}