package sparespark.worldwide.news.ui

import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import sparespark.worldwide.news.R
import sparespark.worldwide.news.core.visible
import sparespark.worldwide.news.databinding.ActivityArticlesBinding
import sparespark.worldwide.news.ui.settings.SettingsActivity

const val TAG = "DEBUG_TAG"

class ArticlesActivity : AppCompatActivity(), Communicator {

    private lateinit var mBinding: ActivityArticlesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_articles)

        setSupportActionBar(mBinding.toolBar)
        updateToolBarTitle(R.string.general)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.settings -> startActivity(Intent(this, SettingsActivity::class.java))
        }
        return super.onOptionsItemSelected(item)
    }

    override fun showProgressCircular() {
        mBinding.progressCircular.visible(true)
    }

    override fun hideProgressCircular() {
        mBinding.progressCircular.visible(false)
    }

    override fun updateToolBarBackground(colorRes: Int) {
        supportActionBar?.setBackgroundDrawable(ColorDrawable(resources.getColor(colorRes)))
    }

    override fun updateToolBarTitle(stringRes: Int) {
        supportActionBar?.title = "\t" + getString(stringRes)
    }

    override fun restartActivity() {
        val intent = applicationContext.packageManager
            .getLaunchIntentForPackage(applicationContext.packageName)

        intent!!.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
    }
}