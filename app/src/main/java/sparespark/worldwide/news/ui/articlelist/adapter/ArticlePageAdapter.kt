package sparespark.worldwide.news.ui.articlelist.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import sparespark.worldwide.news.core.AppCategory
import sparespark.worldwide.news.ui.articlelist.ArticleListView

class ArticlePageAdapter(fm: FragmentManager, private val numOfTabs: Int) :
    FragmentPagerAdapter(fm) {
    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> ArticleListView(AppCategory.GENERAL)
            1 -> ArticleListView(AppCategory.SPORTS)
            2 -> ArticleListView(AppCategory.HEALTH)
            3 -> ArticleListView(AppCategory.BUSINESS)
            4 -> ArticleListView(AppCategory.ENTERTAINMENT)
            else -> ArticleListView(AppCategory.GENERAL)
        }
    }

    override fun getCount(): Int {
        return numOfTabs
    }
}
