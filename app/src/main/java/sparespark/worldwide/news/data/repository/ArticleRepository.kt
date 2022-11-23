package sparespark.worldwide.news.data.repository

import androidx.lifecycle.LiveData
import sparespark.worldwide.news.data.db.dao.ArticleEntry

interface ArticleRepository {

    suspend fun getGeneralArticleList(): LiveData<out List<ArticleEntry>>
    suspend fun getSportArticleList(): LiveData<out List<ArticleEntry>>
    suspend fun getHealthArticleList(): LiveData<out List<ArticleEntry>>
    suspend fun getBusinessArticleList(): LiveData<out List<ArticleEntry>>
    suspend fun getEntertainmentArticleList(): LiveData<out List<ArticleEntry>>

}