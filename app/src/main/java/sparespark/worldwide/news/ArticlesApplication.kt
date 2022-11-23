package sparespark.worldwide.news

import android.app.Application
import android.os.Build
import android.preference.PreferenceManager
import androidx.annotation.RequiresApi
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton
import sparespark.worldwide.news.data.provider.countrycodes.CountryUtil
import sparespark.worldwide.news.data.provider.countrycodes.CountryUtilImpl
import sparespark.worldwide.news.data.db.ArticlesDatabase
import sparespark.worldwide.news.data.network.connectivity.ConnectivityInterceptor
import sparespark.worldwide.news.data.network.connectivity.ConnectivityInterceptorImpl
import sparespark.worldwide.news.data.network.datasource.ArticleNetworkDataSource
import sparespark.worldwide.news.data.network.datasource.ArticleNetworkDataSourceImpl
import sparespark.worldwide.news.data.network.response.ArticleNetworkService
import sparespark.worldwide.news.data.provider.pref.PrefUtil
import sparespark.worldwide.news.data.provider.pref.PrefUtilImpl
import sparespark.worldwide.news.data.repository.ArticleRepository
import sparespark.worldwide.news.data.repository.ArticleRepositoryImpl
import sparespark.worldwide.news.ui.articlelist.viewmodel.ArticleViewModelFactory

class ArticlesApplication : Application(), KodeinAware {
    @RequiresApi(Build.VERSION_CODES.O)
    override val kodein = Kodein.lazy {
        import(androidXModule(this@ArticlesApplication))

        // Pref
        bind<PrefUtil>() with singleton { PrefUtilImpl(instance()) }
        bind<CountryUtil>() with singleton { CountryUtilImpl() }
        // Room
        bind() from singleton { ArticlesDatabase(instance()) }
        bind() from singleton { instance<ArticlesDatabase>().articleDao() }
        // network
        bind() from singleton { ArticleNetworkService(instance()) }
        bind<ConnectivityInterceptor>() with singleton { ConnectivityInterceptorImpl(instance()) }
        bind<ArticleNetworkDataSource>() with singleton {
            ArticleNetworkDataSourceImpl(
                instance()
            )
        }
        // repo
        bind<ArticleRepository>() with singleton {
            ArticleRepositoryImpl(
                instance(),
                instance(),
                instance(),
                instance()
            )
        }
        // viewmodel factory
        bind() from provider { ArticleViewModelFactory(instance()) }
    }

    override fun onCreate() {
        super.onCreate()
        PreferenceManager.setDefaultValues(this, R.xml.preferences, false)
    }
}