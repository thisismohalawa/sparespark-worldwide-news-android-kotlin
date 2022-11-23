package sparespark.worldwide.news.ui.articlelist.viewmodel

import androidx.lifecycle.ViewModel
import sparespark.worldwide.news.core.lazyDeferred
import sparespark.worldwide.news.data.repository.ArticleRepository

class ArticleViewModel(
    private val articleRepository: ArticleRepository
) : ViewModel() {

    val generalArticleList by lazyDeferred {
        articleRepository.getGeneralArticleList()
    }

    val sportsArticleList by lazyDeferred {
        articleRepository.getSportArticleList()
    }

    val healthArticleList by lazyDeferred {
        articleRepository.getHealthArticleList()
    }

    val businessArticleList by lazyDeferred {
        articleRepository.getBusinessArticleList()
    }
    val entertainmentArticleList by lazyDeferred {
        articleRepository.getEntertainmentArticleList()
    }

}