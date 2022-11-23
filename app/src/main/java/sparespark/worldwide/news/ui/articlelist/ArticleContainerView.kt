package sparespark.worldwide.news.ui.articlelist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.articlecontainer_view.*
import sparespark.worldwide.news.R
import sparespark.worldwide.news.core.updateColor
import sparespark.worldwide.news.ui.Communicator
import sparespark.worldwide.news.ui.articlelist.adapter.ArticlePageAdapter

class ArticleContainerView : Fragment() {

    private lateinit var communicator: Communicator

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.articlecontainer_view, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        communicator = activity as Communicator


        setUpViewPager()
    }

    private fun setUpViewPager() {
        val articlePageAdapter = ArticlePageAdapter(childFragmentManager, tabLayout.tabCount)
        view_pager.adapter = articlePageAdapter
        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                view_pager.currentItem = tab.position
                when (tab.position) {
                    0 -> {
                        tabLayout.updateColor(R.color.blue_dark)
                        updateStatusBarColor(R.color.blue_dark)
                        communicator.updateToolBarBackground(R.color.blue_dark)
                        communicator.updateToolBarTitle(R.string.general)
                    }
                    1 -> {
                        tabLayout.updateColor(R.color.green_light)
                        updateStatusBarColor(R.color.green_light)
                        communicator.updateToolBarBackground(R.color.green_light)
                        communicator.updateToolBarTitle(R.string.sports)
                    }
                    2 -> {
                        tabLayout.updateColor(R.color.blue_light)
                        updateStatusBarColor(R.color.blue_light)
                        communicator.updateToolBarBackground(R.color.blue_light)
                        communicator.updateToolBarTitle(R.string.health)
                    }
                    3 -> {
                        tabLayout.updateColor(R.color.purple)
                        updateStatusBarColor(R.color.purple)
                        communicator.updateToolBarBackground(R.color.purple)
                        communicator.updateToolBarTitle(R.string.business)
                    }
                    4 -> {
                        tabLayout.updateColor(R.color.yellow)
                        updateStatusBarColor(R.color.yellow)
                        communicator.updateToolBarBackground(R.color.yellow)
                        communicator.updateToolBarTitle(R.string.entertainment)
                    }
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })

        view_pager.addOnPageChangeListener(object :
            TabLayout.TabLayoutOnPageChangeListener(tabLayout) {
            override fun onPageScrollStateChanged(state: Int) {

            }
        })
    }

    private fun updateStatusBarColor(color: Int) {
        requireActivity().window?.apply {
            addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            statusBarColor = ContextCompat.getColor(requireContext(), color)
        }
    }
}