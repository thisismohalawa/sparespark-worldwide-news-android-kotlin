package sparespark.worldwide.news.data.repository

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import kotlinx.coroutines.*
import sparespark.worldwide.news.core.AppCategory
import sparespark.worldwide.news.data.db.dao.ArticleDao
import sparespark.worldwide.news.data.db.dao.ArticleEntry
import sparespark.worldwide.news.data.network.datasource.ArticleNetworkDataSource
import sparespark.worldwide.news.data.network.response.ArticleResponse
import sparespark.worldwide.news.data.provider.countrycodes.CountryUtil
import sparespark.worldwide.news.data.provider.pref.PrefUtil
import java.time.LocalDateTime
import java.time.ZoneId

@RequiresApi(Build.VERSION_CODES.O)
class ArticleRepositoryImpl(
    private val countryUtil: CountryUtil,
    private val prefUtil: PrefUtil,
    private val articleDao: ArticleDao,
    private val articleDataSource: ArticleNetworkDataSource
) : ArticleRepository {


    init {
        articleDataSource.apply {
            /*
            * Observe whenever there a change if liveData object we triggered.
            * Hasn't any life cycle.
            *
            * */
            downloadedGeneralArticles.observeForever { articleResponse ->
                persistFetchedArticleList(articleResponse, AppCategory.GENERAL)
            }
            downloadedSportsArticles.observeForever { articleResponse ->
                persistFetchedArticleList(articleResponse, AppCategory.SPORTS)
            }
            downloadedHealthArticles.observeForever { articleResponse ->
                persistFetchedArticleList(articleResponse, AppCategory.HEALTH)
            }
            downloadedBusinessArticles.observeForever { articleResponse ->
                persistFetchedArticleList(articleResponse, AppCategory.BUSINESS)
            }
            downloadedEntertainmentArticles.observeForever { articleResponse ->
                persistFetchedArticleList(articleResponse, AppCategory.ENTERTAINMENT)
            }
        }
    }


    @OptIn(DelicateCoroutinesApi::class)
    private fun persistFetchedArticleList(fetchedResponse: ArticleResponse, category: String) {

        fun deleteOldArticleData() {
            articleDao.deleteCategoryDatabase(category)
        }

        fun setCategoryForEachTable() {
            if (fetchedResponse.articles != null) for (i in fetchedResponse.articles.indices) fetchedResponse.articles[i].category =
                category
        }

        GlobalScope.launch(Dispatchers.IO) {
            val articleList = fetchedResponse.articles
            if (articleList?.isNotEmpty() == true) {
                deleteOldArticleData()
                setCategoryForEachTable()
                articleDao.insert(articleList)
                /*
                * Response is valid
                * set last fetched category data and country name...
                * */
                setCategoryLastFetchTime(category)
                setLastFetchLocation()
            }
        }
    }

    private suspend fun initArticles(category: String) {
        if (isLocationChanged()) prefUtil.clearCategoryLastFetchTime()
        /*
        *
        * last fetched category data time is null or black ?
        * first time fetching or user change location... fetching...
        *
        *
        * if last fetched time is less than one hour ago
        * then fetch...
        *
        * */
        if (isFetchNeeded(prefUtil.getCategoryLastFetchTime(category)))
            fetchArticleList(category)

    }

    private fun isFetchNeeded(lastFetchedTime: String): Boolean {
        return if (lastFetchedTime.isBlank()) true
        else {
            val oneHourAgo = LocalDateTime.now(ZoneId.systemDefault()).minusHours(1)
            val lastFetch = LocalDateTime.parse(lastFetchedTime)
            lastFetch.isBefore(oneHourAgo)
        }
    }

    private fun isLocationChanged(): Boolean {
        return prefUtil.getPreferenceLocation() != prefUtil.getLastFetchLocation()
    }

    private fun setCategoryLastFetchTime(category: String) {
        val current = LocalDateTime.now(ZoneId.systemDefault()).toString()
        prefUtil.setCategoryLastFetchTime(category, current)
    }

    private fun setLastFetchLocation() {
        prefUtil.setLastFetchLocation()
    }

    private suspend fun fetchArticleList(category: String) {
        val countryCode = countryUtil.getCountryISOCode(prefUtil.getPreferenceLocation().trim())
        articleDataSource.fetchArticles(countryCode, category)
    }

    /*
    *
    *
    * */
    override suspend fun getGeneralArticleList(): LiveData<out List<ArticleEntry>> {
        return withContext(Dispatchers.IO) {
            initArticles(AppCategory.GENERAL)
            return@withContext articleDao.getArticles(AppCategory.GENERAL)
        }
    }

    override suspend fun getSportArticleList(): LiveData<out List<ArticleEntry>> {
        return withContext(Dispatchers.IO) {
            initArticles(AppCategory.SPORTS)
            return@withContext articleDao.getArticles(AppCategory.SPORTS)
        }
    }

    override suspend fun getHealthArticleList(): LiveData<out List<ArticleEntry>> {
        return withContext(Dispatchers.IO) {
            initArticles(AppCategory.HEALTH)
            return@withContext articleDao.getArticles(AppCategory.HEALTH)
        }
    }

    override suspend fun getBusinessArticleList(): LiveData<out List<ArticleEntry>> {
        return withContext(Dispatchers.IO) {
            initArticles(AppCategory.BUSINESS)
            return@withContext articleDao.getArticles(AppCategory.BUSINESS)
        }
    }

    override suspend fun getEntertainmentArticleList(): LiveData<out List<ArticleEntry>> {
        return withContext(Dispatchers.IO) {
            initArticles(AppCategory.ENTERTAINMENT)
            return@withContext articleDao.getArticles(AppCategory.ENTERTAINMENT)
        }
    }
}